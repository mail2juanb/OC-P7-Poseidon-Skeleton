package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;


/**
 * Controller responsible for handling web requests related to Rating entities.
 * Provides endpoints for listing, adding, updating, and deleting Ratings.
 */
@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/rating")
public class RatingController {

    // Injecting the generic CRUD service for Rating entities
    private final CrudService<Rating> service;


    /**
     * Displays the list of all Rating entries.
     *
     * @param model the model to hold attributes for the view
     * @return the name of the view displaying the Rating list
     */
    @GetMapping("/list")
    public String home(Model model) {

        final List<Rating> ratings = service.getAll();
        model.addAttribute("ratings", ratings);

        return "rating/list";

    }


    /**
     * Displays the form for creating a new Rating.
     *
     * @param rating the Rating object to bind form inputs
     * @return the name of the form view
     */
    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }


    /**
     * Validate and persist a new Rating.
     *
     * @param rating the Rating object populated from form inputs
     * @param result contains validation results
     * @param model the model to return data in case of validation error
     * @return redirect to list view on success, otherwise return to form
     */
    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        if (hasValidationErrors(rating, result, model)) {
            return "rating/add";
        }

        service.create(rating);

        return "redirect:/rating/list";

    }


    /**
     * Display the form for updating an existing Rating.
     *
     * @param id the ID of the Rating to update
     * @param model the model to populate the form with the current data
     * @return the update form view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final Rating rating = service.getById(id);
        model.addAttribute("rating", rating);

        return "rating/update";

    }


    /**
     * Validate and update an existing Rating.
     *
     * @param id the ID of the Rating to update
     * @param rating the updated Rating object
     * @param result holds validation errors if any
     * @param model the model to return data in case of validation error
     * @return redirect to list view on success, otherwise return to form
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {

        if (hasValidationErrors(rating, result, model)) {
            return "rating/update";
        }

        service.update(rating);

        return "redirect:/rating/list";

    }


    /**
     * Delete a Rating by its ID.
     *
     * @param id the ID of the Rating to delete
     * @param model the Spring model (not used here but may be used in the future)
     * @return redirect to the Rating list view
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        service.delete(id);

        return "redirect:/rating/list";

    }



    /**
     * Utility method to check for validation errors and return them in the model.
     *
     * @param rating the entity being validated
     * @param result the result of the validation
     * @param model the model to populate with errors
     * @return true if errors exist, false otherwise
     */
    private boolean hasValidationErrors(Rating rating, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("rating", rating);
            return true;
        }

        return false;

    }

}
