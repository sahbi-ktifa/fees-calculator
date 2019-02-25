package com.mycompany.myapp.domain;

import javax.validation.constraints.NotNull;

public class Rate {
    @NotNull
    private float percent;

    public float getPercent() {
        return percent;
    }

    public Rate percent(float percent) {
        this.percent = percent;
        return this;
    }
}
