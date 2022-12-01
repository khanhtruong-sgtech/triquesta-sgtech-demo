package com.sgtech.service;

import com.sgtech.entity.CreditFacility;
import com.sgtech.enums.CreditFacilityType;
import com.sgtech.enums.CurrencyType;
import com.sgtech.repository.CreditFacilityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditFacilityServiceTest {

    @Mock
    CreditFacilityRepository creditFacilityRepository;

    @InjectMocks
    CreditFacilityServiceImpl creditFacilityService;

    @Test
    public void getCreditFacility() {
        // Create mock data
        Long mockId = 10L;
        CreditFacility mockCreditFacility = CreditFacility.builder()
                .id(mockId)
                .creditFacilityType(CreditFacilityType.CREDIT)
                .totalLimit(BigDecimal.valueOf(1000))
                .currency(CurrencyType.US_DOLLAR)
                .startDate(new Date())
                .endDate(new GregorianCalendar(2023, 11, 10).getTime())
                .build();

        // Define behavior of repository
        when(creditFacilityRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(mockCreditFacility));

        // Call service method
        CreditFacility actualCreditFacility = creditFacilityService.getCreditFacility(mockId);

        // Assert the result
        assertEquals(mockCreditFacility, actualCreditFacility);

        // Ensure repository is called
        verify(creditFacilityRepository).findById(mockId);
    }

}