package com.sgtech.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LoanPayment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LoanPayment extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LoanId", nullable = false)
    private Loan loan;

    @Column(name = "PayDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date payDate;

    @Column(name = "PayAmount", nullable = false)
    private BigDecimal payAmount;

}
