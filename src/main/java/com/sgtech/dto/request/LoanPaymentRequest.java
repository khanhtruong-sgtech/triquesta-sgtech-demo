package com.sgtech.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanPaymentRequest {

    @NotNull(message = "NULL not allowed for field payAmount")
    private BigDecimal payAmount;

}
