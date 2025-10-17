package org.example.service;

import org.example.domain.*;
import org.example.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProjectServiceCascadeDeleteTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TenderPackageRepository packageRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private DocumentRepository documentRepository;

    private Project testProject;
    private TenderPackage testPackage;
    private Contract testContract;
    private Document projectDoc;
    private Document packageDoc;
    private Document contractDoc;

    @BeforeEach
    void setup() {
        // Create test project
        testProject = new Project();
        testProject.setName("Test Project for Cascade Delete");
        testProject.setPlanYear(2025);
        testProject.setStartDate(LocalDate.now());
        testProject.setEndDate(LocalDate.now().plusMonths(6));
        testProject.setLeadDepartment("IT");
        testProject.setLeadStaff("John Doe");
        testProject.setProjectManager("Jane Smith");
        testProject.setRequirement("Test requirement");
        testProject.setProjectType(ProjectType.XAY_DUNG);
        testProject.setInvestmentBudget(BigDecimal.valueOf(100000));
        testProject.setCurrency("VND");
        testProject.setPlanType("Annual");
        testProject = projectService.create(testProject);

        // Create test package
        testPackage = new TenderPackage();
        testPackage.setProject(testProject);
        testPackage.setName("Test Package");
        testPackage.setDeleted(false);
        testPackage = packageRepository.save(testPackage);

        // Create test contract
        testContract = new Contract();
        testContract.setTenderPackage(testPackage);
        testContract.setNumber("CONTRACT-001");
        testContract.setSignedDate(LocalDate.now());
        testContract.setEffectiveDate(LocalDate.now());
        testContract.setDurationMonths(12);
        testContract.setExpireDate(LocalDate.now().plusMonths(12));
        testContract.setValue(50000L);
        testContract.setCurrency("VND");
        testContract.setBidderName("Test Bidder");
        testContract.setType("Fixed Price");
        testContract.setPaymentInstallments(3);
        testContract.setDeleted(false);
        testContract = contractRepository.save(testContract);

        // Create documents at different levels
        projectDoc = new Document();
        projectDoc.setProjectId(testProject.getId());
        projectDoc.setName("Project Document");
        projectDoc.setPath("/docs/project.pdf");
        projectDoc.setDeleted(false);
        projectDoc = documentRepository.save(projectDoc);

        packageDoc = new Document();
        packageDoc.setPackageId(testPackage.getId());
        packageDoc.setName("Package Document");
        packageDoc.setPath("/docs/package.pdf");
        packageDoc.setDeleted(false);
        packageDoc = documentRepository.save(packageDoc);

        contractDoc = new Document();
        contractDoc.setContractId(testContract.getId());
        contractDoc.setName("Contract Document");
        contractDoc.setPath("/docs/contract.pdf");
        contractDoc.setDeleted(false);
        contractDoc = documentRepository.save(contractDoc);
    }

    @Test
    void testCascadeSoftDeleteProject() {
        // Perform soft delete
        projectService.softDelete(testProject.getId());

        // Verify project is soft deleted
        Project deletedProject = projectRepository.findById(testProject.getId()).orElseThrow();
        assertTrue(deletedProject.getDeleted(), "Project should be soft deleted");

        // Verify package is soft deleted
        TenderPackage deletedPackage = packageRepository.findById(testPackage.getId()).orElseThrow();
        assertTrue(deletedPackage.getDeleted(), "Package should be soft deleted");

        // Verify contract is soft deleted
        Contract deletedContract = contractRepository.findById(testContract.getId()).orElseThrow();
        assertTrue(deletedContract.getDeleted(), "Contract should be soft deleted");

        // Verify all documents are soft deleted
        Document deletedProjectDoc = documentRepository.findById(projectDoc.getId()).orElseThrow();
        assertTrue(deletedProjectDoc.getDeleted(), "Project document should be soft deleted");

        Document deletedPackageDoc = documentRepository.findById(packageDoc.getId()).orElseThrow();
        assertTrue(deletedPackageDoc.getDeleted(), "Package document should be soft deleted");

        Document deletedContractDoc = documentRepository.findById(contractDoc.getId()).orElseThrow();
        assertTrue(deletedContractDoc.getDeleted(), "Contract document should be soft deleted");
    }

    @Test
    void testCascadeDoesNotAffectOtherProjects() {
        // Create another project with its own data
        Project otherProject = new Project();
        otherProject.setName("Other Project");
        otherProject.setPlanYear(2025);
        otherProject.setStartDate(LocalDate.now());
        otherProject.setEndDate(LocalDate.now().plusMonths(3));
        otherProject.setLeadDepartment("Finance");
        otherProject.setLeadStaff("Alice");
        otherProject.setProjectManager("Bob");
        otherProject.setRequirement("Other requirement");
        otherProject.setProjectType(ProjectType.MUA_SAM);
        otherProject.setInvestmentBudget(BigDecimal.valueOf(50000));
        otherProject.setCurrency("USD");
        otherProject.setPlanType("Quarterly");
        otherProject = projectService.create(otherProject);

        // Soft delete first project
        projectService.softDelete(testProject.getId());

        // Verify other project is not affected
        Project unaffectedProject = projectRepository.findById(otherProject.getId()).orElseThrow();
        assertFalse(unaffectedProject.getDeleted() != null && unaffectedProject.getDeleted(),
                    "Other project should not be affected");
    }

    @Test
    void testFindByDeletedFalseExcludesSoftDeletedEntities() {
        // Soft delete the project
        projectService.softDelete(testProject.getId());

        // Query for non-deleted entities
        var activePackages = packageRepository.findByProject_IdAndDeletedFalse(testProject.getId());
        assertTrue(activePackages.isEmpty(), "Should not return soft deleted packages");

        var activeContracts = contractRepository.findByTenderPackage_IdAndDeletedFalse(testPackage.getId());
        assertTrue(activeContracts.isEmpty(), "Should not return soft deleted contracts");

        var activeProjectDocs = documentRepository.findByProjectIdAndDeletedFalse(testProject.getId());
        assertTrue(activeProjectDocs.isEmpty(), "Should not return soft deleted project documents");
    }
}
