package com.sgtech.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanPaymentDetails {

    private Date payDate;

    private BigDecimal paidAmount;

}
