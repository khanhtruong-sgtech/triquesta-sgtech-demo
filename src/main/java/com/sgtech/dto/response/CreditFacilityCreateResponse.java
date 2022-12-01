package com.sgtech.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreditFacilityCreateResponse {
    private Long creditFacilityId;
    private String applicantName;
    private String creditFacilityType;
    private BigDecimal totalLimit;
    private String currency;
    private Date startDate;
    private Date endDate;
}
