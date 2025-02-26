package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.TaskFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.request.TaskRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import com.lag.todoapp.todoapp.model.response.LabelDto;
import com.lag.todoapp.todoapp.model.response.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    Page<TaskDto> findAllFilteredAndPaginated(TaskFilter filters, Pageable pageable);

    TaskDto findByIdAdmin(Long taskId) throws NotFoundException;

    Page<TaskDto> findAllPaginated(Pageable pageable, CustomUserDetails userDetails);

    TaskDto findById(Long taskId, CustomUserDetails userDetails) throws NotFoundException;

    TaskDto create(TaskRequest request) throws NotFoundException;

    TaskDto updateById(Long taskId, TaskRequest request, CustomUserDetails userDetails) throws NotFoundException;

    TaskDto deleteById(Long taskId, CustomUserDetails userDetails) throws NotFoundException;

    List<LabelDto> addLabel(Long taskId, Long labelId, CustomUserDetails userDetails) throws NotFoundException;

    List<LabelDto> removeLabel(Long taskId, Long labelId, CustomUserDetails userDetails) throws NotFoundException;

    CommentDto addComment(CommentRequest request, CustomUserDetails userDetails) throws NotFoundException;

    CommentDto removeComment(Long taskId, Long commentId, CustomUserDetails userDetails) throws NotFoundException;

    byte[] generateExcelReport(CustomUserDetails userDetails) throws IOException;
}
