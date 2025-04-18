package com.nnk.springboot.repository;


import com.nnk.springboot.domain.Rating;
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
public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    private final Rating rating = new Rating();



    @BeforeEach
    public void setUp () {
        rating.setMoodysRating("UI-MoodysRating");
        rating.setSandPRating("UI-SandPRating");
        rating.setFitchRating("UI-FitchRating");
        rating.setOrderNumber(120);
        ratingRepository.save(rating);
    }



    @Test
    void save_shouldSaveRating () {

        // Given a Rating -- by setUp()

        // When try to save Rating -- by setUp()
        Rating response = ratingRepository.save(rating);

        // Then Rating is saved
        assertTrue(ratingRepository.findById(response.getId()).isPresent());

    }



    @Test
    void update_shouldUpdateRating () {

        // Given a Rating -- by setUp()

        // Given fields to update
        rating.setMoodysRating("UI-MoodysRating_Updated");
        rating.setOrderNumber(4);

        // When try to update Rating
        Rating response = ratingRepository.save(rating);

        // Then Rating is updated
        assertEquals("UI-MoodysRating_Updated", response.getMoodysRating());
        assertEquals(4, response.getOrderNumber());

    }



    @Test
    void findAll_shouldReturnRating () {

        // Given a Rating -- by setUp()

        // When try to find all Rating
        List<Rating> responseList = ratingRepository.findAll();

        // Then return list of Rating
        assertFalse(responseList.isEmpty());
        assertTrue(responseList.contains(rating));

    }



    @Test
    void findById_shouldReturnRating_withValidId () {

        // Given a Rating -- by setUp()
        final Integer ratingId = rating.getId();

        // When try to find Rating by Id
        Optional<Rating> response = ratingRepository.findById(ratingId);

        // Then return the Rating
        assertEquals(rating.getMoodysRating(), response.get().getMoodysRating());
        assertEquals(rating.getSandPRating(), response.get().getSandPRating());
        assertEquals(rating.getFitchRating(), response.get().getFitchRating());
        assertEquals(rating.getOrderNumber(), response.get().getOrderNumber());

    }



    @Test
    void findById_shouldReturnEmpty_withoutValidId () {

        // Given a Rating -- by setUp()
        final Integer ratingId = rating.getId() + 1;

        // When try to find Rating by Id
        Optional<Rating> response = ratingRepository.findById(ratingId);

        // Then return an empty Rating
        assertTrue(response.isEmpty());

    }



    @Test
    void delete_shouldDeleteRating () {

        // Given a Rating -- by setUp()
        ratingRepository.save(rating);

        // When try to delete Rating
        ratingRepository.deleteById(rating.getId());

        // Then Rating is deleted
        Optional<Rating> response = ratingRepository.findById(rating.getId());
        assertTrue(response.isEmpty());

    }

}
