package com.mycompany.myapp.domain.calculator;

public class FeesResult {
    private float fees;
    private String reason;

    public float getFees() {
        return fees;
    }

    public FeesResult fees(float fee) {
        this.fees = fee;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public FeesResult reason(String reason) {
        this.reason = reason;
        return this;
    }
}
