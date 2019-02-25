package com.mycompany.myapp.domain.restriction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "mappingInfo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientLocationRestrictionPredicate.class, name = "clientLocation"),
    @JsonSubTypes.Type(value = FreelancerLocationRestrictionPredicate.class, name = "freelancerLocation"),
    @JsonSubTypes.Type(value = MissionDurationRestrictionPredicate.class, name = "missionDuration"),
    @JsonSubTypes.Type(value = OrRestrictionPredicate.class, name = "or"),
    @JsonSubTypes.Type(value = CommercialRelationDurationRestrictionPredicate.class, name = "commercialRelationDuration")
})
public abstract class RestrictionPredicate {
    public abstract String getType();
}
