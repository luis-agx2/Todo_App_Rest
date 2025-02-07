package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("SELECT r FROM RoleEntity r " +
            "WHERE (:term IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :term, '%')))" +
            "AND (:createdAt IS NULL OR FUNCTION('DATE', r.createdAt) = :createdAt) ")
    Page<RoleEntity> findAllFiltered(String term, LocalDate createdAt, Pageable pageable);

    Optional<RoleEntity> findByNameAndIdIsNot(String name, Long roleId);
}
