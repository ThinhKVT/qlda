package org.example.repo;

import org.example.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
    Optional<Contract> findByTenderPackage_Id(UUID packageId);
    List<Contract> findByTenderPackage_IdAndDeletedFalse(UUID packageId);
}
