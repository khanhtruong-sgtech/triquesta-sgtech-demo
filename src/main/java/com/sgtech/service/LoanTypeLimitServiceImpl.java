package com.sgtech.service;

import com.sgtech.entity.LoanTypeLimit;
import com.sgtech.exception.ResourceNotFoundException;
import com.sgtech.repository.LoanTypeLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanTypeLimitServiceImpl implements LoanTypeLimitService {

    private final LoanTypeLimitRepository loanTypeLimitRepository;

    @Override
    public BigDecimal getLimitAmount(Long creditFacilityId, Long loanTypeId) {
        LoanTypeLimit loanTypeLimit = loanTypeLimitRepository.findByCreditFacility_IdAndLoanType_Id(creditFacilityId, loanTypeId);
        if (Objects.isNull(loanTypeLimit)) {
            throw new ResourceNotFoundException("LoanTypeLimit not found");
        }
        return loanTypeLimit.getLimitAmount();
    }

}
