package com.mycompany.myapp.domain.restriction.handler;

import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.restriction.MissionDurationRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.RestrictionPredicate;
import com.mycompany.myapp.utils.DurationCustomParserUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MissionDurationRestrictionPredicateHandler implements RestrictionPredicateHandler<MissionDurationRestrictionPredicate> {
    @Override
    public boolean accept(RestrictionPredicate restrictionPredicate) {
        return restrictionPredicate.getType().equals("MISSION_DURATION");
    }

    @Override
    public boolean predicateApplied(MissionDurationRestrictionPredicate restrictionPredicate, FeesCalculationRequest request) {
        LocalDate endMissionDate = DurationCustomParserUtils.retrieveDate(request.getMission().getDuration());
        LocalDate endPredicateDate = DurationCustomParserUtils.retrieveDate(restrictionPredicate.getValue());
        if (restrictionPredicate.getOperand().equals("gt") && endPredicateDate.isBefore(endMissionDate)) {
            return true;
        }
        return restrictionPredicate.getOperand().equals("lt") && endPredicateDate.isAfter(endMissionDate);
    }
}
