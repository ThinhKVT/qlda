package org.example.service;

import org.example.domain.Project;
import org.example.repo.ProjectRepository;
import org.example.repo.TenderPackageRepository;
import org.example.repo.ContractRepository;
import org.example.repo.DocumentRepository;
import org.example.common.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class ProjectService {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    private final TenderPackageRepository packageRepository;
    private final ContractRepository contractRepository;
    private final DocumentRepository documentRepository;

    public ProjectService(ProjectRepository projectRepository,
                         TenderPackageRepository packageRepository,
                         ContractRepository contractRepository,
                         DocumentRepository documentRepository) {
        this.projectRepository = projectRepository;
        this.packageRepository = packageRepository;
        this.contractRepository = contractRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public Project create(Project p) {
        log.info("Creating new project with name: {}", p.getName());

        // Generate project code: DA-{planYear}-{STT}
        String prefix = Constants.Project.CODE_PREFIX + p.getPlanYear() + "-";
        long seq = projectRepository.countByCodeStartingWith(prefix) + 1;
        String code = prefix + String.format(Constants.Project.CODE_FORMAT, seq);

        p.setCode(code);
        p.setDeleted(false);

        Project saved = projectRepository.save(p);
        log.info("Created project with code: {} and id: {}", saved.getCode(), saved.getId());

        return saved;
    }

    @Transactional
    public Project update(Project existing) {
        log.info("Updating project with id: {}", existing.getId());
        Project updated = projectRepository.save(existing);
        log.info("Updated project with code: {}", updated.getCode());
        return updated;
    }

    @Transactional
    public void softDelete(UUID projectId) {
        log.info("Soft deleting project with id: {}", projectId);

        // Soft delete project
        projectRepository.findById(projectId).ifPresent(p -> {
            p.setDeleted(true);
            projectRepository.save(p);
            log.debug("Marked project {} as deleted", p.getCode());
        });

        // Cascade delete related entities
        cascadeDeletePackages(projectId);
        cascadeDeleteProjectDocuments(projectId);

        log.info("Completed soft delete cascade for project: {}", projectId);
    }

    @Transactional
    public void softDelete(Project p) {
        softDelete(p.getId());
    }

    /**
     * Cascade soft delete all packages and their related entities
     */
    private void cascadeDeletePackages(UUID projectId) {
        var packages = packageRepository.findByProject_IdAndDeletedFalse(projectId);

        if (packages.isEmpty()) {
            log.debug("No packages found for project: {}", projectId);
            return;
        }

        log.debug("Found {} packages to delete for project: {}", packages.size(), projectId);

        packages.forEach(pkg -> {
            pkg.setDeleted(true);
            cascadeDeleteContracts(pkg.getId());
            cascadeDeletePackageDocuments(pkg.getId());
        });

        packageRepository.saveAll(packages);
        log.debug("Soft deleted {} packages", packages.size());
    }

    /**
     * Cascade soft delete all contracts and their documents
     */
    private void cascadeDeleteContracts(UUID packageId) {
        var contracts = contractRepository.findByTenderPackage_IdAndDeletedFalse(packageId);

        if (contracts.isEmpty()) {
            return;
        }

        log.debug("Found {} contracts to delete for package: {}", contracts.size(), packageId);

        contracts.forEach(contract -> {
            contract.setDeleted(true);
            cascadeDeleteContractDocuments(contract.getId());
        });

        contractRepository.saveAll(contracts);
        log.debug("Soft deleted {} contracts", contracts.size());
    }

    /**
     * Cascade soft delete all documents linked to a contract
     */
    private void cascadeDeleteContractDocuments(UUID contractId) {
        var docs = documentRepository.findByContractIdAndDeletedFalse(contractId);

        if (!docs.isEmpty()) {
            docs.forEach(doc -> doc.setDeleted(true));
            documentRepository.saveAll(docs);
            log.debug("Soft deleted {} contract documents", docs.size());
        }
    }

    /**
     * Cascade soft delete all documents linked to a package
     */
    private void cascadeDeletePackageDocuments(UUID packageId) {
        var docs = documentRepository.findByPackageIdAndDeletedFalse(packageId);

        if (!docs.isEmpty()) {
            docs.forEach(doc -> doc.setDeleted(true));
            documentRepository.saveAll(docs);
            log.debug("Soft deleted {} package documents", docs.size());
        }
    }

    /**
     * Cascade soft delete all documents linked directly to project
     */
    private void cascadeDeleteProjectDocuments(UUID projectId) {
        var docs = documentRepository.findByProjectIdAndDeletedFalse(projectId);

        if (!docs.isEmpty()) {
            docs.forEach(doc -> doc.setDeleted(true));
            documentRepository.saveAll(docs);
            log.debug("Soft deleted {} project documents", docs.size());
        }
    }
}
