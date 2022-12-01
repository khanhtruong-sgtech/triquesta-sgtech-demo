package com.sgtech.controller;

import com.sgtech.dto.request.LoanCreateRequest;
import com.sgtech.dto.request.LoanPaymentRequest;
import com.sgtech.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    /**
     * Create a loan
     * @param payload
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createLoan(@RequestBody @Valid LoanCreateRequest payload) {
        return new ResponseEntity<>(loanService.createLoan(payload), HttpStatus.CREATED);
    }

    /**
     * Create a payment
     * @param id
     * @param payload
     * @return
     */
    @PostMapping("/{id}/payment")
    public ResponseEntity<?> loanPayment(@PathVariable Long id, @RequestBody @Valid LoanPaymentRequest payload) {
        return new ResponseEntity<>(loanService.payLoan(id, payload), HttpStatus.CREATED);
    }

    /**
     * Bank/applicant can monitor the current total of the loans that the applicant currently has
     * @param applicantId
     * @return
     */
    @GetMapping("/applicants/{id}/loans-status")
    public ResponseEntity<?> monitorLoans(@PathVariable(value = "id") Long applicantId) {
        return new ResponseEntity<>(loanService.getApplicantLoans(applicantId), HttpStatus.OK);
    }

    /**
     * Bank/Applicant can see the list of loan applicant have and payment made to the loan
     * @param applicantId
     * @return
     */
    @GetMapping("/applicants/{id}/loans-payments-details")
    public ResponseEntity<?> getLoanPaymentDetails(@PathVariable(value = "id") Long applicantId) {
        return new ResponseEntity<>(loanService.getApplicantLoanPaymentDetails(applicantId), HttpStatus.OK);
    }

}
