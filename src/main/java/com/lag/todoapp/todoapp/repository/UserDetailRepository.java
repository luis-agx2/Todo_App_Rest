package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {
    Optional<UserDetailEntity> findByUserId(Long userId);
}
