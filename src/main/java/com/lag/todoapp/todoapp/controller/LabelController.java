package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.LabelFilter;
import com.lag.todoapp.todoapp.model.request.LabelRequest;
import com.lag.todoapp.todoapp.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Object> create(@Validated @RequestBody LabelRequest request) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(labelService.create(request));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{labelId}")
    public ResponseEntity<Object> updateById(@RequestBody LabelRequest request, @PathVariable Long labelId) throws NotFoundException, ValidationErrorException {
        return ResponseEntity.ok(labelService.updateyId(request, labelId));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{labelId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long labelId) throws NotFoundException {
        return ResponseEntity.ok(labelService.deleteById(labelId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(labelService.findAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{labelId}")
    public ResponseEntity<Object> getById(@PathVariable Long labelId) throws NotFoundException {
        return ResponseEntity.ok(labelService.findById(labelId));
    }
}
