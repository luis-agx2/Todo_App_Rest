package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.LabelFilter;
import com.lag.todoapp.todoapp.model.request.LabelRequest;
import com.lag.todoapp.todoapp.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/labels")
public class LabelController {
    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Object> getAllFilteredAndPaginated(@RequestParam (required = false) String name,
                                                             @RequestParam (required = false) String color,
                                                             Pageable pageable) {
        LabelFilter filters = new LabelFilter(name, color);

        return ResponseEntity.ok(labelService.findAllFilteredAndPaginated(filters, pageable));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin/{labelId}")
    public ResponseEntity<Object> getByIdAdmin(@PathVariable Long labelId) throws NotFoundException {
        return ResponseEntity.ok(labelService.findByIdAdmin(labelId));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Object> create(@Validated @RequestBody LabelRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(labelService.create(request, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{labelId}")
    public ResponseEntity<Object> updateById(@RequestBody LabelRequest request, @PathVariable Long labelId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException, ValidationErrorException {
        return ResponseEntity.ok(labelService.updateyId(request, labelId, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{labelId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long labelId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(labelService.deleteById(labelId, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Object> getAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(labelService.findAll(userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{labelId}")
    public ResponseEntity<Object> getById(@PathVariable Long labelId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(labelService.findById(labelId, userDetails));
    }
}
