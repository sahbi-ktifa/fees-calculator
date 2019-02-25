package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.calculator.FeesResult;
import com.mycompany.myapp.service.FeesCalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class FeesCalculatorResource {

    private FeesCalculatorService calculatorService;

    public FeesCalculatorResource(FeesCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("fees")
    public FeesResult calculateFees(@Valid @RequestBody FeesCalculationRequest request) {
        return calculatorService.calculateFee(request);
    }
}
