package com.lag.todoapp.todoapp.mapper;

import com.lag.todoapp.todoapp.entity.UserDetailEntity;
import com.lag.todoapp.todoapp.model.request.UserDetailsRequest;
import com.lag.todoapp.todoapp.model.response.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserDetailMapper {
    private final UserMapper userMapper;

    @Autowired
    public UserDetailMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDetailsDto toDtoAdmin(UserDetailEntity entity) {
        UserDetailsDto dto = new UserDetailsDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(entity.getAge());
        dto.setUserDto(userMapper.toDto(entity.getUser()));

        return dto;
    }

    public UserDetailsDto toDto(UserDetailEntity entity) {
        UserDetailsDto dto = toDtoAdmin(entity);
        dto.setUserDto(userMapper.toDtoWitOutRoles(entity.getUser()));

        return dto;
    }

    public UserDetailEntity toEntity(UserDetailsRequest request, UserDetailEntity entity) {
        UserDetailEntity userDetail = new UserDetailEntity();

        userDetail.setId(entity.getId());
        userDetail.setCreatedAt(entity.getCreatedAt());

        userDetail.setFirstName(updateIfValueExists(entity::getFirstName, request::getFirstName));
        userDetail.setLastName(updateIfValueExists(entity::getLastName, request::getLastName));
        userDetail.setAge(updateIfValueExists(entity::getAge, request::getAge));

        return userDetail;
    }

    private <T> T updateIfValueExists(Supplier<T> newValue, Supplier<T> value) {
        return newValue.get() != null
                ? newValue.get()
                : value.get();
    }
}
