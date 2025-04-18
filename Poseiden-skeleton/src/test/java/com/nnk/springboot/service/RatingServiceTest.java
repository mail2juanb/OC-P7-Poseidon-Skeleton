package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repository.RatingRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest extends AbstractCrudServiceTest<Rating> {

    @Mock
    private RatingRepository ratingRepository;

    @Override
    protected AbstractCrudService<Rating> initService() {
        this.repository = ratingRepository;
        return new RatingServiceImpl(ratingRepository);
    }


    @Override
    protected Rating createModelWithId(int id) {
        Rating rating = mock(Rating.class);
        lenient().when(rating.getId()).thenReturn(id);
        return rating;
    }


    @Override
    protected Rating createModelWithNullId() {
        Rating rating = new Rating();
        rating.setId(null);
        rating.setMoodysRating("Test");
        return rating;
    }


    @Override
    protected Rating createUpdatedModel() {
        return mock(Rating.class);
    }

}
