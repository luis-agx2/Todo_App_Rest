package com.lag.todoapp.todoapp.model.request;

import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.validation.FieldExist;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class RegisterRequest {
    @NotBlank
    @FieldExist(entiy = UserEntity.class, field = "email")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @FieldExist(entiy = UserEntity.class, field = "nickname")
    private String nickname;

    private Set<Long> roles;

    @NotBlank
    private String firstName;

    private String lastName;

    private Integer age;

    public RegisterRequest() {
    }

    public RegisterRequest(String email,
                           String password,
                           String nickname,
                           Set<Long> roles,
                           String firstName,
                           String lastName,
                           Integer age) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "RegisterRequest[" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", roles=" + roles +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", age=" + age +
                ']';
    }
}
