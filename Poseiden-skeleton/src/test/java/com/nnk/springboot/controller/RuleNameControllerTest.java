package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.AbstractCrudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbstractCrudService<RuleName> service;



    @Test
    public void home_ShouldReturn200AndRuleNameListView() throws Exception {

        // Given : a simulated list of RuleNames returned by the service
        RuleName rule1 = new RuleName();
        rule1.setId(1);
        rule1.setName("Rule 1");
        rule1.setDescription("Description 1");
        rule1.setJson("Json String 1");
        rule1.setTemplate("Template 1");
        rule1.setSqlStr("SQL 1");
        rule1.setSqlPart("SQL Part 1");

        RuleName rule2 = new RuleName();
        rule2.setId(2);
        rule2.setName("Rule 2");
        rule2.setDescription("Description 2");
        rule2.setJson("Json String 2");
        rule2.setTemplate("Template 2");
        rule2.setSqlStr("SQL 2");
        rule2.setSqlPart("SQL Part 2");

        List<RuleName> ruleNames = Arrays.asList(rule1, rule2);

        BDDMockito.given(service.getAll()).willReturn(ruleNames);

        // When: a GET request is made to /ruleName/list
        // Then: the “ruleName/list” view is returned with a list containing 2 items
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNames", hasSize(2)));
    }


    @Test
    public void addRuleForm_ShouldReturn200AndRuleNameAddView() throws Exception {

        // When: a GET request is made to /ruleName/add
        // Then: the “ruleName/add” view is returned without error
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }


    @Test
    public void validateRuleName_ShouldReturn302AndRuleNameListView() throws Exception {

        // Given: a valid RuleName object with all required fields
        RuleName rule = new RuleName();
        rule.setId(null);
        rule.setName("Rule 1");
        rule.setDescription("Description 1");
        rule.setJson("Json String 1");
        rule.setTemplate("Template 1");
        rule.setSqlStr("SQL 1");
        rule.setSqlPart("SQL Part 1");

        // When: a valid POST request is made to /ruleName/validate
        // Then: a redirection to /ruleName/list is expected and the service is called
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", rule.getName())
                        .param("description", rule.getDescription())
                        .param("json", rule.getJson())
                        .param("template", rule.getTemplate())
                        .param("sqlStr", rule.getSqlStr())
                        .param("sqlPart", rule.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).create(any(RuleName.class));
    }


    @Test
    public void validateRuleName_WithValidationError_ShouldReturn200AndRuleNameAddView() throws Exception {

        // Given: a required field (name) is too large
        String longName = "A".repeat(126);

        // When: the form is submitted with a validation error
        // Then: the “ruleName/add” view is displayed and the error is attached to the model
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", longName)
                        .param("description", "Description 1")
                        .param("json", "{}")
                        .param("template", "Template 1")
                        .param("sqlStr", "SQL 1")
                        .param("sqlPart", "SQL Part 1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }


    @Test
    public void showUpdateForm_ShouldReturn200AndRuleNameUpdateView() throws Exception {

        // Given: an existing RuleName with a given ID
        Integer id = 1;

        RuleName rule = new RuleName();
        rule.setId(id);
        rule.setName("Rule 1");
        rule.setDescription("Description 1");
        rule.setJson("Json String 1");
        rule.setTemplate("Template 1");
        rule.setSqlStr("SQL 1");
        rule.setSqlPart("SQL Part 1");

        BDDMockito.given(service.getById(id)).willReturn(rule);

        // When: a GET request is made to /ruleName/update/{id}
        // Then: the “ruleName/update” view is displayed with the expected object in the model
        mockMvc.perform(get("/ruleName/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", hasProperty("id", is(id))));
    }


    @Test
    public void showUpdateForm_WithUnknownId_ShouldReturn302AndRedirectToErrorPage() throws Exception {

        // Given: no RuleName found, exception triggered
        Integer unknownId = 999;
        BDDMockito.given(service.getById(unknownId)).willThrow(new IllegalArgumentException("RuleName not found"));

        // When / Then: the user is redirected to /error
        mockMvc.perform(get("/ruleName/update/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }


    @Test
    public void updateRuleName_ShouldReturn302AndShowRuleNameListView() throws Exception {

        // Given: a valid RuleName object for the update
        Integer id = 1;
        RuleName rule = new RuleName();
        rule.setId(id);
        rule.setName("Updated Rule");
        rule.setDescription("Updated Description");
        rule.setJson("Json String Updated");
        rule.setTemplate("Updated Template");
        rule.setSqlStr("Updated SQL");
        rule.setSqlPart("Updated SQL Part");

        // When: a POST request is made to /ruleName/update/{id} with valid data
        // Then: a redirection to /ruleName/list is expected and the service is called
        mockMvc.perform(post("/ruleName/update/{id}", id)
                        .param("name", rule.getName())
                        .param("description", rule.getDescription())
                        .param("json", rule.getJson())
                        .param("template", rule.getTemplate())
                        .param("sqlStr", rule.getSqlStr())
                        .param("sqlPart", rule.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).update(any(RuleName.class));
    }


    @Test
    public void updateRuleName_WithValidationErrors_ShouldReturn200AndRuleNameUpdateView() throws Exception {

        // Given: a required field (name) is too large
        Integer id = 1;
        String longName = "A".repeat(126);

        // When: an invalid POST request is made to /ruleName/update/{id}
        // Then: the “ruleName/update” view is displayed and the validation error is detected
        mockMvc.perform(post("/ruleName/update/{id}", id)
                        .param("name", longName)
                        .param("description", "Updated Description")
                        .param("json", "{}")
                        .param("template", "Updated Template")
                        .param("sqlStr", "Updated SQL")
                        .param("sqlPart", "Updated SQL Part"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }


    @Test
    public void deleteRuleName_ShouldReturn302AndShowRuleNameListView() throws Exception {

        // Given : a RuleName to be deleted
        Integer id = 1;
        RuleName rule = new RuleName();
        rule.setId(id);
        rule.setName("Rule 1");
        rule.setDescription("Description 1");
        rule.setJson("Json String 1");
        rule.setTemplate("Template 1");
        rule.setSqlStr("SQL 1");
        rule.setSqlPart("SQL Part 1");

        // When: a GET request is made to /ruleName/delete/{id}
        // Then: a redirection to /ruleName/list is expected and the service is called
        mockMvc.perform(get("/ruleName/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).delete(id);
    }

}


