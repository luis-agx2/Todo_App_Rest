package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.LabelEntity;
import com.lag.todoapp.todoapp.model.request.LabelRequest;
import com.lag.todoapp.todoapp.model.response.LabelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class LabelMapper {
    private final UserMapper userMapper;

    @Autowired
    public LabelMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<LabelDto> toDtosListAdmin(List<LabelEntity> entities) {
        return entities.stream()
                .map(this::toDtoAdmin)
                .toList();
    }

    public LabelDto toDtoAdmin(LabelEntity entity) {
        LabelDto dto = new LabelDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setColor(entity.getColor());

        if (entity.getUser() != null) {
            dto.setUser(userMapper.toDto(entity.getUser()));
        }

        return dto;
    }

    public List<LabelDto> toDtosList(List<LabelEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public LabelDto toDto(LabelEntity entity) {
        LabelDto dto = new LabelDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setColor(entity.getColor());
        dto.setUser(null);

        return dto;
    }

    public LabelEntity toEntity(LabelRequest request) {
        LabelEntity entity = new LabelEntity();

        entity.setName(request.getName());
        entity.setColor(request.getColor());

        return entity;
    }

    public LabelEntity toEntity(LabelRequest request, LabelEntity entity) {
        LabelEntity entityToEdit = new LabelEntity();

        entityToEdit.setId(entity.getId());
        entityToEdit.setUser(entity.getUser());
        entityToEdit.setCreatedAt(entity.getCreatedAt());

        entityToEdit.setName(updateValueIfExists(entity::getName, request::getName));
        entityToEdit.setColor(updateValueIfExists(entity::getColor, request::getColor));

        return entityToEdit;
    }

    private <T> T updateValueIfExists(Supplier<T> value, Supplier<T> newValue) {
        return newValue.get() != null
                ? newValue.get()
                : value.get();
    }
}
