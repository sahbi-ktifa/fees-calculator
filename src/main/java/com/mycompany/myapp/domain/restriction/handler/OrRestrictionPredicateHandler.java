package com.mycompany.myapp.domain.restriction.handler;

import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.restriction.OrRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.RestrictionPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OrRestrictionPredicateHandler implements RestrictionPredicateHandler<OrRestrictionPredicate> {
    private ApplicationContext appContext;
    private Logger logger = LoggerFactory.getLogger(OrRestrictionPredicateHandler.class);

    public OrRestrictionPredicateHandler(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public boolean accept(RestrictionPredicate restrictionPredicate) {
        return restrictionPredicate.getType().equals("OR");
    }

    @Override
    @SuppressWarnings("unchecked") // As we trust convention naming, we should retrieve correct implementation
    public boolean predicateApplied(OrRestrictionPredicate restrictionPredicate, FeesCalculationRequest request) {
        if (!CollectionUtils.isEmpty(restrictionPredicate.getRestrictionPredicates())) {
            for (RestrictionPredicate predicate : restrictionPredicate.getRestrictionPredicates()) {
                if (predicate instanceof OrRestrictionPredicate) {
                    return predicateApplied((OrRestrictionPredicate) predicate, request);
                }
                try {
                    String className = predicate.getClass().getPackage().getName() + ".handler." + predicate.getClass().getSimpleName() + "Handler";
                    RestrictionPredicateHandler handler = (RestrictionPredicateHandler) appContext.getBean(Class.forName(className));
                    if (handler.predicateApplied(predicate, request)) {
                        return true;
                    }
                } catch (ClassNotFoundException e) {
                    logger.info("Unable to load bean", e);
                }
            }

        }
        return false;
    }
}
