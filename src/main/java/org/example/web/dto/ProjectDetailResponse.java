package org.example.web.dto;

import lombok.Data;
import org.example.domain.ProjectType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectDetailResponse {
    private UUID id;
    private String projectCode;
    private String name;
    private String description;
    private Integer planYear;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Boolean deleted;
    private String leadDepartment;
    private String leadStaff;
    private String projectManager;
    private String requirement;
    private String options;
    private ProjectType projectType;
    private BigDecimal investmentBudget;
    private String currency;
    private String planType;
    private List<DocumentSummary> docs;
}
