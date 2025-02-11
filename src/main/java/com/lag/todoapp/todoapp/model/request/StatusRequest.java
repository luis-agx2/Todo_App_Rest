package com.lag.todoapp.todoapp.model.request;

import com.lag.todoapp.todoapp.entity.StatusEntity;
import com.lag.todoapp.todoapp.validation.FieldExist;
import jakarta.validation.constraints.NotBlank;

public class StatusRequest {
    @NotBlank
    @FieldExist(entiy = StatusEntity.class, field = "name")
    private String name;

    public StatusRequest() {
    }

    public StatusRequest(String name) {
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
        return "StatusRequest[" +
                "name='" + name + '\'' +
                ']';
    }
}
