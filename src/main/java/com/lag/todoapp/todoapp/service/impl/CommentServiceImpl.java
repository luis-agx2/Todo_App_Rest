package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.CommentEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.mapper.CommentMapper;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.CommentFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import com.lag.todoapp.todoapp.repository.CommentRepository;
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

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Page<CommentDto> findAllFilteredAndPaginated(CommentFilter filters, Pageable pageable) {
        Page<CommentEntity> result = commentRepository.findAllFilteredAndPaginated(filters.getTitle(), filters.getUserId(), filters.getCreatedAt(), pageable);

        return new PageImpl<>(
                commentMapper.toCommentDtoListAdmin(result.getContent()),
                pageable,
                result.getTotalElements()
        );
    }

    @Override
    public CommentDto findByIdAdmin(Long commentId) throws NotFoundException {
        CommentEntity entity = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found"));

        return commentMapper.toDtoAdmin(entity);
    }

    @Transactional
    @Override
    public CommentDto create(CommentRequest request) throws NotFoundException {
        CommentEntity commentToCreate = mapToEntity(request);
        commentToCreate.setCreatedAt(LocalDateTime.now());

        return commentMapper.toDto(commentRepository.save(commentToCreate));
    }

    @Transactional
    @Override
    public CommentDto updateById(Long commentId, CommentRequest request) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentEntity entity = commentRepository.findByIdAndUserId(commentId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Comment not found"));
        CommentEntity commentToCreate = commentMapper.toEntity(request, entity);
        commentToCreate.setUpdatedAt(LocalDateTime.now());

        return commentMapper.toDto(commentRepository.save(commentToCreate));
    }

    @Transactional
    @Override
    public CommentDto deleteById(Long commentId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentEntity entity = commentRepository.findByIdAndUserId(commentId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Comment not found"));
        commentRepository.delete(entity);

        return commentMapper.toDto(entity);
    }

    @Override
    public List<CommentDto> findAllMe() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return commentMapper.toCommentDtoList(commentRepository.findAllByUserId(userDetails.getId()));
    }

    @Override
    public CommentDto findById(Long commentId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentEntity entity = commentRepository.findByIdAndUserId(commentId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Comment not found"));

        return commentMapper.toDto(entity);
    }

    // TODO: Cuando este listo las task relacionar la task con el comment
    private CommentEntity mapToEntity(CommentRequest request) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new NotFoundException("User not found"));

        CommentEntity entity = commentMapper.toEntity(request);
        entity.setUser(user);

        return entity;
    }
}
