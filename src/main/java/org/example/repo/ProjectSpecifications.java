package org.example.repo;

import org.example.domain.Project;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectSpecifications {
    public static Specification<Project> filter(
            String projectCode,
            List<String> statuses,
            LocalDate startDateFrom,
            LocalDate startDateTo,
            LocalDate endDateFrom,
            LocalDate endDateTo,
            String leadDepartment,
            String leadStaff,
            String projectManager,
            String requirementContains
    ) {
        return (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            // exclude deleted
            predicates.add(cb.isFalse(root.get("deleted")));
            if (projectCode != null && !projectCode.isBlank()) {
                predicates.add(cb.equal(root.get("code"), projectCode));
            }
            if (statuses != null && !statuses.isEmpty()) {
                var in = cb.in(root.get("status"));
                statuses.forEach(in::value);
                predicates.add(in);
            }
            if (startDateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDateFrom));
            }
            if (startDateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), startDateTo));
            }
            if (endDateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("endDate"), endDateFrom));
            }
            if (endDateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDateTo));
            }
            if (leadDepartment != null && !leadDepartment.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("leadDepartment")), "%" + leadDepartment.toLowerCase() + "%"));
            }
            if (leadStaff != null && !leadStaff.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("leadStaff")), "%" + leadStaff.toLowerCase() + "%"));
            }
            if (projectManager != null && !projectManager.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("projectManager")), "%" + projectManager.toLowerCase() + "%"));
            }
            if (requirementContains != null && !requirementContains.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("requirement")), "%" + requirementContains.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}

