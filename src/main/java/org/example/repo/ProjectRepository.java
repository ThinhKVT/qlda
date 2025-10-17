package org.example.repo;

import org.example.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {
    Page<Project> findByPlanYear(Integer planYear, Pageable pageable);
    Page<Project> findByStatus(String status, Pageable pageable);
    Page<Project> findByPlanYearAndStatus(Integer planYear, String status, Pageable pageable);

    Optional<Project> findTopByPlanYearOrderByCodeDesc(Integer planYear);

    long countByCodeStartingWith(String codePrefix);
}
