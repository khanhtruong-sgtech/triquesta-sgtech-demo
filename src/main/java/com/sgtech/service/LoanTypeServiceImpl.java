package com.sgtech.service;

import com.sgtech.entity.Applicant;
import com.sgtech.entity.LoanType;
import com.sgtech.exception.ResourceNotFoundException;
import com.sgtech.repository.ApplicantRepository;
import com.sgtech.repository.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;

    @Override
    public List<LoanType> findAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    @Override
    public LoanType getLoanTypeById(Long id) {
        return loanTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan Type not found"));
    }

}
