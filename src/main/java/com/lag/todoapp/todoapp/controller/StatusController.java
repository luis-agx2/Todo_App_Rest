package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.StatusFilter;
import com.lag.todoapp.todoapp.model.request.StatusRequest;
import com.lag.todoapp.todoapp.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Object> getAllFilteredAndPaginated(@RequestParam (required = false) String name,
                                                             Pageable pageable) {
        StatusFilter filters = new StatusFilter(name);

        return ResponseEntity.ok(statusService.findFilteredAndPaginated(filters, pageable));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin/{statusId}")
    public ResponseEntity<Object> getById(@PathVariable Long statusId) throws NotFoundException {
        return ResponseEntity.ok(statusService.findById(statusId));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<Object> create(@Valid @RequestBody StatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statusService.create(request));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/admin/{statusId}")
    public ResponseEntity<Object> updateById(@RequestBody StatusRequest request, @PathVariable Long statusId) throws NotFoundException, ValidationErrorException {
        return ResponseEntity.ok(statusService.updateById(statusId, request));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/admin/{statusId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long statusId) throws NotFoundException {
        return ResponseEntity.ok(statusService.deleteById(statusId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(statusService.findAll());
    }
}
