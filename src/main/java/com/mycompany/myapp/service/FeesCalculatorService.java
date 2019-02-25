package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Rule;
import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.calculator.FeesResult;
import com.mycompany.myapp.domain.restriction.RestrictionPredicate;
import com.mycompany.myapp.domain.restriction.handler.RestrictionPredicateHandler;
import com.mycompany.myapp.repository.RuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class FeesCalculatorService {

    private RuleRepository ruleRepository;
    private List<RestrictionPredicateHandler> predicateHandlers;

    public FeesCalculatorService(RuleRepository ruleRepository, List<RestrictionPredicateHandler> predicateHandlers) {
        this.ruleRepository = ruleRepository;
        this.predicateHandlers = predicateHandlers;
    }

    public FeesResult calculateFee(FeesCalculationRequest request) {
        FeesResult result = new FeesResult().fees(10);
        List<Rule> rules = ruleRepository.findAll();
        if (CollectionUtils.isEmpty(rules)) {
            return result;
        }
        for (Rule rule : rules) {
            boolean ruleApplied = proceedRule(rule, request);
            if (ruleApplied && rule.getRate().getPercent() < result.getFees()) {
                result.fees(rule.getRate().getPercent());
                result.reason(rule.getName());
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked") // As we trust accept methods as it is
    private boolean proceedRule(Rule rule, FeesCalculationRequest request) {
        for (RestrictionPredicate restriction : rule.getRestrictions()) {
            RestrictionPredicateHandler predicateHandler = predicateHandlers.stream()
                .filter(handler -> handler.accept(restriction))
                .findFirst().orElse(null);
            boolean isPredicateApplied = predicateHandler != null && predicateHandler.predicateApplied(restriction, request);
            if (!isPredicateApplied) {
                return false;
            }
        }
        return true;
    }
}
