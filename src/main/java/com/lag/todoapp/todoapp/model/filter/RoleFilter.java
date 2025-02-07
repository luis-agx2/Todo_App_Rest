package com.lag.todoapp.todoapp.model.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoleFilter {
    private String name;

    private LocalDate createdAt;

    public RoleFilter() {
    }

    public RoleFilter(String name, LocalDate createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RoleFilter[" +
                "name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ']';
    }
}
