package com.sgtech.service;

import com.sgtech.dto.request.CreditFacilityCreateRequest;
import com.sgtech.dto.response.CommonResponse;
import com.sgtech.dto.response.CreditFacilityCreateResponse;
import com.sgtech.entity.CreditFacility;

public interface CreditFacilityService {

    CreditFacilityCreateResponse createCreditFacility(CreditFacilityCreateRequest request);

    CreditFacility getCreditFacility(Long id);

}
