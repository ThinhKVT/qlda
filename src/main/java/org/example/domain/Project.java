package org.example.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    // Mã dự án tự sinh: DA-{planYear}-{STT}
    @Column(name = "code")
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "plan_year")
    private Integer planYear;

    private String status;

    // Phòng đầu mối
    @Column(name = "lead_department")
    private String leadDepartment;
    // Cán bộ đầu mối
    @Column(name = "lead_staff")
    private String leadStaff;
    // Cán bộ QLDA
    @Column(name = "project_manager")
    private String projectManager;

    // requirement/ options lưu dạng JSON (text)
    @Column(columnDefinition = "TEXT")
    private String requirement;
    @Column(columnDefinition = "TEXT")
    private String options;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    // soft delete
    @Column(name = "deleted")
    private Boolean deleted = false;

    // New fields
    @Enumerated(EnumType.STRING)
    @Column(name = "project_type")
    private ProjectType projectType;

    @Column(name = "investment_budget")
    private BigDecimal investmentBudget;

    private String currency;

    @Column(name = "plan_type")
    private String planType;
}
