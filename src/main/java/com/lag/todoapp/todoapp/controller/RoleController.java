package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.model.filter.RoleFilter;
import com.lag.todoapp.todoapp.model.request.RoleRequest;
import com.lag.todoapp.todoapp.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Object> getAllAdmin(@RequestParam (required = false) String name,
                                              @RequestParam (required = false) LocalDate createdAt,
                                              Pageable pageable) {
        RoleFilter filters = new RoleFilter(name, createdAt);

        return ResponseEntity.ok(roleService.findAllFiltered(filters, pageable));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin/{roleId}")
    public ResponseEntity<Object> getByIdAdmin(@PathVariable Long roleId) throws NotFoundException {
        return ResponseEntity.ok(roleService.findById(roleId));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<Object> createByAdmin(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(request));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/admin/{roleId}")
    public ResponseEntity<Object> updateByAdmin(@RequestBody RoleRequest request, @PathVariable Long roleId) throws NotFoundException, ValidationErrorException {
        return ResponseEntity.ok(roleService.update(roleId, request));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/admin/{roleId}")
    public ResponseEntity<Object> deleteByIdAdmin(@PathVariable Long roleId) throws NotFoundException {
        return ResponseEntity.ok(roleService.deleteById(roleId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(roleService.findAll());
    }
}
