package com.sgtech.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ApplicantLoansStatus {
    private Long applicantId;
    private String applicantName;
    private long numberOfLoans;
    private long numberOfFullyPaidLoans;
    private BigDecimal notPaidAmount;
}
