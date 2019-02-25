package com.mycompany.myapp.domain.restriction;

public class CommercialRelationDurationRestrictionPredicate extends RestrictionPredicate {
    private String operand;
    private String value;

    public String getOperand() {
        return operand;
    }

    public CommercialRelationDurationRestrictionPredicate operand(String operand) {
        this.operand = operand;
        return this;
    }

    public String getValue() {
        return value;
    }

    public CommercialRelationDurationRestrictionPredicate value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String getType() {
        return "COMMERCIAL_RELATION_DURATION";
    }
}
