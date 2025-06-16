package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleNameDomainTest {

    @Test
    void update_shouldUpdateFieldsFromGivenRuleName() {

        // Given an original RuleName
        RuleName original = new RuleName();
        original.setId(1);
        original.setName("Rule A");
        original.setDescription("Description A");
        original.setJson("json String");
        original.setTemplate("Template A");
        original.setSqlStr("SELECT * FROM A");
        original.setSqlPart("WHERE id = A");

        // And a RuleName with updated values
        RuleName updated = new RuleName();
        updated.setId(1);
        updated.setName("Rule B");
        updated.setDescription("Description B");
        updated.setJson("new json String");
        updated.setTemplate("Template B");
        updated.setSqlStr("SELECT * FROM B");
        updated.setSqlPart("WHERE id = B");

        // When calling update
        original.update(updated);

        // Then all fields should be updated
        assertEquals(updated.getId(), original.getId());
        assertEquals(updated.getName(), original.getName());
        assertEquals(updated.getDescription(), original.getDescription());
        assertEquals(updated.getJson(), original.getJson());
        assertEquals(updated.getTemplate(), original.getTemplate());
        assertEquals(updated.getSqlStr(), original.getSqlStr());
        assertEquals(updated.getSqlPart(), original.getSqlPart());
    }

}
