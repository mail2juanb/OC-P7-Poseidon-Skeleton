package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurvePointDomainTest {

    @Test
    void update_shouldUpdateFieldsFromGivenCurvePoint() {
        // Given an existing CurvePoint
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(5.5);
        curvePoint.setValue(100.0);

        // And a CurvePoint with new values
        CurvePoint updated = new CurvePoint();
        updated.setId(2);
        updated.setCurveId(20);
        updated.setTerm(10.0);
        updated.setValue(200.0);

        // When calling update
        curvePoint.update(updated);

        // Then only the expected fields are updated
        //assertEquals(updated.getId(), curvePoint.getId());
        assertEquals(updated.getCurveId(), curvePoint.getCurveId());
        assertEquals(updated.getTerm(), curvePoint.getTerm());
        assertEquals(updated.getValue(), curvePoint.getValue());
    }

}
