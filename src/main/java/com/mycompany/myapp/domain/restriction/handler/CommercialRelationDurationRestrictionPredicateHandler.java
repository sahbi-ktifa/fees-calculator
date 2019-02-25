package com.mycompany.myapp.domain.restriction.handler;

import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.restriction.CommercialRelationDurationRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.RestrictionPredicate;
import com.mycompany.myapp.utils.DurationCustomParserUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommercialRelationDurationRestrictionPredicateHandler implements RestrictionPredicateHandler<CommercialRelationDurationRestrictionPredicate> {
    @Override
    public boolean accept(RestrictionPredicate restrictionPredicate) {
        return restrictionPredicate.getType().equals("COMMERCIAL_RELATION_DURATION");
    }

    @Override
    public boolean predicateApplied(CommercialRelationDurationRestrictionPredicate restrictionPredicate, FeesCalculationRequest request) {
        if (request.getCommercialRelation() == null) {
            return false;
        }
        LocalDate predicateDate = DurationCustomParserUtils.retrieveDate(restrictionPredicate.getValue(), true);
        if (restrictionPredicate.getOperand().equals("gt") && request.getCommercialRelation().getLastMission().isBefore(predicateDate.atStartOfDay())) {
            return true;
        }
        return restrictionPredicate.getOperand().equals("lt") && request.getCommercialRelation().getLastMission().isAfter(predicateDate.atStartOfDay());
    }
}
