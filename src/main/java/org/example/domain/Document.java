package org.example.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Data
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "package_id")
    private UUID packageId;

    @Column(name = "contract_id")
    private UUID contractId;

    @Column(name = "payment_id")
    private UUID paymentId;

    private String type;
    private String name;
    private String path;
    private Long size;

    @Column(name = "deleted")
    private Boolean deleted = false;
}
