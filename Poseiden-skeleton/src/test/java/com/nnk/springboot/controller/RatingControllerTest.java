package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.CrudService;
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
@WebMvcTest(RatingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudService<Rating> service;




    @Test
    public void home_ShouldReturn200AndRatingListView() throws Exception {

        // Given : a simulated rating list returned by the service
        Rating rating1 = new Rating();
        rating1.setId(1);
        rating1.setMoodysRating("Aaa");
        rating1.setSandPRating("Aa");
        rating1.setFitchRating("A");
        rating1.setOrderNumber(1);

        Rating rating2 = new Rating();
        rating2.setId(2);
        rating2.setMoodysRating("Baa");
        rating2.setSandPRating("A");
        rating2.setFitchRating("B");
        rating2.setOrderNumber(2);

        List<Rating> ratings = Arrays.asList(rating1, rating2);

        BDDMockito.given(service.getAll()).willReturn(ratings);

        // When: a GET request is made to /rating/list
        // Then: the “rating/list” view is returned with a list containing 2 items
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratings", hasSize(2)));
    }


    @Test
    public void addRatingForm_ShouldReturn200AndAddView() throws Exception {

        // Given: the user wants to add a new rating
        // When: a GET request is made to /rating/add
        // Then: the “rating/add” view is returned with an empty form
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }


    @Test
    public void validate_ShouldReturn302AndRedirectToList() throws Exception {

        // Given: a form with valid data for a Rating
        // When: a POST request is made to /rating/validate
        // Then: the request redirects to the Ratings list page
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).create(any(Rating.class));
    }


    @Test
    public void validate_WithValidationError_ShouldReturn200AndAddView() throws Exception {

        // Given : a form with a “moodysRating” field that is too large
        String longMoodysRating = "A".repeat(126);

        // When: a POST request is made to /rating/validate with invalid data
        // Then: the “rating/add” view is returned with an error on the “moodysRating” field
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", longMoodysRating)
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"));
    }


    @Test
    public void showUpdateForm_ShouldReturn200AndUpdateView() throws Exception {

        // Given : an existing Rating with a valid ID
        Integer id = 1;
        Rating rating = new Rating();
        rating.setId(id);
        rating.setMoodysRating("Aaa");
        rating.setSandPRating("Aa");
        rating.setFitchRating("A");
        rating.setOrderNumber(1);

        BDDMockito.given(service.getById(id)).willReturn(rating);

        // When: a GET request is made to /rating/update/{id} with a valid ID
        // Then: the “rating/update” view is returned with the Rating data to be updated
        mockMvc.perform(get("/rating/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", hasProperty("id", is(id))));
    }


    @Test
    public void showUpdateForm_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : a non-existent Rating ID
        Integer id = 999;
        BDDMockito.given(service.getById(id)).willThrow(new IllegalArgumentException("Not found"));

        // When: a GET request is made to /rating/update/{id} with an unknown ID
        // Then: the request redirects to the error page
        mockMvc.perform(get("/rating/update/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }


    @Test
    public void update_ShouldReturn302AndRedirectToList() throws Exception {

        // Given: a valid form to update a Rating with an ID
        Integer id = 1;

        // When: a POST request is made to /rating/update/{id} with valid data
        // Then: the request redirects to the Ratings list page
        mockMvc.perform(post("/rating/update/{id}", id)
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).update(any(Rating.class));
    }


    @Test
    public void update_WithValidationError_ShouldReturn200AndUpdateView() throws Exception {

        // Given: an invalid form for updating a Rating with a “moodysRating” field that is too large
        Integer id = 1;
        String longMoodysRating = "A".repeat(126);

        // When: a POST request is made to /rating/update/{id} with invalid data
        // Then: the “rating/update” view is returned with an error on the “moodysRating” field
        mockMvc.perform(post("/rating/update/{id}", id)
                        .param("moodysRating", longMoodysRating)
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"));
    }


    @Test
    public void delete_ShouldReturn302AndRedirectToList() throws Exception {

        // Given: a valid ID to delete a Rating
        Integer id = 1;

        // When: a GET request is made to /rating/delete/{id} to delete the Rating
        // Then: the request redirects to the Ratings list page
        mockMvc.perform(get("/rating/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).delete(id);
    }


    @Test
    public void delete_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : a non-existent Rating ID
        Integer unknownId = 999;
        BDDMockito.willThrow(new IllegalArgumentException("Not found")).given(service).delete(unknownId);

        // When: a GET request is made to /rating/delete/{id} to delete a non-existent rating.
        // Then: the request redirects to the error page.
        mockMvc.perform(get("/rating/delete/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }

}
