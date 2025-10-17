package org.example.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "package_id")
    private TenderPackage tenderPackage;

    @NotBlank
    private String number;

    @NotNull
    @Column(name = "signed_date")
    private LocalDate signedDate;

    @NotNull
    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @NotNull
    @Min(1)
    @Column(name = "duration_months")
    private Integer durationMonths;

    @NotNull
    @Column(name = "expire_date")
    private LocalDate expireDate;

    @NotNull
    @Min(1)
    private Long value;

    @NotBlank
    private String currency;

    @NotBlank
    @Column(name = "bidder_name")
    private String bidderName;

    @NotBlank
    private String type;

    @NotNull
    @Min(1)
    @Column(name = "payment_installments")
    private Integer paymentInstallments;

    @Column(name = "deleted")
    private Boolean deleted = false;
}
