package com.sgtech.controller;

import com.sgtech.dto.request.CreditFacilityCreateRequest;
import com.sgtech.dto.response.CreditFacilityCreateResponse;
import com.sgtech.service.CreditFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credit-facilities")
public class CreditFacilityController {

    private final CreditFacilityService creditFacilityService;

    @PostMapping()
    public ResponseEntity<CreditFacilityCreateResponse> createCreditFacility(@RequestBody @Valid CreditFacilityCreateRequest payload) {
        return new ResponseEntity<>(creditFacilityService.createCreditFacility(payload), HttpStatus.CREATED);
    }

}
