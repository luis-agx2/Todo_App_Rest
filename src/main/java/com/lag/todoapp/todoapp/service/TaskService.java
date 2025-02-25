package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
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

    Page<TaskDto> findAllPaginated(Pageable pageable);

    TaskDto findById(Long taskId) throws NotFoundException;

    TaskDto create(TaskRequest request) throws NotFoundException;

    TaskDto updateById(Long taskId, TaskRequest request) throws NotFoundException;

    TaskDto deleteById(Long taskId) throws NotFoundException;

    List<LabelDto> addLabel(Long taskId, Long labelId) throws NotFoundException;

    List<LabelDto> removeLabel(Long taskId, Long labelId) throws NotFoundException;

    CommentDto addComment(CommentRequest request) throws NotFoundException;

    CommentDto removeComment(Long taskId, Long commentId) throws NotFoundException;

    byte[] generateExcelReport() throws IOException;
}
