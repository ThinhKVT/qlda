package org.example.bootstrap;

import org.example.domain.Contract;
import org.example.domain.Project;
import org.example.domain.TenderPackage;
import org.example.repo.ContractRepository;
import org.example.repo.ProjectRepository;
import org.example.repo.TenderPackageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DevDataLoader implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final TenderPackageRepository tenderPackageRepository;
    private final ContractRepository contractRepository;

    public DevDataLoader(ProjectRepository projectRepository,
                         TenderPackageRepository tenderPackageRepository,
                         ContractRepository contractRepository) {
        this.projectRepository = projectRepository;
        this.tenderPackageRepository = tenderPackageRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (projectRepository.count() > 0) return;

        for (int i = 1; i <= 3; i++) {
            Project p = new Project();
            p.setCode("DA2025%03d".formatted(i));
            p.setName("Dự án mẫu " + i);
            p.setPlanYear(2025);
            p.setStatus(i % 2 == 0 ? "DANG_THUC_HIEN" : "MOI");
            p.setStartDate(LocalDate.of(2025, 1, i));
            p.setEndDate(LocalDate.of(2025, 12, Math.min(28, i)));
            p = projectRepository.save(p);

            TenderPackage pkg = new TenderPackage();
            pkg.setProject(p);
            pkg.setName("Gói thầu " + i);
            pkg = tenderPackageRepository.save(pkg);

            Contract c = new Contract();
            c.setTenderPackage(pkg);
            c.setNumber("HD-2025/%03d".formatted(i));
            c.setSignedDate(LocalDate.of(2025, 10, 1));
            c.setEffectiveDate(LocalDate.of(2025, 10, 1));
            c.setDurationMonths(12);
            c.setExpireDate(LocalDate.of(2026, 10, 1));
            c.setValue(1_000_000_000L * i);
            c.setCurrency("VND");
            c.setBidderName("Công ty XYZ " + i);
            c.setType("Trọn gói");
            c.setPaymentInstallments(3);
            contractRepository.save(c);
        }
    }
}

