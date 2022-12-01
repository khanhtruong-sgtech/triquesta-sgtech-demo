package com.sgtech.service;

import com.sgtech.dto.request.LoanCreateRequest;
import com.sgtech.dto.request.LoanPaymentRequest;
import com.sgtech.dto.response.ApplicantLoansStatus;
import com.sgtech.dto.response.CommonResponse;
import com.sgtech.dto.response.LoanDetailsResponse;
import com.sgtech.dto.response.LoanPaymentDetails;
import com.sgtech.entity.*;
import com.sgtech.enums.CurrencyType;
import com.sgtech.enums.LoanStatus;
import com.sgtech.exception.BadRequestException;
import com.sgtech.exception.ResourceNotFoundException;
import com.sgtech.repository.LoanPaymentRepository;
import com.sgtech.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanPaymentRepository loanPaymentRepository;
    private final LoanTypeService loanTypeService;
    private final LoanTypeLimitService loanTypeLimitService;
    private final CreditFacilityService creditFacilityService;
    private final ApplicantService applicantService;

    @Override
    public CommonResponse createLoan(LoanCreateRequest request) {
        BigDecimal loanTypeLimit = loanTypeLimitService.getLimitAmount(request.getCreditFacilityId(), request.getLoanTypeId());

        // Find all current loans to check whether this request exceeds the limit
        List<Loan> currentLoanListOfThisCreditFacility = getLoansByCreditFacilityId(request.getCreditFacilityId());
        if (!CollectionUtils.isEmpty(currentLoanListOfThisCreditFacility)) {
            BigDecimal currentTotalLoanAmount = calculateTotalAmountFromLoans(currentLoanListOfThisCreditFacility);
            if ((currentTotalLoanAmount.add(request.getAmount()).compareTo(loanTypeLimit) == 1)) {
                throw new BadRequestException("Loan amount exceeds the limit");
            }
        }

        if (request.getAmount().compareTo(loanTypeLimit) == 1) {
            throw new BadRequestException("Loan amount exceeds the limit");
        }

        Loan loan = buildLoan(request);
        loanRepository.save(loan);
        return CommonResponse.builder().numberOfRecordsInserted(1).build();
    }

    @Override
    @Transactional
    public CommonResponse payLoan(Long loanId, LoanPaymentRequest request) {
        Loan loan = findLoadById(loanId);
        if (loan.getLoanStatus() == LoanStatus.CLOSED) {
            throw new BadRequestException("This loan is already closed. No need to pay anymore");
        }

        List<LoanPayment> oldLoanPayments = loan.getLoanPayments();
        BigDecimal paidAmount = calculateTotalAmountFromLoanPayments(oldLoanPayments);
        BigDecimal mustPayAmount = calculateMustPayAmount(loan.getStartDate(), loan.getInterestRate(), loan.getAmount());

        CommonResponse commonResponse = new CommonResponse();
        if (paidAmount.add(request.getPayAmount()).compareTo(mustPayAmount) == 1) {
            throw new BadRequestException("This pay amount exceed current load amount, please check again!");
        } else if (paidAmount.add(request.getPayAmount()).compareTo(mustPayAmount) == 0) {
            loan.setLoanStatus(LoanStatus.CLOSED);
            commonResponse.setContent("You fully paid!");
        } else {
            loan.setLoanStatus(LoanStatus.PARTIALLY_PAID);
            commonResponse.setContent("You have paid partially");
        }
        commonResponse.setNumberOfRecordsInserted(1);

        LoanPayment loanPayment = buildLoanPayment(loan, request);
        loanPaymentRepository.save(loanPayment);
        return commonResponse;
    }

    @Override
    @Transactional
    public ApplicantLoansStatus getApplicantLoans(Long applicantId) {
        Applicant applicant = applicantService.findApplicantById(applicantId);
        ApplicantLoansStatus applicantLoansStatus = new ApplicantLoansStatus();
        List<CreditFacility> creditFacilityList = applicant.getCreditFacilities();

        List<Loan> loanList = new ArrayList<>();
        for (CreditFacility creditFacility : creditFacilityList) {
            loanList.addAll(creditFacility.getLoans());
        }

        List<Loan> remainingLoanList = loanList.stream()
                .filter(loan -> loan.getLoanStatus() != LoanStatus.CLOSED)
                .collect(Collectors.toList());

        List<LoanPayment> remainingLoanPaymentList = new ArrayList<>();
        for (Loan loan : loanList) {
            remainingLoanPaymentList.addAll(loan.getLoanPayments());
        }

        BigDecimal totalRemainingLoanAmount = calculateTotalAmountFromLoans(loanList);
        BigDecimal totalPaidLoadAmount = calculateTotalAmountFromLoanPayments(remainingLoanPaymentList);

        applicantLoansStatus.setApplicantId(applicant.getId());
        applicantLoansStatus.setApplicantName(applicant.getFirstName() + " " + applicant.getLastName());
        applicantLoansStatus.setNumberOfLoans(loanList.size());
        applicantLoansStatus.setNumberOfFullyPaidLoans(loanList.size() - remainingLoanList.size());
        applicantLoansStatus.setNotPaidAmount(totalRemainingLoanAmount.subtract(totalPaidLoadAmount));
        return applicantLoansStatus;
    }

    @Override
    public List<LoanDetailsResponse> getApplicantLoanPaymentDetails(Long applicantId) {
        Applicant applicant = applicantService.findApplicantById(applicantId);
        List<CreditFacility> creditFacilityList = applicant.getCreditFacilities();

        List<Loan> loanList = new ArrayList<>();
        for (CreditFacility creditFacility : creditFacilityList) {
            loanList.addAll(creditFacility.getLoans());
        }

        List<LoanDetailsResponse> loanDetailsResponses = new ArrayList<>();
        for (Loan loan : loanList) {
            LoanDetailsResponse loanDetailsResponse = buildLoanDetailsResponses(loan);
            loanDetailsResponses.add(loanDetailsResponse);
        }

        return loanDetailsResponses;
    }

    private BigDecimal calculateMustPayAmount(Date startDate, BigDecimal interestRate, BigDecimal loanAmount) {
        long numberOfDays = ChronoUnit.DAYS.between(startDate.toInstant(), new Date().toInstant());
        BigDecimal dateRate = BigDecimal.valueOf((float) numberOfDays / 365);
        BigDecimal interest = (loanAmount.multiply(dateRate)).multiply(interestRate);
        return loanAmount.add(interest).setScale(2, RoundingMode.HALF_UP);
    }

    private List<LoanPaymentDetails> buildLoanPaymentDetailsList(List<LoanPayment> loanPayments) {
        return loanPayments.stream()
                .map(loanPayment -> LoanPaymentDetails.builder()
                        .payDate(loanPayment.getPayDate())
                        .paidAmount(loanPayment.getPayAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private LoanDetailsResponse buildLoanDetailsResponses(Loan loan) {
        List<LoanPaymentDetails> loanPaymentDetailsList = buildLoanPaymentDetailsList(loan.getLoanPayments());
        return LoanDetailsResponse.builder()
                .loanId(loan.getId())
                .loanType(loan.getLoanType().getName())
                .amount(loan.getAmount())
                .currency(loan.getCurrency().toString())
                .startDate(loan.getStartDate())
                .endDate(loan.getEndDate())
                .interestRate(loan.getInterestRate())
                .loanStatus(loan.getLoanStatus().toString())
                .loanPaymentDetailsList(loanPaymentDetailsList)
                .build();
    }


    private LoanPayment buildLoanPayment(Loan loan, LoanPaymentRequest loanPaymentRequest) {
        return LoanPayment.builder()
                .loan(loan)
                .payAmount(loanPaymentRequest.getPayAmount())
                .payDate(new Date())
                .build();
    }

    public Loan findLoadById(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Load not found"));
    }

    private List<Loan> getLoansByCreditFacilityId(Long creditFacilityId) {
        return loanRepository.findAllByCreditFacility_Id(creditFacilityId);
    }

    private Loan buildLoan(LoanCreateRequest request) {
        CreditFacility creditFacility = creditFacilityService.getCreditFacility(request.getCreditFacilityId());
        LoanType loanType = loanTypeService.getLoanTypeById(request.getLoanTypeId());
        return Loan.builder()
                .creditFacility(creditFacility)
                .currency(CurrencyType.valueOf(request.getCurrency()))
                .loanType(loanType)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .amount(request.getAmount())
                .interestRate(request.getInterestRate())
                .loanStatus(LoanStatus.NEW)
                .build();
    }

    private BigDecimal calculateTotalAmountFromLoanPayments(List<LoanPayment> loanPayments) {
        return loanPayments.stream()
                .map(LoanPayment::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalAmountFromLoans(List<Loan> loans) {
        return loans.stream()
                .map(Loan::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
