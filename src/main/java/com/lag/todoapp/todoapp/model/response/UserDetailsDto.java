package com.lag.todoapp.todoapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserDetailsDto {
    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto userDto;

    public UserDetailsDto() {
    }

    public UserDetailsDto(Long id,
                          String firstName,
                          String lastName,
                          Integer age,
                          UserDto userDto) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userDto = userDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public String toString() {
        return "UserDetailsResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", userDto=" + userDto +
                '}';
    }
}
