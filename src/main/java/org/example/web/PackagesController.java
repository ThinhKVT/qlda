package org.example.web;

import org.example.domain.Contract;
import org.example.domain.Project;
import org.example.domain.TenderPackage;
import org.example.repo.ContractRepository;
import org.example.repo.ProjectRepository;
import org.example.repo.TenderPackageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
public class PackagesController {
    private final ProjectRepository projectRepository;
    private final TenderPackageRepository tenderPackageRepository;
    private final ContractRepository contractRepository;

    public PackagesController(ProjectRepository projectRepository, TenderPackageRepository tenderPackageRepository, ContractRepository contractRepository) {
        this.projectRepository = projectRepository;
        this.tenderPackageRepository = tenderPackageRepository;
        this.contractRepository = contractRepository;
    }

    @GetMapping("/api/projects/{projectId}/packages")
    public ResponseEntity<List<TenderPackage>> list(@PathVariable UUID projectId) {
        if (!projectRepository.existsById(projectId)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tenderPackageRepository.findByProject_Id(projectId));
    }

    @PostMapping("/api/projects/{projectId}/packages")
    public ResponseEntity<TenderPackage> create(@PathVariable UUID projectId, @Valid @RequestBody TenderPackage pkg) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) return ResponseEntity.notFound().build();
        pkg.setProject(project);
        return ResponseEntity.ok(tenderPackageRepository.save(pkg));
    }

    @GetMapping("/api/packages/{id}")
    public ResponseEntity<TenderPackage> get(@PathVariable UUID id) {
        return tenderPackageRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/packages/{id}")
    public ResponseEntity<TenderPackage> update(@PathVariable UUID id, @RequestBody TenderPackage in) {
        return tenderPackageRepository.findById(id)
                .map(ex -> {
                    ex.setName(in.getName());
                    return ResponseEntity.ok(tenderPackageRepository.save(ex));
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/packages/{id}/contract")
    public ResponseEntity<Contract> getContract(@PathVariable UUID id) {
        return contractRepository.findByTenderPackage_Id(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/packages/{id}/contract")
    public ResponseEntity<Contract> saveContract(@PathVariable UUID id, @Valid @RequestBody Contract in) {
        TenderPackage pkg = tenderPackageRepository.findById(id).orElse(null);
        if (pkg == null) return ResponseEntity.notFound().build();
        Contract c = contractRepository.findByTenderPackage_Id(id).orElseGet(Contract::new);
        c.setTenderPackage(pkg);
        c.setNumber(in.getNumber());
        c.setSignedDate(in.getSignedDate());
        c.setEffectiveDate(in.getEffectiveDate());
        c.setDurationMonths(in.getDurationMonths());
        c.setExpireDate(in.getExpireDate());
        c.setValue(in.getValue());
        c.setCurrency(in.getCurrency());
        c.setBidderName(in.getBidderName());
        c.setType(in.getType());
        c.setPaymentInstallments(in.getPaymentInstallments());
        return ResponseEntity.ok(contractRepository.save(c));
    }
}
