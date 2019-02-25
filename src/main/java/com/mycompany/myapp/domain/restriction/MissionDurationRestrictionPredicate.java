package com.mycompany.myapp.domain.restriction;

public class MissionDurationRestrictionPredicate extends RestrictionPredicate {
    private String operand;
    private String value;

    public String getOperand() {
        return operand;
    }

    public MissionDurationRestrictionPredicate operand(String operand) {
        this.operand = operand;
        return this;
    }

    public String getValue() {
        return value;
    }

    public MissionDurationRestrictionPredicate value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String getType() {
        return "MISSION_DURATION";
    }
}
