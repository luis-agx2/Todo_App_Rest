package com.lag.todoapp.todoapp.model.response;

public class RoleDto {
    private Long id;

    private String name;

    public RoleDto() {
    }

    public RoleDto(Long id,
                   String name) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDto[" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ']';
    }
}
