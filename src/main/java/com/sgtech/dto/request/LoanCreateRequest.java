package com.sgtech.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanCreateRequest {
    @NotNull(message = "NULL not allowed for field creditFacilityId")
    private Long creditFacilityId;

    @NotNull(message = "NULL not allowed for field loanTypeId")
    private Long loanTypeId;

    @NotNull(message = "NULL not allowed for field amount")
    private BigDecimal amount;

    @NotNull(message = "NULL not allowed for field currency")
    private String currency;

    @NotNull(message = "NULL not allowed for field startDate")
    private Date startDate;

    @NotNull(message = "NULL not allowed for field endDate")
    private Date endDate;

    @NotNull(message = "NULL not allowed for field interestRate")
    private BigDecimal interestRate;

}
