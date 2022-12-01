package com.sgtech.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanDetailsResponse {
    private Long loanId;
    private String loanType;
    private BigDecimal amount;
    private String currency;
    private Date startDate;
    private Date endDate;
    private BigDecimal interestRate;
    private String loanStatus;
    private List<LoanPaymentDetails> loanPaymentDetailsList;
}
