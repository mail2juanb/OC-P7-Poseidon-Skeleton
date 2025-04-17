package com.nnk.springboot.repositories;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
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
// We dont want the H2 in-memory database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CurvePointRepositoryTest {

    @Autowired
    private CurvePointRepository curvePointRepository;

    private final CurvePoint curvePoint = new CurvePoint();



    @BeforeEach
    public void setUp () {
        curvePoint.setTerm(12.2);
        curvePoint.setValue(24.4);
        curvePointRepository.save(curvePoint);
    }



    @Test
    void save_shouldSaveCurvePoint () {

        // Given a CurvePoint -- by setUp()

        // When try to save CurvePoint -- by setUp()
        CurvePoint response = curvePointRepository.save(curvePoint);

        // Then CurvePoint is saved
        assertTrue(curvePointRepository.findById(response.getId()).isPresent());

    }



    @Test
    void update_shouldUpdateCurvePoint () {

        // Given a CurvePoint -- by setUp()

        // Given fields to update
        curvePoint.setTerm(99.9);
        curvePoint.setValue(0.);

        // When try to update CurvePoint
        CurvePoint response = curvePointRepository.save(curvePoint);

        // Then CurvePoint is updated
        assertEquals(99.9, response.getTerm());
        assertEquals(0., response.getValue());

    }



    @Test
    void findAll_shouldReturnCurvePointList () {

        // Given a CurvePoint -- by setUp()

        // When try to find all CurvePoint
        List<CurvePoint> responseList = curvePointRepository.findAll();

        // Then return list of CurvePoint
        assertFalse(responseList.isEmpty());
        assertTrue(responseList.contains(curvePoint));

    }



    @Test
    void findById_shouldReturnCurvePoint_withValidId () {

        // Given a CurvePoint -- by setUp()
        final Integer curvePointId = curvePoint.getId();

        // When try to find CurvePoint by Id
        Optional<CurvePoint> response = curvePointRepository.findById(curvePointId);

        // Then return the CurvePoint
        assertEquals(curvePoint.getTerm(), response.get().getTerm());
        assertEquals(curvePoint.getValue(), response.get().getValue());

    }



    @Test
    void findById_shouldReturnEmpty_withoutValidId () {

        // Given a CurvePoint -- by setUp()
        final Integer curvePointId = curvePoint.getId() + 1;

        // When try to find CurvePoint by Id
        Optional<CurvePoint> response = curvePointRepository.findById(curvePointId);

        // Then return an empty CurvePoint
        assertTrue(response.isEmpty());

    }



    @Test
    void delete_shouldDeleteCurvePoint () {

        // Given a CurvePoint -- by setUp()
        curvePointRepository.save(curvePoint);

        // When try to delete CurvePoint
        curvePointRepository.deleteById(curvePoint.getId());

        // Then CurvePoint is deleted
        Optional<CurvePoint> response = curvePointRepository.findById(curvePoint.getId());
        assertTrue(response.isEmpty());

    }

}
