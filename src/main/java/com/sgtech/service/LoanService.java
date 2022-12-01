package com.sgtech.service;

import com.sgtech.dto.request.LoanCreateRequest;
import com.sgtech.dto.request.LoanPaymentRequest;
import com.sgtech.dto.response.ApplicantLoansStatus;
import com.sgtech.dto.response.CommonResponse;
import com.sgtech.dto.response.LoanDetailsResponse;
import com.sgtech.entity.Loan;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {
    CommonResponse createLoan(LoanCreateRequest request);

    CommonResponse payLoan(Long loanId, LoanPaymentRequest request);

    BigDecimal calculateTotalAmountFromLoans(List<Loan> loans);

    ApplicantLoansStatus getApplicantLoans(Long applicantId);

    List<LoanDetailsResponse> getApplicantLoanPaymentDetails(Long applicantId);
}
