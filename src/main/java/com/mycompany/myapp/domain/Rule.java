package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.restriction.RestrictionPredicate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document
public class Rule {
    private String id;
    @NotNull
    private String name;
    @NotNull
    private Rate rate;
    @NotNull
    private List<RestrictionPredicate> restrictions;

    public String getId() {
        return id;
    }

    public Rule setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Rule name(String name) {
        this.name = name;
        return this;
    }

    public Rate getRate() {
        return rate;
    }

    public Rule rate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public List<RestrictionPredicate> getRestrictions() {
        return restrictions;
    }

    public Rule restrictions(List<RestrictionPredicate> restrictions) {
        this.restrictions = restrictions;
        return this;
    }
}
