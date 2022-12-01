package com.sgtech.entity;

import com.sgtech.enums.CreditFacilityType;
import com.sgtech.enums.CurrencyType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CreditFacility")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CreditFacility extends BaseEntity {

    @Column(name = "CreditFacilityType", nullable = false, columnDefinition = "nvarchar(max)")
    @Enumerated(EnumType.STRING)
    private CreditFacilityType creditFacilityType;

    @Column(name = "TotalLimit", nullable = false)
    private BigDecimal totalLimit;

    @Column(name = "CurrencyType", nullable = false, columnDefinition = "nvarchar(max)")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "StartDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date startDate;

    @Column(name = "EndDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date endDate;

    @ManyToOne
    @JoinColumn(name = "ApplicantId", nullable = false)
    private Applicant applicant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditFacility")
    private List<Loan> loans;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditFacility")
    private List<LoanTypeLimit> loanTypeLimits;

}
