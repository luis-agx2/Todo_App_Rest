package com.lag.todoapp.todoapp.model.request;

import com.lag.todoapp.todoapp.entity.RoleEntity;
import com.lag.todoapp.todoapp.validation.FieldExist;
import jakarta.validation.constraints.NotBlank;

public class RoleRequest {
    @NotBlank
    @FieldExist(entiy = RoleEntity.class, field = "name", message = "This role is already registered.")
    private String name;

    public RoleRequest() {
    }

    public RoleRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleRequest[" +
                "name='" + name + '\'' +
                ']';
    }
}
