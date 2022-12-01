package com.sgtech.service;

import com.sgtech.dto.request.CreditFacilityCreateRequest;
import com.sgtech.dto.request.LoanTypeLimitRequest;
import com.sgtech.dto.response.CreditFacilityCreateResponse;
import com.sgtech.entity.Applicant;
import com.sgtech.entity.CreditFacility;
import com.sgtech.entity.LoanTypeLimit;
import com.sgtech.enums.CreditFacilityType;
import com.sgtech.enums.CurrencyType;
import com.sgtech.exception.BadRequestException;
import com.sgtech.exception.ResourceNotFoundException;
import com.sgtech.repository.CreditFacilityRepository;
import com.sgtech.repository.LoanTypeLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditFacilityServiceImpl implements CreditFacilityService {

    private final CreditFacilityRepository creditFacilityRepository;
    private final LoanTypeLimitRepository loanTypeLimitRepository;
    private final ApplicantService applicantService;
    private final LoanTypeService loanTypeService;

    @Override
    @Transactional
    public CreditFacilityCreateResponse createCreditFacility(CreditFacilityCreateRequest request) {
        Applicant applicant = applicantService.findApplicantById(request.getApplicantId());

        List<CreditFacility> currentCreditFacilitiesOfThisApplicant = creditFacilityRepository.findAllByApplicant_Id(request.getApplicantId());
        if (!CollectionUtils.isEmpty(currentCreditFacilitiesOfThisApplicant) &&
                isCreditFacilityTypeExisted(request.getCreditFacilityType(), currentCreditFacilitiesOfThisApplicant)) {
            throw new BadRequestException("Credit Facility with Type " + request.getCreditFacilityType() + " is already created");
        }

        BigDecimal totalLimitOfEachLoanType = calculateTotalLimitAmount(request.getLoanTypeLimitRequests());
        if (totalLimitOfEachLoanType.compareTo(request.getTotalLimit()) == 1) {
            throw new BadRequestException("Total limits of each loan type shouldn't be greater than totalLimit");
        }

        CreditFacility creditFacility = buildCreditFacility(request, applicant);
        CreditFacility creditFacilityResult = creditFacilityRepository.save(creditFacility);

        List<LoanTypeLimit> loanTypeLimits = buildLoanTypeLimit(request.getLoanTypeLimitRequests(), creditFacilityResult);
        loanTypeLimitRepository.saveAll(loanTypeLimits);

        return buildCreditFacilityCreateResponse(creditFacilityResult);
    }

    @Override
    public CreditFacility getCreditFacility(Long id) {
        return creditFacilityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Credit facility not found"));
    }

    private boolean isCreditFacilityTypeExisted(String creditFacilityType, List<CreditFacility> currentCreditFacilities) {
        return currentCreditFacilities.stream()
                .anyMatch(creditFacility -> creditFacility.getCreditFacilityType() == CreditFacilityType.valueOf(creditFacilityType));
    }

    private CreditFacilityCreateResponse buildCreditFacilityCreateResponse(CreditFacility creditFacility) {
        return CreditFacilityCreateResponse.builder()
                .creditFacilityId(creditFacility.getId())
                .applicantName(buildFullName(creditFacility.getApplicant()))
                .creditFacilityType(creditFacility.getCreditFacilityType().toString())
                .totalLimit(creditFacility.getTotalLimit())
                .currency(creditFacility.getCurrency().toString())
                .startDate(creditFacility.getStartDate())
                .endDate(creditFacility.getEndDate())
                .build();
    }

    private String buildFullName(Applicant applicant) {
        return applicant.getFirstName() + " " + applicant.getLastName();
    }

    private BigDecimal calculateTotalLimitAmount(List<LoanTypeLimitRequest> loanTypeLimitRequests) {
        return loanTypeLimitRequests.stream()
                .map(LoanTypeLimitRequest::getLimitAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CreditFacility buildCreditFacility(CreditFacilityCreateRequest request, Applicant applicant) {
        return CreditFacility.builder()
                .creditFacilityType(CreditFacilityType.valueOf(request.getCreditFacilityType()))
                .applicant(applicant)
                .currency(CurrencyType.valueOf(request.getCurrency()))
                .totalLimit(request.getTotalLimit())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    private List<LoanTypeLimit> buildLoanTypeLimit(List<LoanTypeLimitRequest> requests, CreditFacility creditFacility) {
        return requests.stream()
                .map(loanLimit -> LoanTypeLimit.builder()
                        .creditFacility(creditFacility)
                        .loanType(loanTypeService.getLoanTypeById(loanLimit.getLoanTypeId()))
                        .limitAmount(loanLimit.getLimitAmount())
                        .build())
                .collect(Collectors.toList());
    }

}
