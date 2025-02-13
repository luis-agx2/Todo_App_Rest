package com.lag.todoapp.todoapp.model.response;

public class CommentDto {
    private Long id;

    private String title;

    private String message;

    private UserDto user;

    public CommentDto() {
    }

    public CommentDto(Long id,
                      String title,
                      String message,
                      UserDto user) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.user = user;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDto getUserDto() {
        return user;
    }

    public void setUserDto(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommentDto[" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", user=" + user +
                ']';
    }
}
