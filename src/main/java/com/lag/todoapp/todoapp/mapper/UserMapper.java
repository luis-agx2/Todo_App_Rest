package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.model.response.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final RoleMapper roleMapper;

    @Autowired
    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setRoles(roleMapper.toDtoList(entity.getRoles().stream().toList()));

        return dto;
    }

    UserDto toDtoWitOutRoles(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setRoles(null);

        return dto;
    }
}
