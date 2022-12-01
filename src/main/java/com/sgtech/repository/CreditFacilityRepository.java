package com.sgtech.repository;

import com.sgtech.entity.CreditFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditFacilityRepository extends JpaRepository<CreditFacility, Long> {

    List<CreditFacility> findAllByApplicant_Id(Long applicantId);
}
