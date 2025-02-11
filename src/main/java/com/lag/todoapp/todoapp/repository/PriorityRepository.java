package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.PriorityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<PriorityEntity, Long> {
    @Query("SELECT p FROM PriorityEntity p " +
            "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<PriorityEntity> findAllFilteredAndPaginated(String name, Pageable pageable);

    Optional<PriorityEntity> findByNameAndIdIsNot(String name, Long priorityId);
}
