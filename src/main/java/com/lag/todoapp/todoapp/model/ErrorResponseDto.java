package com.lag.todoapp.todoapp.model;

public class ErrorResponseDto {
    private Integer code;

    private String message;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(Integer code,
                            String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponseDto[" +
                "code=" + code +
                ", message='" + message + '\'' +
                ']';
    }
}
