package com.lag.todoapp.todoapp.controller;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.TaskFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.request.TaskRequest;
import com.lag.todoapp.todoapp.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Object> getAllAdmin(@RequestParam (required = false) String title,
                                               @RequestParam (required = false) Long priorityId,
                                               @RequestParam (required = false) Long statusId,
                                               @RequestParam (required = false) Long userId,
                                               @RequestParam (required = false) LocalDate startDate,
                                               @RequestParam (required = false) LocalDate endDate,
                                               Pageable pageable) {
        TaskFilter filters = new TaskFilter(title, priorityId, statusId, userId, startDate, endDate);

        return ResponseEntity.ok(taskService.findAllFilteredAndPaginated(filters, pageable));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin/{taskId}")
    public ResponseEntity<Object> getByIdAdmin(@PathVariable Long taskId) throws NotFoundException {
        return ResponseEntity.ok(taskService.findByIdAdmin(taskId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Object> getAll(Pageable pageable, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(taskService.findAllPaginated(pageable, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{taskIk}")
    public ResponseEntity<Object> getById(@PathVariable Long taskIk, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(taskService.findById(taskIk, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody TaskRequest request) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{taskId}")
    public ResponseEntity<Object> updateById(@RequestBody TaskRequest request, @PathVariable Long taskId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(taskService.updateById(taskId, request, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteById(@PathVariable Long taskId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(taskService.deleteById(taskId, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{taskId}/label/{labelId}")
    public ResponseEntity<Object> addLabelToTask(@PathVariable Long taskId, @PathVariable Long labelId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(taskService.addLabel(taskId, labelId, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{taskId}/label/{labelId}")
    public ResponseEntity<Object> removeLabelFromTask(@PathVariable Long taskId, @PathVariable Long labelId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(taskService.removeLabel(taskId, labelId, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment")
    public ResponseEntity<Object> addCommentToTask(@Valid @RequestBody CommentRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(taskService.addComment(request, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{taskId}/comment/{commentId}")
    public ResponseEntity<Object> removeCommentFomTask(@PathVariable Long taskId, @PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        return ResponseEntity.ok(taskService.removeComment(taskId, commentId, userDetails));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/report")
    public ResponseEntity<byte[]> downloadReport(@AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        byte[] excelBytes = taskService.generateExcelReport(userDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attach; filename=reporte.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

}
