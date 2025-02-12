package com.lag.todoapp.todoapp.model.response;

public class LabelDto {
    private Long id;

    private String name;

    private String color;

    private UserDto user;

    public LabelDto() {
    }

    public LabelDto(Long id,
                    String name,
                    String color,
                    UserDto user) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.user = user;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LabelDto[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", user=" + user +
                ']';
    }
}
