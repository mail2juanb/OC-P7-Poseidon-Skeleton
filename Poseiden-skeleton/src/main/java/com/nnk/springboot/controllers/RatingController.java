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


// TODO : Ajouter la documentation Javadoc

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/rating")
public class RatingController {

    private final CrudService<Rating> service;


    @GetMapping("/list")
    public String home(Model model) {

        final List<Rating> ratings = service.getAll();
        log.debug("List of Rating found : {}", ratings);
        model.addAttribute("ratings", ratings);

        return "rating/list";

    }



    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }



    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        // NOTE: check data valid and save to db, after saving return Rating list
        // Check moodysRating            = @Size(max = 125)
        // Check sandPRating             = @Size(max = 125)
        // Check fitchRating             = @Size(max = 125)
        // Check orderNumber             = @Min -128 / @Max 127

        log.debug("Rating moodysRating = {}", rating.getMoodysRating());
        log.debug("Rating sandPRating = {}", rating.getSandPRating());
        log.debug("Rating fitchRating = {}", rating.getFitchRating());
        log.debug("Rating orderNumber = {}", rating.getOrderNumber());

        if (hasValidationErrors(rating, result, model)) {
            return "rating/add";
        }

        service.create(rating);

        return "redirect:/rating/list";

    }



    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final Rating rating = service.getById(id);
        log.debug("Rating with id {} = {}", id, rating);
        model.addAttribute("rating", rating);

        return "rating/update";

    }



    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {

        // NOTE: check required fields, if valid call service to update Rating and return Rating list
        // Check moodysRating            = @Size(max = 125)
        // Check sandPRating             = @Size(max = 125)
        // Check fitchRating             = @Size(max = 125)
        // Check orderNumber             = @Min -128 / @Max 127

        if (hasValidationErrors(rating, result, model)) {
            return "rating/add";
        }

        service.update(rating);
        log.debug("Update Rating with id {} = {}", id, rating);

        return "redirect:/rating/list";

    }



    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        log.info("Delete Rating with id {}", id);
        service.delete(id);

        return "redirect:/rating/list";

    }





    private boolean hasValidationErrors(Rating rating, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("trade", rating);
            return true;
        }

        return false;

    }

}
