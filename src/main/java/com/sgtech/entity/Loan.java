package com.sgtech.entity;

import com.sgtech.enums.CurrencyType;
import com.sgtech.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Loan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Loan extends BaseEntity{

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "CurrencyType", nullable = false, columnDefinition = "nvarchar(255)")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "StartDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "EndDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "InterestRate", nullable = false)
    private BigDecimal interestRate;

    @ManyToOne
    @JoinColumn(name = "CreditFacilityId", nullable = false)
    private CreditFacility creditFacility;

    @ManyToOne
    @JoinColumn(name = "LoanTypeId", nullable = false)
    private LoanType loanType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "loan")
    private List<LoanPayment> loanPayments;

    @Column(name = "LoanStatus", nullable = false, columnDefinition = "nvarchar(255)")
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

}
