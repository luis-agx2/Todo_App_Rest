package com.lag.todoapp.todoapp.model.request;

import com.lag.todoapp.todoapp.validation.LabelExist;
import jakarta.validation.constraints.NotBlank;

public class LabelRequest {
    @NotBlank
    @LabelExist
    private String name;

    @NotBlank
    private String color;

    public LabelRequest() {
    }

    public LabelRequest(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "LabelRequest[" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ']';
    }
}
