package com.mycompany.myapp.domain.restriction;

public class FreelancerLocationRestrictionPredicate extends RestrictionPredicate {
    private String country;

    public String getCountry() {
        return country;
    }

    public FreelancerLocationRestrictionPredicate country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String getType() {
        return "FREELANCER_LOCATION";
    }
}
