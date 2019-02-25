package com.mycompany.myapp.domain.restriction;

public class ClientLocationRestrictionPredicate extends RestrictionPredicate {
    private String country;

    public String getCountry() {
        return country;
    }

    public ClientLocationRestrictionPredicate country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String getType() {
        return "CLIENT_LOCATION";
    }
}
