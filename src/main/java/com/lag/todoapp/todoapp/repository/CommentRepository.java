package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c " +
            "JOIN FETCH c.user u " +
            "JOIN FETCH u.roles " +
            "WHERE (:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%') ) ) " +
            "AND (:userId IS NULL OR u.id = :userId) " +
            "AND (:createdAt IS NULL OR FUNCTION('DATE', c.createdAt) = :createdAt)")
    Page<CommentEntity> findAllFilteredAndPaginated(String title, Long userId, LocalDate createdAt, Pageable pageable);

    @Query("SELECT c FROM CommentEntity c " +
            "JOIN FETCH c.user u " +
            "JOIN FETCH u.roles r " +
            "WHERE c.id = :commentId")
    @Override
    Optional<CommentEntity> findById(Long commentId);

    @Query("SELECT c FROM CommentEntity c " +
            "JOIN FETCH c.user u " +
            "WHERE u.id = :userId")
    List<CommentEntity> findAllByUserId(Long userId);

    @Query("SELECT c FROM CommentEntity c " +
            "JOIN FETCH c.user u " +
            "WHERE c.id = :commentId " +
            "AND u.id = :userId")
    Optional<CommentEntity> findByIdAndUserId(Long commentId, Long userId);
}
