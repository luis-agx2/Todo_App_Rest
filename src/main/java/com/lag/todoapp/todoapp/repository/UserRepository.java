package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.roles r " +
            "WHERE u.email = :email")
    Optional<UserEntity> findByEmail(String email);
}
