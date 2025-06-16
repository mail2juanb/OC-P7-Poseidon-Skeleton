package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingDomainTest {

    @Test
    void update_shouldUpdateFieldsFromGivenRating() {

        // Given an original Rating
        Rating original = new Rating();
        original.setId(1);
        original.setMoodysRating("Moody-A");
        original.setSandPRating("S&P-A");
        original.setFitchRating("Fitch-A");
        original.setOrderNumber(10);

        // And a Rating with new values
        Rating updated = new Rating();
        updated.setId(1);
        updated.setMoodysRating("Moody-B");
        updated.setSandPRating("S&P-B");
        updated.setFitchRating("Fitch-B");
        updated.setOrderNumber(20);

        // When calling update
        original.update(updated);

        // Then the original Rating is updated with values from updated
        assertEquals(updated.getId(), original.getId());
        assertEquals(updated.getMoodysRating(), original.getMoodysRating());
        assertEquals(updated.getSandPRating(), original.getSandPRating());
        assertEquals(updated.getFitchRating(), original.getFitchRating());
        assertEquals(updated.getOrderNumber(), original.getOrderNumber());
    }

}
