package com.lag.todoapp.todoapp.model.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDto {
    private Long id;

    private String nickname;

    private String email;

    private List<RoleDto> roles = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(Long id,
                   String nickname,
                   String email,
                   List<RoleDto> roles) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto[" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ']';
    }
}
