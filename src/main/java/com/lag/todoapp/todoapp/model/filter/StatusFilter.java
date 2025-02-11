package com.lag.todoapp.todoapp.model.filter;

public class StatusFilter {
    private String name;

    public StatusFilter() {
    }

    public StatusFilter(String name) {
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
        return "StatusFilter[" +
                "name='" + name + '\'' +
                ']';
    }
}
