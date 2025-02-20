package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.TaskEntity;
import com.lag.todoapp.todoapp.model.request.TaskRequest;
import com.lag.todoapp.todoapp.model.response.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

@Component
public class TaskMapper {
    private final UserMapper userMapper;

    private final LabelMapper labelMapper;

    private final PriorityMapper priorityMapper;

    private final CommentMapper commentMapper;
    private final StatusMapper statusMapper;

    @Autowired
    public TaskMapper(UserMapper userMapper,
                      LabelMapper labelMapper,
                      PriorityMapper priorityMapper,
                      CommentMapper commentMapper, StatusMapper statusMapper) {
        this.userMapper = userMapper;
        this.labelMapper = labelMapper;
        this.priorityMapper = priorityMapper;
        this.commentMapper = commentMapper;
        this.statusMapper = statusMapper;
    }

    public List<TaskDto> toDtoListAdmin(List<TaskEntity> entities) {
        return entities.stream()
                .map(this::toDtoAdmin)
                .toList();
    }

    public TaskDto toDtoAdmin(TaskEntity entity) {
        TaskDto dto = new TaskDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setUser(entity.getUser() != null ? userMapper.toDtoWitOutRoles(entity.getUser()) : null);
        dto.setPriority(entity.getPriority() != null ? priorityMapper.toDto(entity.getPriority()) : null);
        dto.setStatus(entity.getStatus() != null ? statusMapper.toDto(entity.getStatus()) : null);
        dto.setLabels(entity.getLabels() != null ? labelMapper.toDtosListAdmin(entity.getLabels().stream().toList()) : null);
        dto.setComments(entity.getComments() != null ? commentMapper.toCommentDtoList(entity.getComments().stream().toList()) : null);

        return dto;
    }

    public List<TaskDto> toDtoList(List<TaskEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public TaskDto toDto(TaskEntity entity) {
        TaskDto dto = new TaskDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setUser(null);
        dto.setStatus(entity.getStatus() != null ? statusMapper.toDto(entity.getStatus()) : null);
        dto.setPriority(entity.getPriority() != null ? priorityMapper.toDto(entity.getPriority()) : null);
        dto.setLabels(entity.getLabels() != null ? labelMapper.toDtosList(entity.getLabels().stream().toList()) : null);
        dto.setComments(entity.getComments() != null ? commentMapper.toCommentDtoList(entity.getComments().stream().toList()) : null);

        return dto;
    }

    public TaskEntity toEntity(TaskRequest request) {
        TaskEntity entity = new TaskEntity();

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());

        return entity;
    }

    public TaskEntity toEntity(TaskRequest request, TaskEntity entity) {
        TaskEntity taskToCreate = new TaskEntity();

        taskToCreate.setId(entity.getId());
        taskToCreate.setUser(entity.getUser());
        taskToCreate.setCreatedAt(entity.getCreatedAt());

        taskToCreate.setTitle(updateValueIfExists(entity::getTitle, request::getTitle));
        taskToCreate.setDescription(updateValueIfExists(entity::getDescription, request::getDescription));

        return taskToCreate;
    }

    private <T> T updateValueIfExists(Supplier<T> value, Supplier<T> newValue) {
        return newValue.get() != null
                ? newValue.get()
                : value.get();
    }
}
