package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.entity.*;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.mapper.CommentMapper;
import com.lag.todoapp.todoapp.mapper.LabelMapper;
import com.lag.todoapp.todoapp.mapper.TaskMapper;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.TaskFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.request.TaskRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import com.lag.todoapp.todoapp.model.response.LabelDto;
import com.lag.todoapp.todoapp.model.response.TaskDto;
import com.lag.todoapp.todoapp.repository.*;
import com.lag.todoapp.todoapp.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final StatusRepository statusRepository;

    private final PriorityRepository priorityRepository;

    private final UserRepository userRepository;

    private final LabelRepository labelRepository;

    private final LabelMapper labelMapper;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskMapper taskMapper,
                           StatusRepository statusRepository,
                           PriorityRepository priorityRepository,
                           UserRepository userRepository,
                           LabelRepository labelRepository,
                           LabelMapper labelMapper, CommentMapper commentMapper, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.statusRepository = statusRepository;
        this.priorityRepository = priorityRepository;
        this.userRepository = userRepository;
        this.labelRepository = labelRepository;
        this.labelMapper = labelMapper;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<TaskDto> findAllFilteredAndPaginated(TaskFilter filters, Pageable pageable) {
        Page<TaskEntity> result = taskRepository.findAllFilteredAndPaginated(filters.getTitle(),
                filters.getPriorityId(),
                filters.getStatusId(),
                filters.getUserId(),
                filters.getStartDate(),
                filters.getEndDate(),
                pageable);

        return new PageImpl<>(
                taskMapper.toDtoListAdmin(result.getContent()),
                pageable,
                result.getTotalElements()
        );
    }

    @Override
    public TaskDto findByIdAdmin(Long taskId) throws NotFoundException {
        TaskEntity entity = taskRepository.findByIdAdmin(taskId).orElseThrow(() -> new NotFoundException("Task not found"));

        return taskMapper.toDtoAdmin(entity);
    }

    @Override
    public Page<TaskDto> findAllPaginated(Pageable pageable) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<TaskEntity> results = taskRepository.findAllPaginatedByUser(userDetails.getId(), pageable);

        return new PageImpl<>(
                taskMapper.toDtoList(results.getContent()),
                pageable,
                results.getTotalElements()
        );
    }

    @Override
    public TaskDto findById(Long taskId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity entity = taskRepository.findByIdAndUserId(taskId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Task not found"));

        return taskMapper.toDto(entity);
    }

    @Transactional
    @Override
    public TaskDto create(TaskRequest request) throws NotFoundException {
        TaskEntity entityToCreate = toEntityForCreate(request);

        return taskMapper.toDto(taskRepository.save(entityToCreate));
    }

    @Transactional
    @Override
    public TaskDto updateById(Long taskId, TaskRequest request) throws NotFoundException {
        TaskEntity entityToUpdate = toEntityForUpdate(taskId, request);

        return taskMapper.toDto(taskRepository.save(entityToUpdate));
    }

    @Transactional
    @Override
    public TaskDto deleteById(Long taskId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskEntity entity = taskRepository.findByIdAndUserId(taskId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Task not found"));

        taskRepository.delete(entity);

        return taskMapper.toDto(entity);
    }

    @Transactional
    @Override
    public List<LabelDto> addLabel(Long taskId, Long labelId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskEntity entity = taskRepository.findByIdAndUserId(taskId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Task not found"));

        boolean containLabel = entity.getLabels().stream().anyMatch(label -> label.getId().equals(labelId));

        if (!containLabel) {
            LabelEntity label = labelRepository.findByIdAndUserId(labelId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Label not found"));

            entity.getLabels().add(label);
        }

        TaskEntity entityUpdated = taskRepository.save(entity);

        return labelMapper.toDtosList(entityUpdated.getLabels().stream().toList());
    }

    @Transactional
    @Override
    public List<LabelDto> removeLabel(Long taskId, Long labelId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskEntity entity = taskRepository.findByIdAndUserId(taskId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Task not found"));

        entity.getLabels().removeIf(label -> label.getId().equals(labelId));

        TaskEntity updatedTask = taskRepository.save(entity);

        return labelMapper.toDtosList(updatedTask.getLabels().stream().toList());
    }

    @Override
    public CommentDto addComment(CommentRequest request) throws NotFoundException {
        TaskEntity entity = taskRepository.findByIdAdmin(request.getTaskId()).orElseThrow(() -> new NotFoundException("Task not found"));
        CommentEntity comment = saveComment(request);

        entity.getComments().add(comment);
        taskRepository.save(entity);

        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto removeComment(Long taskId, Long commentId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity task = taskRepository.findByIdAndUserId(taskId, userDetails.getId()).orElseThrow(() -> new NotFoundException("Task not found"));

        CommentEntity commentToDelete = task.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId) && comment.getUser().getId().equals(userDetails.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        task.getComments().remove(commentToDelete);

        taskRepository.save(task);

        return commentMapper.toDtoMe(commentToDelete);
    }

    private TaskEntity toEntityForCreate(TaskRequest request) throws NotFoundException {
        TaskEntity entity = taskMapper.toEntity(request);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(getStatus(request.getStatusId()));
        entity.setPriority(getPriority(request.getPriorityId()));
        entity.setUser(getLoggedUser());
        entity.setLabels(getLabels(request.getLabelsId()));

        return entity;
    }

    private TaskEntity toEntityForUpdate(Long taskId, TaskRequest request) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity entity = taskRepository.findByIdAndUserId(taskId, userDetails.getId()).orElseThrow(() -> new NotFoundException("User not found"));

        TaskEntity entityToUpdate = taskMapper.toEntity(request, entity);
        entityToUpdate.setUpdatedAt(LocalDateTime.now());
        entityToUpdate.setUser(entity.getUser());
        entityToUpdate.setComments(entity.getComments());

        entityToUpdate.setStatus(resolveProperty(request.getStatusId(), entity.getStatus(), StatusEntity::getId, this::getStatus));
        entityToUpdate.setPriority(resolveProperty(request.getPriorityId(), entity.getPriority(), PriorityEntity::getId, this::getPriority));
        entityToUpdate.setLabels(getLabels(request.getLabelsId()));

        return entityToUpdate;
    }

    private StatusEntity getStatus(Long statusId) throws NotFoundException {
        return statusRepository.findById(statusId).orElseThrow(() -> new NotFoundException("Status not found"));
    }

    private PriorityEntity getPriority(Long priorityId) throws NotFoundException {
        return priorityRepository.findById(priorityId).orElseThrow(() -> new NotFoundException("Priority not found"));
    }

    private UserEntity getLoggedUser() throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private <T, I> T resolveProperty(I requestId,
                                      T currentProperty,
                                      Function<T, I> idExtractor,
                                      Function<I, T> fetcher) {
        if (requestId == null) {
            return currentProperty;
        }

        if (currentProperty == null || !idExtractor.apply(currentProperty).equals(requestId)) {
            return fetcher.apply(requestId);
        }

        return currentProperty;
    }

    private Set<LabelEntity> getLabels(Set<Long> labelsId) throws NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<LabelEntity> entities = labelRepository.findAllByIdInAndUserId(labelsId.stream().toList(), userDetails.getId());

        if (entities.size() < labelsId.size()) {
            throw new NotFoundException("Some label not was found");
        }

        return new HashSet<>(entities);
    }

    private CommentEntity saveComment(CommentRequest request) {
        CommentEntity comment = commentMapper.toEntity(request);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(getLoggedUser());

        return commentRepository.save(comment);
    }
}
