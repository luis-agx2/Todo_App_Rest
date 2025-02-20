package com.lag.todoapp.todoapp.model.filter;

import java.time.LocalDate;

public class TaskFilter {
    private String title;

    private Long priorityId;

    private Long statusId;

    private Long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    public TaskFilter() {
    }

    public TaskFilter(String title,
                      Long priorityId,
                      Long statusId,
                      Long userId,
                      LocalDate startDate,
                      LocalDate endDate) {
        this.title = title;
        this.priorityId = priorityId;
        this.statusId = statusId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TaskFilter{" +
                "title='" + title + '\'' +
                ", priorityId=" + priorityId +
                ", statusId=" + statusId +
                ", userId=" + userId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
