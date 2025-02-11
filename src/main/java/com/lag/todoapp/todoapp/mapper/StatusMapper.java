package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.StatusEntity;
import com.lag.todoapp.todoapp.model.request.StatusRequest;
import com.lag.todoapp.todoapp.model.response.StatusDto;
import com.lag.todoapp.todoapp.service.StatusService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class StatusMapper {
    public List<StatusDto> toEntitiesList(List<StatusEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public StatusDto toDto(StatusEntity entity) {
        return new StatusDto(
                entity.getId(),
                entity.getName()
        );
    }

    public StatusEntity toEntity(StatusRequest request) {
        StatusEntity entity = new StatusEntity();

        entity.setName(request.getName());

        return entity;
    }

    public StatusEntity toEntity(StatusRequest request, StatusEntity entity) {
        StatusEntity entityToEdit = new StatusEntity();

        entityToEdit.setId(entity.getId());
        entityToEdit.setCreatedAt(entity.getCreatedAt());

        entityToEdit.setName(updateValueIfExists(entity::getName, request::getName));

        return entityToEdit;
    }

    private <T> T updateValueIfExists(Supplier<T> value, Supplier<T> newValue) {
        return newValue.get() != null
                ? newValue.get()
                : value.get();
    }
}
