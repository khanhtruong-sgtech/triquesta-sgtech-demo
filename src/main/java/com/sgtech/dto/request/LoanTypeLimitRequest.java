package com.sgtech.dto.request;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanTypeLimitRequest {
    private Long loanTypeId;
    private BigDecimal limitAmount;
}
