package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FeesCalculatorApp;
import com.mycompany.myapp.repository.RuleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the RulesResource REST controller.
 *
 * @see FeesCalculatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeesCalculatorApp.class)
public class RulesResourceIntTest {

    private MockMvc restRulesMockMvc;

    @Autowired
    private RuleRepository ruleRepository;

    @Before
    public void setup() {
        RulesResource rulesResource = new RulesResource(ruleRepository);
        this.restRulesMockMvc = MockMvcBuilders
            .standaloneSetup(rulesResource)
            .build();
    }

    @Test
    public void retrieveRules() throws Exception {
        restRulesMockMvc.perform(get("/api/rules"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void createRule_without_correct_sample_should_be_refused() throws Exception {
        restRulesMockMvc.perform(post("/api/rules")
            .content("{\"name\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createRule_with_correct_sample_should_be_accepted() throws Exception {
        restRulesMockMvc.perform(post("/api/rules")
            .content("{\"name\":\"test\",\"rate\":{\"percent\":\"8\"},\"restrictions\":[{\"mappingInfo\":\"clientLocation\",\"country\":\"ES\"},{\"mappingInfo\":\"freelancerLocation\",\"country\":\"ES\"}]}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}
