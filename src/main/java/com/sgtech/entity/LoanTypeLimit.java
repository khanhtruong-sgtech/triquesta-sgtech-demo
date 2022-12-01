package com.sgtech.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "LoanTypeLimit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LoanTypeLimit extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "CreditFacilityId", nullable = false)
    private CreditFacility creditFacility;

    @ManyToOne
    @JoinColumn(name = "LoanTypeId", nullable = false)
    private LoanType loanType;

    @Column(name = "LimitAmount", nullable = false)
    private BigDecimal limitAmount;

}
