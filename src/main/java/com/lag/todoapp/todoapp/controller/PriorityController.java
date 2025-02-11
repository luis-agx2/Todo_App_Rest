package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.PriorityFilter;
import com.lag.todoapp.todoapp.model.request.PriorityRequest;
import com.lag.todoapp.todoapp.service.PriorityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/priorities")
public class PriorityController {
    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Object> getFilteredAndPaginated(@RequestParam (required = false) String name,
                                                          Pageable pageable) {
        PriorityFilter filters = new PriorityFilter(name);
        return ResponseEntity.ok(priorityService.findAllFiltered(filters, pageable));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin/{priorityId}")
    public ResponseEntity<Object> getById(@PathVariable Long priorityId) throws NotFoundException {
        return ResponseEntity.ok(priorityService.findById(priorityId));
    }

    @PostMapping("/admin")
    public ResponseEntity<Object> create(@Valid @RequestBody PriorityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priorityService.create(request));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/admin/{priorityId}")
    public ResponseEntity<Object> update(@RequestBody PriorityRequest request, @PathVariable Long priorityId) throws NotFoundException, ValidationErrorException {
        return ResponseEntity.ok(priorityService.updateById(priorityId, request));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/admin/{priorityId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long priorityId) throws NotFoundException {
        return ResponseEntity.ok(priorityService.deleteById(priorityId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(priorityService.findAll());
    }
}
