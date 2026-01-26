package org.example.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Data
@Entity
@Table(name = "packages")
public class TenderPackage {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "description")
    private String description;
}
