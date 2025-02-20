package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.LabelEntity;
import com.lag.todoapp.todoapp.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t FROM TaskEntity t " +
            "LEFT JOIN FETCH t.user u " +
            "LEFT JOIN FETCH t.priority p " +
            "LEFT JOIN FETCH t.status s " +
            "LEFT JOIN FETCH t.labels l " +
            "LEFT JOIN FETCH t.comments c " +
            "WHERE (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%') ) ) " +
            "AND (:priorityId IS NULL OR t.priority.id = :priorityId) " +
            "AND (:statusId IS NULL OR t.status.id = :statusId) " +
            "AND (:userId IS NULL OR t.user.id = :userId) " +
            "AND ((:startDate IS NULL OR :endDate IS  NULL) OR FUNCTION('DATE', t.createdAt) BETWEEN :startDate AND :endDate)")
    Page<TaskEntity> findAllFilteredAndPaginated(String title,
                                                 Long priorityId,
                                                 Long statusId,
                                                 Long userId,
                                                 LocalDate startDate,
                                                 LocalDate endDate,
                                                 Pageable pageable);

    @Query("SELECT t FROM TaskEntity t " +
            "LEFT JOIN FETCH t.user u " +
            "LEFT JOIN FETCH t.priority p " +
            "LEFT JOIN FETCH t.status s " +
            "LEFT JOIN FETCH t.labels l " +
            "LEFT JOIN FETCH t.comments c " +
            "WHERE t.id = :taskId")
    Optional<TaskEntity> findByIdAdmin(Long taskId);

    @Query("SELECT t from TaskEntity t " +
            "LEFT JOIN FETCH t.user u " +
            "LEFT JOIN FETCH t.priority p " +
            "LEFT JOIN FETCH t.status s " +
            "LEFT JOIN FETCH t.labels " +
            "LEFT JOIN FETCH t.comments " +
            "WHERE u.id = :userId")
    Page<TaskEntity> findAllPaginatedByUser(Long userId, Pageable pageable);

    @Query("SELECT t from TaskEntity t " +
            "LEFT JOIN FETCH t.user u " +
            "LEFT JOIN FETCH t.priority p " +
            "LEFT JOIN FETCH t.status s " +
            "LEFT JOIN FETCH t.labels " +
            "LEFT JOIN FETCH t.comments " +
            "WHERE t.id = :taskId AND u.id = :userId")
    Optional<TaskEntity> findByIdAndUserId(Long taskId, Long userId);
}
