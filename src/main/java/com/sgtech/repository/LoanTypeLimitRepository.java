package com.sgtech.repository;

import com.sgtech.entity.LoanTypeLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeLimitRepository extends JpaRepository<LoanTypeLimit, Long> {

    LoanTypeLimit findByCreditFacility_IdAndLoanType_Id(Long creditFacilityId, Long loanTypeId);

}
