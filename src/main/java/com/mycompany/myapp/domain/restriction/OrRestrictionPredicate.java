package com.mycompany.myapp.domain.restriction;

import java.util.List;

public class OrRestrictionPredicate extends RestrictionPredicate {
    private List<RestrictionPredicate> restrictionPredicates;

    public List<RestrictionPredicate> getRestrictionPredicates() {
        return restrictionPredicates;
    }

    public OrRestrictionPredicate setRestrictionPredicates(List<RestrictionPredicate> restrictionPredicates) {
        this.restrictionPredicates = restrictionPredicates;
        return this;
    }

    @Override
    public String getType() {
        return "OR";
    }
}
