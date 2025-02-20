package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.LabelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, Long> {
    @Query("SELECT l FROM LabelEntity l " +
            "JOIN FETCH l.user u " +
            "JOIN FETCH u.roles r " +
            "WHERE (:name IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%') ) ) " +
            "AND (:color IS NULL OR LOWER(l.color) LIKE LOWER(CONCAT('%', :color, '%') ) )")
    Page<LabelEntity> findAllFilteredAndPaginated(String name, String color, Pageable pageable);

    @Query("SELECT l FROM LabelEntity l " +
            "JOIN FETCH l.user u " +
            "WHERE l.id = :labelId AND u.id = :userId")
    Optional<LabelEntity> findByIdAndUserId(Long labelId, Long userId);

    @Override
    @Query("SELECT l FROM LabelEntity l " +
            "JOIN FETCH l.user u " +
            "JOIN FETCH u.roles r " +
            "WHERE l.id = :labelId")
    Optional<LabelEntity> findById(Long labelId);

    List<LabelEntity> findAllByUserId(Long userId);

    boolean existsByNameAndUserId(String name, Long userId);

    Optional<LabelEntity> findByNameAndUserIdAndIdIsNot(String name, Long userId, Long labelId);

    List<LabelEntity> findAllByIdInAndUserId(List<Long> ids, Long userId);
}
