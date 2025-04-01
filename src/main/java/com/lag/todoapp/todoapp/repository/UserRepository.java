package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.roles r " +
            "LEFT JOIN UserDetailEntity ud " +
            "ON ud.user.id = u.id " +
            "WHERE (:nickName IS NULL OR LOWER(u.nickname) LIKE LOWER(CONCAT('%', :nickName, '%') ) )" +
            "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%') ) )" +
            "AND (:nameTerm IS NULL OR (LOWER(CONCAT(ud.firstName, ' ', ud.lastName)) LIKE LOWER(CONCAT('%', :nameTerm, '%') ) OR LOWER(CONCAT(ud.lastName, ' ', ud.firstName)) LIKE LOWER(CONCAT('%', :nameTerm, '%') )))" +
            "AND (:roleIds IS NULL OR r.id in :roleIds)")
    Page<UserEntity> findAllFilteredAndPageable(String nickName, String email, String nameTerm, Set<Long> roleIds, Pageable pageable);

    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.roles r " +
            "WHERE u.email = :email")
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByNicknameAndIdIsNot(String nickName, Long userId);
}
