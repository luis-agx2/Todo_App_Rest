package com.lag.todoapp.todoapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name", unique = true)
    private String nickName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;

    @Column(name = "account_no_locked")
    private boolean accountNoLocked;

    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserEntity() {
    }

    public UserEntity(Long id, String nickName, String email, String password, Set<RoleEntity> roles, boolean isEnabled, boolean accountNoExpired, boolean accountNoLocked, boolean credentialNoExpired) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isEnabled = isEnabled;
        this.accountNoExpired = accountNoExpired;
        this.accountNoLocked = accountNoLocked;
        this.credentialNoExpired = credentialNoExpired;
    }

    public UserEntity(Long id, String nickName, String email, String password, Set<RoleEntity> roles, boolean isEnabled, boolean accountNoExpired, boolean accountNoLocked, boolean credentialNoExpired, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isEnabled = isEnabled;
        this.accountNoExpired = accountNoExpired;
        this.accountNoLocked = accountNoLocked;
        this.credentialNoExpired = credentialNoExpired;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isAccountNoExpired() {
        return accountNoExpired;
    }

    public void setAccountNoExpired(boolean accountNoExpired) {
        this.accountNoExpired = accountNoExpired;
    }

    public boolean isAccountNoLocked() {
        return accountNoLocked;
    }

    public void setAccountNoLocked(boolean accountNoLocked) {
        this.accountNoLocked = accountNoLocked;
    }

    public boolean isCredentialNoExpired() {
        return credentialNoExpired;
    }

    public void setCredentialNoExpired(boolean credentialNoExpired) {
        this.credentialNoExpired = credentialNoExpired;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserEntity[" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", isEnabled=" + isEnabled +
                ", accountNoExpired=" + accountNoExpired +
                ", accountNoLocked=" + accountNoLocked +
                ", credentialNoExpired=" + credentialNoExpired +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ']';
    }
}
