package com.lag.todoapp.todoapp.model.filter;

import java.time.LocalDate;

public class CommentFilter {
    private String title;

    private Long userId;

    private LocalDate createdAt;

    public CommentFilter() {
    }

    public CommentFilter(String title,
                         Long userId,
                         LocalDate createdAt) {
        this.title = title;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CommentFilter[" +
                "title='" + title + '\'' +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ']';
    }
}
