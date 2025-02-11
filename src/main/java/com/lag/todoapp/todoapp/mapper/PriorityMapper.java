package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.PriorityEntity;
import com.lag.todoapp.todoapp.model.request.PriorityRequest;
import com.lag.todoapp.todoapp.model.response.PriorityDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class PriorityMapper {
    public List<PriorityDto> toDtoList(List<PriorityEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public PriorityDto toDto(PriorityEntity entity) {
        return new PriorityDto(
                entity.getId(),
                entity.getName()
        );
    }

    public PriorityEntity toEntity(PriorityRequest request) {
        PriorityEntity entity = new PriorityEntity();

        entity.setName(request.getName());

        return entity;
    }

    public PriorityEntity toEntity(PriorityRequest request, PriorityEntity entity) {
        PriorityEntity entityToUpdate = new PriorityEntity();

        entityToUpdate.setId(entity.getId());
        entityToUpdate.setCreatedAt(entity.getCreatedAt());

        entityToUpdate.setName(updateValueIfExist(entity::getName, request::getName));

        return entityToUpdate;
    }

    private <T> T updateValueIfExist(Supplier<T> value, Supplier<T> newValue) {
        return newValue.get() != null
                ? newValue.get()
                : value.get();
    }
}
