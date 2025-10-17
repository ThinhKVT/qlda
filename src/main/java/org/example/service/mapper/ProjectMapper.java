package org.example.service.mapper;

import org.example.domain.Project;
import org.example.web.dto.ProjectCreateRequest;
import org.example.web.dto.ProjectDetailResponse;
import org.example.web.dto.DocumentSummary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for converting between Project entity and DTOs
 */
@Component
public class ProjectMapper {

    /**
     * Convert ProjectCreateRequest to Project entity
     */
    public Project toEntity(ProjectCreateRequest request) {
        if (request == null) {
            return null;
        }

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setPlanYear(request.getPlanYear());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setStatus(request.getStatus());
        project.setLeadDepartment(request.getLeadDepartment());
        project.setLeadStaff(request.getLeadStaff());
        project.setProjectManager(request.getProjectManager());
        project.setRequirement(request.getRequirement());
        project.setOptions(request.getOptions());
        project.setProjectType(request.getProjectType());
        project.setInvestmentBudget(request.getInvestmentBudget());
        project.setCurrency(request.getCurrency());
        project.setPlanType(request.getPlanType());

        return project;
    }

    /**
     * Convert Project entity to ProjectDetailResponse with documents
     */
    public ProjectDetailResponse toDetailResponse(Project project, List<DocumentSummary> documents) {
        if (project == null) {
            return null;
        }

        ProjectDetailResponse response = new ProjectDetailResponse();
        response.setId(project.getId());
        response.setProjectCode(project.getCode());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setPlanYear(project.getPlanYear());
        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());
        response.setStatus(project.getStatus());
        response.setDeleted(project.getDeleted());
        response.setLeadDepartment(project.getLeadDepartment());
        response.setLeadStaff(project.getLeadStaff());
        response.setProjectManager(project.getProjectManager());
        response.setRequirement(project.getRequirement());
        response.setOptions(project.getOptions());
        response.setProjectType(project.getProjectType());
        response.setInvestmentBudget(project.getInvestmentBudget());
        response.setCurrency(project.getCurrency());
        response.setPlanType(project.getPlanType());
        response.setDocs(documents != null ? documents : List.of());

        return response;
    }

    /**
     * Update existing project entity from update data
     * Only updates non-null and non-blank fields
     */
    public void updateEntity(Project existing, Project updates) {
        if (updates == null) {
            return;
        }

        // Không cập nhật id, code
        if (updates.getName() != null && !updates.getName().isBlank()) {
            existing.setName(updates.getName());
        }
        if (updates.getDescription() != null) {
            existing.setDescription(updates.getDescription());
        }
        if (updates.getPlanYear() != null) {
            existing.setPlanYear(updates.getPlanYear());
        }
        if (updates.getStatus() != null && !updates.getStatus().isBlank()) {
            existing.setStatus(updates.getStatus());
        }
        if (updates.getLeadDepartment() != null && !updates.getLeadDepartment().isBlank()) {
            existing.setLeadDepartment(updates.getLeadDepartment());
        }
        if (updates.getLeadStaff() != null && !updates.getLeadStaff().isBlank()) {
            existing.setLeadStaff(updates.getLeadStaff());
        }
        if (updates.getProjectManager() != null && !updates.getProjectManager().isBlank()) {
            existing.setProjectManager(updates.getProjectManager());
        }
        if (updates.getRequirement() != null && !updates.getRequirement().isBlank()) {
            existing.setRequirement(updates.getRequirement());
        }
        if (updates.getOptions() != null && !updates.getOptions().isBlank()) {
            existing.setOptions(updates.getOptions());
        }
        if (updates.getStartDate() != null) {
            existing.setStartDate(updates.getStartDate());
        }
        if (updates.getEndDate() != null) {
            existing.setEndDate(updates.getEndDate());
        }
        if (updates.getDeleted() != null) {
            existing.setDeleted(updates.getDeleted());
        }
        if (updates.getProjectType() != null) {
            existing.setProjectType(updates.getProjectType());
        }
        if (updates.getInvestmentBudget() != null) {
            existing.setInvestmentBudget(updates.getInvestmentBudget());
        }
        if (updates.getCurrency() != null && !updates.getCurrency().isBlank()) {
            existing.setCurrency(updates.getCurrency());
        }
        if (updates.getPlanType() != null && !updates.getPlanType().isBlank()) {
            existing.setPlanType(updates.getPlanType());
        }
    }
}

