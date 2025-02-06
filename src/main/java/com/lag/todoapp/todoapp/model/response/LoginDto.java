package com.lag.todoapp.todoapp.model.response;

public class LoginDto {
    private String jwt;

    public LoginDto() {
    }

    public LoginDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "LoginResponse[" +
                "jwt='" + jwt + '\'' +
                ']';
    }
}
