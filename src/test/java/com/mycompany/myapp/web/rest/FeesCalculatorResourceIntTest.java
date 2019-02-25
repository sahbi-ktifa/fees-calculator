package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FeeCalculatorApp;
import com.mycompany.myapp.domain.Rate;
import com.mycompany.myapp.domain.Rule;
import com.mycompany.myapp.domain.restriction.ClientLocationRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.CommercialRelationDurationRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.FreelancerLocationRestrictionPredicate;
import com.mycompany.myapp.domain.restriction.MissionDurationRestrictionPredicate;
import com.mycompany.myapp.repository.RuleRepository;
import com.mycompany.myapp.service.FeesCalculatorService;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the FeeCalculatorResource REST controller.
 *
 * @see FeesCalculatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeeCalculatorApp.class)
public class FeesCalculatorResourceIntTest {

    private MockMvc restFeeCalculatorMockMvc;

    @Autowired
    private FeesCalculatorService calculatorService;

    @Autowired
    private RuleRepository ruleRepository;

    @Before
    public void setup() {
        FeesCalculatorResource feesCalculatorResource = new FeesCalculatorResource(calculatorService);
        this.restFeeCalculatorMockMvc = MockMvcBuilders
            .standaloneSetup(feesCalculatorResource)
            .build();
        ruleRepository.save(new Rule().name("rule1")
            .rate(new Rate().percent(8))
            .restrictions(Collections.singletonList(new MissionDurationRestrictionPredicate().operand("gt").value("2months"))));
        ruleRepository.save(new Rule().name("rule1.1")
            .rate(new Rate().percent(8))
            .restrictions(Collections.singletonList(new MissionDurationRestrictionPredicate().operand("lt").value("2weeks"))));
        ruleRepository.save(new Rule().name("rule2")
            .rate(new Rate().percent(8))
            .restrictions(Arrays.asList(new ClientLocationRestrictionPredicate().country("ES"),
                new FreelancerLocationRestrictionPredicate().country("ES"))));
        ruleRepository.save(new Rule().name("rule3")
            .rate(new Rate().percent(6))
            .restrictions(Collections.singletonList(new MissionDurationRestrictionPredicate().operand("gt").value("4months"))));
        ruleRepository.save(new Rule().name("rule4")
            .rate(new Rate().percent(8))
            .restrictions(Collections.singletonList(new CommercialRelationDurationRestrictionPredicate().operand("gt").value("2years"))));
        ruleRepository.save(new Rule().name("rule4.1")
            .rate(new Rate().percent(8))
            .restrictions(Collections.singletonList(new CommercialRelationDurationRestrictionPredicate().operand("lt").value("2years"))));
    }

    @Test
    public void calculateFee_should_not_match_any_rules() throws Exception {
        restFeeCalculatorMockMvc.perform(post("/fees")
            .content("{\n" +
                "\t\"mission\": {\n" +
                "\t\t\"duration\": \"1month\"\n" +
                "\t}\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.fees", is(10.0)))
            .andExpect(jsonPath("$.reason", IsNull.nullValue()));
    }

    @Test
    public void calculateFee_should_match_rule1() throws Exception {
        restFeeCalculatorMockMvc.perform(post("/fees")
            .content("{\n" +
                "\t\"client\": {\"ip\": \"217.127.206.227\"},\n" +
                "\t\"freelancer\": {\"ip\": \"217.127.206.227\"},\n" +
                "\t\"mission\": {\n" +
                "\t\t\"duration\": \"4months\"\n" +
                "\t},\n" +
                "\t\"commercialRelation\": {\n" +
                "\t\t\"firstMission\": \"2018-04-16T13:24:17.510Z\",\n" +
                "\t\t\"lastMission\": \"2018-07-16T14:24:17.510Z\"\n" +
                "\t}\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.fees", is(8.0)))
            .andExpect(jsonPath("$.reason", is("rule1")));
    }

