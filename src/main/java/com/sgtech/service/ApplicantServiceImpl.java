package com.sgtech.service;

import com.sgtech.entity.Applicant;
import com.sgtech.exception.ResourceNotFoundException;
import com.sgtech.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Override
    public Applicant findApplicantById(Long id) {
        return applicantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Applicant not found"));
    }

}
