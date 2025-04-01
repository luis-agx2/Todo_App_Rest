package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.model.request.UserRequest;
import com.lag.todoapp.todoapp.model.response.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class UserMapper {
    private final RoleMapper roleMapper;

    @Autowired
    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<UserDto> toDtoListWithRoles(List<UserEntity> entityList) {
        return entityList.stream()
                .map(this::toDtoWitOutRoles)
                .toList();
    }

    public UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setRoles(roleMapper.toDtoList(entity.getRoles().stream().toList()));

        return dto;
    }

    public UserDto toDtoWitOutRoles(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setRoles(null);

        return dto;
    }

    public UserEntity toEntity(UserRequest request, UserEntity entity) {
        UserEntity user = new UserEntity();

        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setEnabled(entity.isEnabled());
        user.setCreatedAt(entity.getCreatedAt());
        user.setCredentialNoExpired(entity.isCredentialNoExpired());
        user.setAccountNoExpired(entity.isAccountNoExpired());
        user.setAccountNoLocked(entity.isAccountNoLocked());

        user.setNickname(updateValueIfExists(request::getNickname, entity::getNickname));

        return user;
    }

    private <T> T updateValueIfExists(Supplier<T> newValue, Supplier<T> currentValue) {
        return newValue.get() != null
                ? newValue.get()
                : currentValue.get();
    }
}
