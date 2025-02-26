package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.CommentEntity;
import com.lag.todoapp.todoapp.entity.TaskEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.mapper.CommentMapper;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.CommentFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import com.lag.todoapp.todoapp.repository.CommentRepository;
import com.lag.todoapp.todoapp.repository.TaskRepository;
import com.lag.todoapp.todoapp.repository.UserRepository;
import com.lag.todoapp.todoapp.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              UserRepository userRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Page<CommentDto> findAllFilteredAndPaginated(CommentFilter filters, Pageable pageable) {
        Page<CommentEntity> result = commentRepository.findAllFilteredAndPaginated(filters.getTitle(), filters.getUserId(), filters.getCreatedAt(), pageable);

        return new PageImpl<>(
                commentMapper.toCommentDtoList(result.getContent()),
                pageable,
                result.getTotalElements()
        );
    }

    @Override
    public CommentDto findByIdAdmin(Long commentId) throws NotFoundException {
        CommentEntity entity = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found"));

        return commentMapper.toDto(entity);
    }

    @Transactional
    @Override
    public CommentDto updateById(Long commentId, CommentRequest request, CustomUserDetails userDetails) throws NotFoundException {
        CommentEntity entity = commentRepository.findByIdAndUserId(commentId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Comment not found"));
        CommentEntity commentToCreate = commentMapper.toEntity(request, entity);
        commentToCreate.setUpdatedAt(LocalDateTime.now());

        return commentMapper.toDto(commentRepository.save(commentToCreate));
    }

    @Override
    public List<CommentDto> findAllMe(CustomUserDetails userDetails) {
        return commentMapper.toCommentDtoListMe(commentRepository.findAllByUserId(userDetails.getId()));
    }

    @Override
    public CommentDto findById(Long commentId, CustomUserDetails userDetails) throws NotFoundException {
        CommentEntity entity = commentRepository.findByIdAndUserId(commentId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Comment not found"));

        return commentMapper.toDtoMe(entity);
    }
}
