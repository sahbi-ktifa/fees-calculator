package com.mycompany.myapp.domain.calculator;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CommercialRelation {
    @NotNull
    private LocalDateTime firstMission;
    @NotNull
    private LocalDateTime lastMission;

    public LocalDateTime getFirstMission() {
        return firstMission;
    }

    public CommercialRelation setFirstMission(LocalDateTime firstMission) {
        this.firstMission = firstMission;
        return this;
    }

    public LocalDateTime getLastMission() {
        return lastMission;
    }

    public CommercialRelation setLastMission(LocalDateTime lastMission) {
        this.lastMission = lastMission;
        return this;
    }
}
