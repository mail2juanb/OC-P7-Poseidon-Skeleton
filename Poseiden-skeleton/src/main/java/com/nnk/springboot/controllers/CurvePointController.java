package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
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
 * Controller responsible for handling web requests related to CurvePoint entities.
 * This includes displaying, adding, updating, and deleting CurvePoints.
 */
@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/curvePoint")
public class CurvePointController {

    // Injecting the generic CRUD service for CurvePoint entities
    private final CrudService<CurvePoint> service;


    /**
     * Display the list of all CurvePoints.
     *
     * @param model the Spring model to hold attributes for the view
     * @return the name of the view displaying the list
     */
    @GetMapping("/list")
    public String home(Model model) {

        final List<CurvePoint> curvePoints = service.getAll();
        log.debug("List of curvePoint found : {}", curvePoints);
        model.addAttribute("curvePoints", curvePoints);

        return "curvePoint/list";

    }


    /**
     * Display the form for adding a new CurvePoint.
     *
     * @param curvePoint the CurvePoint object (empty) used to bind form inputs
     * @return the name of the form view
     */
    @GetMapping("/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }


    /**
     * Validate and create a new CurvePoint.
     *
     * @param curvePoint the CurvePoint object populated from the form
     * @param result holds validation errors if any
     * @param model the Spring model to return values back to the view
     * @return redirect to list if valid, or stay on form view otherwise
     */
    @PostMapping("/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        // NOTE: check data valid and save to db, after saving return Curve list
        // Check CurveId            = @Min -128 / @Max 127 / @NotNull
        // Check Term               = none
        // Check Value              = none

        log.debug("CurvePoint curveId = {}", curvePoint.getCurveId());
        log.debug("CurvePoint term = {}", curvePoint.getTerm());
        log.debug("CurvePoint value = {}", curvePoint.getValue());

        if (hasValidationErrors(curvePoint, result, model)) {
            return "curvePoint/add";
        }

        service.create(curvePoint);

        return "redirect:/curvePoint/list";

    }


    /**
     * Display the form for updating an existing CurvePoint.
     *
     * @param id the ID of the CurvePoint to update
     * @param model the model to populate the form with the current data
     * @return the update form view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final CurvePoint curvePoint = service.getById(id);
        log.debug("CurvePoint with id {} = {}", id, curvePoint);
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";

    }


    /**
     * Validate and update an existing CurvePoint.
     *
     * @param id the ID of the CurvePoint to update
     * @param curvePoint the updated CurvePoint object from the form
     * @param result binding result to check for validation errors
     * @param model the model used to pass data to the view
     * @return redirect to list if successful, or return to form if validation fails
     */
    @PostMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {

        // NOTE: check required fields, if valid call service to update Curve and return Curve list
        // Check CurveId            = @Min -128 / @Max 127 / @NotNull
        // Check Term               = none
        // Check Value              = none

        if (hasValidationErrors(curvePoint, result, model)) {
            return "curvePoint/update";
        }

        service.update(curvePoint);
        log.debug("Update CurvePoint with id {} = {}", id, curvePoint);

        return "redirect:/curvePoint/list";
    }


    /**
     * Delete a CurvePoint by its ID.
     *
     * @param id the ID of the CurvePoint to delete
     * @param model the model used for UI feedback (not currently used)
     * @return redirect to the CurvePoint list after deletion
     */
    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {

        log.info("Delete CurvePoint with id {}", id);
        service.delete(id);

        return "redirect:/curvePoint/list";

    }



    /**
     * Utility method to handle validation errors.
     * Adds the invalid object and errors to the model.
     *
     * @param curvePoint the entity being validated
     * @param result the binding result containing validation errors
     * @param model the model to populate with error information
     * @return true if errors are present, false otherwise
     */
    private boolean hasValidationErrors(CurvePoint curvePoint, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("curvePoint", curvePoint);
            return true;
        }

        return false;

    }
}
