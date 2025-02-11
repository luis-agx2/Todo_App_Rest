package com.lag.todoapp.todoapp.repository;

import com.lag.todoapp.todoapp.entity.StatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    @Query("SELECT s FROM StatusEntity s " +
            "WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<StatusEntity> findAllFilteredAndPaginated(String name, Pageable pageable);

    Optional<StatusEntity> findByNameAndIdIsNot(String name, Long statusId);
}
