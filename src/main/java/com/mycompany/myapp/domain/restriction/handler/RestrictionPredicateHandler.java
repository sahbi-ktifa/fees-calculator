package com.mycompany.myapp.domain.restriction.handler;

import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.restriction.RestrictionPredicate;

public interface RestrictionPredicateHandler<T extends RestrictionPredicate> {

    boolean accept(RestrictionPredicate restrictionPredicate);

    boolean predicateApplied(T restrictionPredicate, FeesCalculationRequest request);
}
