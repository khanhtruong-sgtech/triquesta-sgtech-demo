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
public class CreditFacilityCreateRequest {

    @NotNull(message = "NULL not allowed for field applicantId")
    private Long applicantId;

    @NotNull(message = "NULL not allowed for field creditFacilityType")
    private String creditFacilityType;

    @NotNull(message = "NULL not allowed for field totalLimit")
    private BigDecimal totalLimit;

    @NotNull(message = "NULL not allowed for field currency")
    private String currency;

    @NotNull(message = "NULL not allowed for field startDate")
    private Date startDate;

    @NotNull(message = "NULL not allowed for field endDate")
    private Date endDate;

    @NotEmpty(message = "EMPTY not allowed for list loanLimits")
    private List<LoanTypeLimitRequest> loanTypeLimitRequests;
}
