package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.filter.CommentFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Object> getAllFilteredAndPaginated(@RequestParam (required = false) String title,
                                                             @RequestParam (required = false) Long userId,
                                                             @RequestParam (required = false)LocalDate createdAt,
                                                             Pageable pageable) {
        CommentFilter filters = new CommentFilter(title, userId, createdAt);

        return ResponseEntity.ok(commentService.findAllFilteredAndPaginated(filters, pageable));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin/{commentId}")
    public ResponseEntity<Object> getByIdAdmin(@PathVariable Long commentId) throws NotFoundException {
        return ResponseEntity.ok(commentService.findByIdAdmin(commentId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(commentService.findAllMe());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{commentId}")
    public ResponseEntity<Object> getById(@PathVariable Long commentId) throws NotFoundException {
        return ResponseEntity.ok(commentService.findById(commentId));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CommentRequest request) throws NotFoundException {
        return ResponseEntity.ok(commentService.create(request));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{commentId}")
    public ResponseEntity<Object> updateById(@RequestBody CommentRequest request, @PathVariable Long commentId) throws NotFoundException {
        return ResponseEntity.ok(commentService.updateById(commentId, request));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long commentId) throws NotFoundException {
        return ResponseEntity.ok(commentService.deleteById(commentId));
    }
}
