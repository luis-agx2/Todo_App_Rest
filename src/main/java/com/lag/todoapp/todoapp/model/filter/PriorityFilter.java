package com.lag.todoapp.todoapp.model.filter;

public class PriorityFilter {
    private String name;

    public PriorityFilter() {
    }

    public PriorityFilter(String name) {
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
        return "PriorityFilter[" +
                "name='" + name + '\'' +
                ']';
    }
}
