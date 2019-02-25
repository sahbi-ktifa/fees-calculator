package com.mycompany.myapp.domain.calculator;

public class FeesCalculationRequest {
    private UserLocation client;
    private UserLocation freelancer;
    private MissionData mission;
    private CommercialRelation commercialRelation;

    public UserLocation getClient() {
        return client;
    }

    public FeesCalculationRequest setClient(UserLocation client) {
        this.client = client;
        return this;
    }

    public UserLocation getFreelancer() {
        return freelancer;
    }

    public FeesCalculationRequest setFreelancer(UserLocation freelancer) {
        this.freelancer = freelancer;
        return this;
    }

    public MissionData getMission() {
        return mission;
    }

    public FeesCalculationRequest setMission(MissionData mission) {
        this.mission = mission;
        return this;
    }

    public CommercialRelation getCommercialRelation() {
        return commercialRelation;
    }

    public FeesCalculationRequest setCommercialRelation(CommercialRelation commercialRelation) {
        this.commercialRelation = commercialRelation;
        return this;
    }
}
