package com.lag.todoapp.todoapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskDto {
    private Long id;

    private String title;

    private String description;

    private List<LabelDto> labels = new ArrayList<>();

    private PriorityDto priority;

    private StatusDto status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;

    private List<CommentDto> comments = new ArrayList<>();

    public TaskDto() {
    }

    public TaskDto(Long id,
                   String title,
                   String description,
                   List<LabelDto> labels,
                   PriorityDto priority,
                   StatusDto status,
                   UserDto user,
                   List<CommentDto> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.labels = labels;
        this.priority = priority;
        this.status = status;
        this.user = user;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LabelDto> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelDto> labels) {
        this.labels = labels;
    }

    public PriorityDto getPriority() {
        return priority;
    }

    public void setPriority(PriorityDto priority) {
        this.priority = priority;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "TaskDto[" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", labels=" + labels +
                ", priority=" + priority +
                ", status=" + status +
                ", user=" + user +
                ", comments=" + comments +
                ']';
    }
}
