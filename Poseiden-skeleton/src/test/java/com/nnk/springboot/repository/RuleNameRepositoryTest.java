package com.nnk.springboot.repository;


import com.nnk.springboot.domain.RuleName;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RuleNameRepositoryTest {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    private final RuleName ruleName = new RuleName();



    @BeforeEach
    public void setUp () {
        ruleName.setName("UI-Name");
        ruleName.setDescription("UI-Description");
        ruleName.setJson("UI-Json");
        ruleName.setTemplate("UI-Template");
        ruleName.setSqlStr("UI-SQLStr");
        ruleName.setSqlPart("UI-SQLPart");
        ruleNameRepository.save(ruleName);
    }



    @Test
    void save_shouldSaveRuleName () {

        // Given a RuleName -- by setUp()

        // When try to save RuleName -- by setUp()
        RuleName response = ruleNameRepository.save(ruleName);

        // Then RuleName is saved
        assertTrue(ruleNameRepository.findById(response.getId()).isPresent());

    }



    @Test
    void update_shouldUpdateRuleName () {

        // Given a RuleName -- by setUp()

        // Given fields to update
        ruleName.setName("UI-Name_Updated");
        ruleName.setDescription("UI-Description_Updated");

        // When try to update RuleName
        RuleName response = ruleNameRepository.save(ruleName);

        // Then RuleName is updated
        assertEquals("UI-Name_Updated", response.getName());
        assertEquals("UI-Description_Updated", response.getDescription());

    }



    @Test
    void findAll_shouldReturnRuleNameList () {

        // Given a RuleName -- by setUp()

        // When try to find all RuleName
        List<RuleName> responseList = ruleNameRepository.findAll();

        // Then return list of RuleName
        assertFalse(responseList.isEmpty());
        assertTrue(responseList.contains(ruleName));

    }



    @Test
    void findById_shouldReturnRuleName_withValidId () {

        // Given a RuleName -- by setUp()
        final Integer ruleNameId = ruleName.getId();

        // When try to find RuleName by Id
        Optional<RuleName> response = ruleNameRepository.findById(ruleNameId);

        // Then return the RuleName
        assertEquals(ruleName.getName(), response.get().getName());
        assertEquals(ruleName.getDescription(), response.get().getDescription());
        assertEquals(ruleName.getJson(), response.get().getJson());
        assertEquals(ruleName.getTemplate(), response.get().getTemplate());
        assertEquals(ruleName.getSqlStr(), response.get().getSqlStr());
        assertEquals(ruleName.getSqlPart(), response.get().getSqlPart());

    }



    @Test
    void findById_shouldReturnEmpty_withoutValidId () {

        // Given a RuleName -- by setUp()
        final Integer ruleNameId = ruleName.getId() + 1;

        // When try to find RuleName by Id
        Optional<RuleName> response = ruleNameRepository.findById(ruleNameId);

        // Then return an empty RuleName
        assertTrue(response.isEmpty());

    }



    @Test
    void delete_shouldDeleteRuleName () {

        // Given a RuleName -- by setUp()
        ruleNameRepository.save(ruleName);

        // When try to delete RuleName
        ruleNameRepository.deleteById(ruleName.getId());

        // Then RuleName is deleted
        Optional<RuleName> response = ruleNameRepository.findById(ruleName.getId());
        assertTrue(response.isEmpty());

    }

}
