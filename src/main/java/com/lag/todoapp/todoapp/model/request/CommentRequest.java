package com.lag.todoapp.todoapp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotNull
    private Long taskId;

    public CommentRequest() {
    }

    public CommentRequest(String title,
                          String message,
                          Long taskId) {
        this.title = title;
        this.message = message;
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "CommentRequest[" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", taskId='" + taskId + '\'' +
                ']';
    }
}
