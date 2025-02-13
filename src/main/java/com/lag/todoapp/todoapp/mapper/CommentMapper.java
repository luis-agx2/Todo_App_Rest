package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.CommentEntity;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class CommentMapper {
    private final UserMapper userMapper;

    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<CommentDto> toCommentDtoList(List<CommentEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public CommentDto toDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setUserDto(null);

        return dto;
    }

    public List<CommentDto> toCommentDtoListAdmin(List<CommentEntity> entities) {
        return entities.stream()
                .map(this::toDtoAdmin)
                .toList();
    }

    public CommentDto toDtoAdmin(CommentEntity entity) {
        CommentDto dto = new CommentDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());

        if (entity.getUser() != null) {
            dto.setUserDto(userMapper.toDto(entity.getUser()));
        }

        return dto;
    }

    public CommentEntity toEntity(CommentRequest request) {
        CommentEntity entity = new CommentEntity();

        entity.setTitle(request.getTitle());
        entity.setMessage(request.getMessage());

        return entity;
    }

    public CommentEntity toEntity(CommentRequest request, CommentEntity entity) {
        CommentEntity entityToEdit = new CommentEntity();

        entityToEdit.setId(entity.getId());
        entityToEdit.setUser(entity.getUser());
        entityToEdit.setTitle(updateValueIfExists(entity::getTitle, request::getTitle));
        entityToEdit.setMessage(updateValueIfExists(entity::getMessage, request::getMessage));
        entityToEdit.setCreatedAt(entity.getCreatedAt());

        return entityToEdit;
    }

    private <T> T updateValueIfExists(Supplier<T> value, Supplier<T> newValue) {
        return newValue.get() != null
                ? newValue.get()
                : value.get();
    }
}
