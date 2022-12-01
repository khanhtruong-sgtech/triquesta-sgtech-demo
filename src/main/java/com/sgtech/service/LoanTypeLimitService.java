package com.sgtech.service;

import java.math.BigDecimal;

public interface LoanTypeLimitService {

    BigDecimal getLimitAmount(Long creditFacilityId, Long loanTypeId);

}
