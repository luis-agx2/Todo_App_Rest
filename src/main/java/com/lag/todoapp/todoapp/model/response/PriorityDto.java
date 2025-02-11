package com.lag.todoapp.todoapp.model.response;

public class PriorityDto {
    private Long id;

    private String name;

    public PriorityDto() {
    }

    public PriorityDto(Long id,
                       String name) {
        this.id = id;
        this.name = name;
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
        return "PriorityDto[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ']';
    }
}
