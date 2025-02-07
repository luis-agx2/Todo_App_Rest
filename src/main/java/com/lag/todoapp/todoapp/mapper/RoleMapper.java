package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.RoleEntity;
import com.lag.todoapp.todoapp.model.request.RoleRequest;
import com.lag.todoapp.todoapp.model.response.RoleDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class RoleMapper {
    public List<RoleDto> toDtoList(List<RoleEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public RoleDto toDto(RoleEntity entity) {
        return new RoleDto(
                entity.getId(),
                entity.getName()
        );
    }

    public RoleEntity toEntity(RoleRequest request) {
        RoleEntity role = new RoleEntity();

        role.setName(request.getName());

        return role;
    }

    public RoleEntity toEntity(RoleRequest request, RoleEntity entity) {
        RoleEntity role = new RoleEntity();

        role.setId(entity.getId());
        role.setCreatedAt(entity.getCreatedAt());
        role.setName(updateValueIfExist(entity::getName, request::getName));

        return role;
    }

    private <T> T updateValueIfExist(Supplier<T> value, Supplier<T> newValue) {
        return newValue.get() != null
                ? newValue.get()
                : value.get();
    }
}
