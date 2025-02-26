package com.lag.todoapp.todoapp.service;

import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.CommentFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Page<CommentDto> findAllFilteredAndPaginated(CommentFilter filters, Pageable pageable);

    CommentDto findByIdAdmin(Long commentId) throws NotFoundException;

    CommentDto updateById(Long commentId, CommentRequest request, CustomUserDetails userDetails) throws NotFoundException;

    List<CommentDto> findAllMe(CustomUserDetails userDetails);

    CommentDto findById(Long taskId, CustomUserDetails userDetails) throws NotFoundException;
}
