package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Rule;
import com.mycompany.myapp.repository.RuleRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
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
