package com.lag.todoapp.todoapp.model.request;

import com.lag.todoapp.todoapp.entity.PriorityEntity;
import com.lag.todoapp.todoapp.validation.FieldExist;
import jakarta.validation.constraints.NotBlank;

public class PriorityRequest {
    @NotBlank
    @FieldExist(entiy = PriorityEntity.class, field = "name")
    private String name;

    public PriorityRequest() {
    }

    public PriorityRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PriorityRequest[" +
                "name='" + name + '\'' +
                ']';
    }
}
