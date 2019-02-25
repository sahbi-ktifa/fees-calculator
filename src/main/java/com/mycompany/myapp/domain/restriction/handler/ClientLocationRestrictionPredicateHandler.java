package com.mycompany.myapp.domain.restriction.handler;

import com.mycompany.myapp.domain.calculator.FeesCalculationRequest;
import com.mycompany.myapp.domain.restriction.ClientLocationRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.RestrictionPredicate;
import com.mycompany.myapp.service.IPStackService;
import org.springframework.stereotype.Component;

@Component
public class ClientLocationRestrictionPredicateHandler implements RestrictionPredicateHandler<ClientLocationRestrictionPredicate> {
    private IPStackService ipStackService;

    public ClientLocationRestrictionPredicateHandler(IPStackService ipStackService) {
        this.ipStackService = ipStackService;
    }

    @Override
    public boolean accept(RestrictionPredicate restrictionPredicate) {
        return restrictionPredicate.getType().equals("CLIENT_LOCATION");
    }

    @Override
    public boolean predicateApplied(ClientLocationRestrictionPredicate restrictionPredicate, FeesCalculationRequest request) {
        if (request.getClient() == null) {
            return false;
        }
        String countryCodeFromIp = ipStackService.retrieveCountryCodeFromIp(request.getClient().getIp());
        return countryCodeFromIp != null && countryCodeFromIp.equals(restrictionPredicate.getCountry());
    }
}
