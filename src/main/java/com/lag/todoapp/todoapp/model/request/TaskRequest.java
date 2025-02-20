package com.lag.todoapp.todoapp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class TaskRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Set<Long> labelsId = new HashSet<>();

    private Long statusId;

    @NotNull
    private Long priorityId;

    public TaskRequest() {
    }

    public TaskRequest(String title,
                       String description,
                       Set<Long> labelsId,
                       Long statusId,
                       Long priorityId) {
        this.title = title;
        this.description = description;
        this.labelsId = labelsId;
        this.statusId = statusId;
        this.priorityId = priorityId;
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

    public Set<Long> getLabelsId() {
        return labelsId;
    }

    public void setLabelsId(Set<Long> labelsId) {
        this.labelsId = labelsId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    @Override
    public String toString() {
        return "TaskRequest[" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", labelsId=" + labelsId +
                ", statusId=" + statusId +
                ", priorityId=" + priorityId +
                ']';
    }
}
