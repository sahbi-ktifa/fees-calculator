package com.mycompany.myapp.domain.restriction.handler;

import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.restriction.FreelancerLocationRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.RestrictionPredicate;
import com.mycompany.myapp.service.IPStackService;
import org.springframework.stereotype.Component;

@Component
public class FreelancerLocationRestrictionPredicateHandler implements RestrictionPredicateHandler<FreelancerLocationRestrictionPredicate> {
    private IPStackService ipStackService;

    public FreelancerLocationRestrictionPredicateHandler(IPStackService ipStackService) {
        this.ipStackService = ipStackService;
    }

    @Override
    public boolean accept(RestrictionPredicate restrictionPredicate) {
        return restrictionPredicate.getType().equals("FREELANCER_LOCATION");
    }

    @Override
    public boolean predicateApplied(FreelancerLocationRestrictionPredicate restrictionPredicate, FeesCalculationRequest request) {
        if (request.getFreelancer() == null) {
            return false;
        }
        String countryCodeFromIp = ipStackService.retrieveCountryCodeFromIp(request.getFreelancer().getIp());
        return countryCodeFromIp != null && countryCodeFromIp.equals(restrictionPredicate.getCountry());
    }
}
