package org.example.repo;

import org.example.domain.TenderPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TenderPackageRepository extends JpaRepository<TenderPackage, UUID> {
    List<TenderPackage> findByProject_Id(UUID projectId);
    List<TenderPackage> findByProject_IdAndDeletedFalse(UUID projectId);
}
