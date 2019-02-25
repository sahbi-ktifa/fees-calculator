package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Rule;
import com.mycompany.myapp.repository.RuleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RulesResource {

    private RuleRepository ruleRepository;

    public RulesResource(RuleRepository rulesRepository) {
        this.ruleRepository = rulesRepository;
    }

    @GetMapping("rules")
    public List<Rule> retrieveRules() {
        return ruleRepository.findAll();
    }

    @PostMapping("rules")
    public Rule createRule(@Valid @RequestBody Rule rule) {
        return ruleRepository.save(rule);
    }
}
