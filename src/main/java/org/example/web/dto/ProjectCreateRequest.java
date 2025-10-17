package org.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.example.validation.DateRangeValidator;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.example.domain.ProjectType;

@Data
@DateRangeValidator(startDateField = "startDate", endDateField = "endDate",
                    message = "End date must be after or equal to start date")
public class ProjectCreateRequest {
    @NotBlank
    private String name;

    private String description;

    @JsonProperty("plan_year")
    @NotNull
    private Integer planYear;

    @JsonProperty("start_date")
    @NotNull
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull
    private LocalDate endDate;

    private String status;

    @JsonProperty("lead_department")
    @NotBlank
    private String leadDepartment;

    @JsonProperty("lead_staff")
    @NotBlank
    private String leadStaff;

    @JsonProperty("project_manager")
    @NotBlank
    private String projectManager;

    @NotBlank
    private String requirement;

    private String options;

    @JsonProperty("project_type")
    @NotNull
    private ProjectType projectType;

    @JsonProperty("investment_budget")
    @NotNull
    @DecimalMin(value = "0", message = "investmentBudget must be >= 0")
    private BigDecimal investmentBudget;

    @NotBlank
    private String currency;

    @JsonProperty("plan_type")
    @NotBlank
    private String planType;
}