    @Test
    public void calculateFee_should_match_rule1_1() throws Exception {
        restFeeCalculatorMockMvc.perform(post("/fees")
            .content("{\n" +
                "\t\"client\": {\"ip\": \"217.127.206.227\"},\n" +
                "\t\"freelancer\": {\"ip\": \"217.127.206.227\"},\n" +
                "\t\"mission\": {\n" +
                "\t\t\"duration\": \"1week\"\n" +
                "\t},\n" +
                "\t\"commercialRelation\": {\n" +
                "\t\t\"firstMission\": \"2018-04-16T13:24:17.510Z\",\n" +
                "\t\t\"lastMission\": \"2018-07-16T14:24:17.510Z\"\n" +
                "\t}\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.fees", is(8.0)))
            .andExpect(jsonPath("$.reason", is("rule1.1")));
    }

    @Test
    public void calculateFee_should_match_rule2() throws Exception {
        restFeeCalculatorMockMvc.perform(post("/fees")
            .content("{\n" +
                "\t\"client\": {\"ip\": \"217.127.206.227\"},\n" +
                "\t\"freelancer\": {\"ip\": \"217.127.206.227\"},\n" +
                "\t\"mission\": {\n" +
                "\t\t\"duration\": \"1months\"\n" +
                "\t},\n" +
                "\t\"commercialRelation\": {\n" +
                "\t\t\"firstMission\": \"2018-04-16T13:24:17.510Z\",\n" +
                "\t\t\"lastMission\": \"2018-07-16T14:24:17.510Z\"\n" +
                "\t}\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.fees", is(8.0)))
            .andExpect(jsonPath("$.reason", is("rule2")));
    }

    @Test
    public void calculateFee_should_match_rule3() throws Exception {
        restFeeCalculatorMockMvc.perform(post("/fees")
            .content("{\n" +
                "\t\"client\": {\"ip\": \"17.127.206.227\"},\n" +
                "\t\"freelancer\": {\"ip\": \"247.127.206.227\"},\n" +
                "\t\"mission\": {\n" +
                "\t\t\"duration\": \"8months\"\n" +
                "\t},\n" +
                "\t\"commercialRelation\": {\n" +
                "\t\t\"firstMission\": \"2018-04-16T13:24:17.510Z\",\n" +
                "\t\t\"lastMission\": \"2018-07-16T14:24:17.510Z\"\n" +
                "\t}\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.fees", is(6.0)))
            .andExpect(jsonPath("$.reason", is("rule3")));
    }

    @Test
    public void calculateFee_should_match_rule4() throws Exception {
        restFeeCalculatorMockMvc.perform(post("/fees")
            .content("{\n" +
                "\t\"client\": {\"ip\": \"17.127.206.227\"},\n" +
                "\t\"freelancer\": {\"ip\": \"247.127.206.227\"},\n" +
                "\t\"mission\": {\n" +
                "\t\t\"duration\": \"1months\"\n" +
                "\t},\n" +
                "\t\"commercialRelation\": {\n" +
                "\t\t\"firstMission\": \"2018-04-16T13:24:17.510Z\",\n" +
                "\t\t\"lastMission\": \"2016-07-16T14:24:17.510Z\"\n" +
                "\t}\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.fees", is(8.0)))
            .andExpect(jsonPath("$.reason", is("rule4")));
    }

    @Test
    public void calculateFee_should_match_rule4_1() throws Exception {
        restFeeCalculatorMockMvc.perform(post("/fees")
            .content("{\n" +
                "\t\"client\": {\"ip\": \"17.127.206.227\"},\n" +
                "\t\"freelancer\": {\"ip\": \"247.127.206.227\"},\n" +
                "\t\"mission\": {\n" +
                "\t\t\"duration\": \"1months\"\n" +
                "\t},\n" +
                "\t\"commercialRelation\": {\n" +
                "\t\t\"firstMission\": \"2018-04-16T13:24:17.510Z\",\n" +
                "\t\t\"lastMission\": \"2018-07-16T14:24:17.510Z\"\n" +
                "\t}\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.fees", is(8.0)))
            .andExpect(jsonPath("$.reason", is("rule4.1")));
    }
}
