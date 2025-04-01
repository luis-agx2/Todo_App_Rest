package com.lag.todoapp.todoapp.model.filter;

import java.util.Set;

public class UserFilter {
    private String nickName;

    private String email;

    private String nameTerm;

    private Set<Long> roleIds;

    public UserFilter() {
    }

    public UserFilter(String nickName,
                      String email,
                      String nameTerm,
                      Set<Long> roleIds) {
        this.nickName = nickName;
        this.email = email;
        this.nameTerm = nameTerm;
        this.roleIds = roleIds;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameTerm() {
        return nameTerm;
    }

    public void setNameTerm(String nameTerm) {
        this.nameTerm = nameTerm;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "UserFilter{" +
                "nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", nameTerm='" + nameTerm + '\'' +
                ", roleIds=" + roleIds +
                '}';
    }
}
