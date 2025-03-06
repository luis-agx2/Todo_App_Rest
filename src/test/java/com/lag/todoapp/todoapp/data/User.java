package com.lag.todoapp.todoapp.data;

import com.lag.todoapp.todoapp.entity.RoleEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;

import java.util.Set;

public class User {
    public static UserEntity getUser() {
        return new UserEntity(
                1L,
                "my_nick",
                "email@test.com",
                "123456",
                Set.of(new RoleEntity(1L, "Role_1")),
                true,
                true,
                true,
                true
        );
    }
}
