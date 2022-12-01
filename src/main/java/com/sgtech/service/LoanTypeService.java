package com.sgtech.service;

import com.sgtech.entity.LoanType;
import com.sgtech.exception.ResourceNotFoundException;

import java.util.List;

public interface LoanTypeService {
    List<LoanType> findAllLoanTypes();
    LoanType getLoanTypeById(Long id);
}
