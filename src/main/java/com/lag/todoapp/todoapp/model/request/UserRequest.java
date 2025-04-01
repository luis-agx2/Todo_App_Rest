package com.lag.todoapp.todoapp.model.request;


import java.util.HashSet;
import java.util.Set;

public class UserRequest {
    private String nickname;

    private String email;

    private String password;

    private Set<Long> roleIds = new HashSet<>();

    public UserRequest() {
    }

    public UserRequest(String nickname,
                       String email,
                       String password,
                       Set<Long> roleIds) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roleIds = roleIds;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleIds=" + roleIds +
                '}';
    }
}
