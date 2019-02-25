package com.mycompany.myapp.domain.calculator;

import javax.validation.constraints.NotNull;

public class MissionData {
    @NotNull
    private String duration;

    public String getDuration() {
        return duration;
    }

    public MissionData setDuration(String duration) {
        this.duration = duration;
        return this;
    }
}
