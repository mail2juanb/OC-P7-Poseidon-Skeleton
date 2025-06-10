package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
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
 * Controller responsible for handling web requests related to RuleName entities.
 * Provides endpoints for listing, creating, updating, and deleting RuleNames.
 */
@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    // Injecting the generic CRUD service for RuleName entities
    private final CrudService<RuleName> service;



    /**
     * Display the list of all RuleNames.
     *
     * @param model the Spring Model to populate the view
     * @return the view name to display the list of rule names
     */
    @GetMapping("/list")
    public String home(Model model) {

        final List<RuleName> ruleNames = service.getAll();
        log.debug("List of RuleNames found : {}", ruleNames);
        model.addAttribute("ruleNames", ruleNames);

        return "ruleName/list";

    }


    /**
     * Display the form to add a new RuleName.
     *
     * @param ruleName an empty RuleName object to bind form inputs
     * @return the view name of the add form
     */
    @GetMapping("/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }


    /**
     * Validate and persist a new RuleName.
     *
     * @param ruleName the RuleName object populated from form inputs
     * @param result contains validation results
     * @param model the model to return data in case of validation error
     * @return redirect to list view on success, otherwise return to form
     */
    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

        // NOTE: check data valid and save to db, after saving return RuleName list
        // Check name                = @Size(max = 125)
        // Check description         = @Size(max = 125)
        // Check json                = @Size(max = 125)
        // Check template            = @Size(max = 512)
        // Check sqlStr              = @Size(max = 125)
        // Check sqlPart             = @Size(max = 125)

        log.debug("RuleName name = {}", ruleName.getName());
        log.debug("RuleName description = {}", ruleName.getDescription());
        log.debug("RuleName json = {}", ruleName.getJson());
        log.debug("RuleName template = {}", ruleName.getTemplate());
        log.debug("RuleName sqlStr = {}", ruleName.getSqlStr());
        log.debug("RuleName sqlPart = {}", ruleName.getSqlPart());

        if (hasValidationErrors(ruleName, result, model)) {
            return "ruleName/add";
        }

        service.create(ruleName);

        return "redirect:/ruleName/list";

    }


    /**
     * Display the form to update an existing RuleName.
     *
     * @param id the ID of the RuleName to update
     * @param model the model to pass the current RuleName to the view
     * @return the update form view name
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final RuleName ruleName = service.getById(id);
        log.debug("RuleName with id {} = {}", id, ruleName);
        model.addAttribute("ruleName", ruleName);

        return "ruleName/update";
    }


    /**
     * Validate and update an existing RuleName.
     *
     * @param id the ID of the RuleName to update
     * @param ruleName the updated RuleName object
     * @param result holds validation errors if any
     * @param model the model to return data in case of validation error
     * @return redirect to list view on success, otherwise return to form
     */
    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {

        // NOTE: check required fields, if valid call service to update RuleName and return RuleName list
        // Check name                = @Size(max = 125)
        // Check description         = @Size(max = 125)
        // Check json                = @Size(max = 125)
        // Check template            = @Size(max = 512)
        // Check sqlStr              = @Size(max = 125)
        // Check sqlPart             = @Size(max = 125)

        if (hasValidationErrors(ruleName, result, model)) {
            return "ruleName/update";
        }

        service.update(ruleName);
        log.debug("Update RuleName with id {} = {}", id, ruleName);

        return "redirect:/ruleName/list";
    }


    /**
     * Delete a RuleName by its ID.
     *
     * @param id the ID of the RuleName to delete
     * @param model the Spring model (not used here but may be used in the future)
     * @return redirect to the RuleName list view
     */
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        log.info("Delete RuleName with id {}", id);
        service.delete(id);

        return "redirect:/ruleName/list";

    }



    /**
     * Utility method to check for validation errors and return them in the model.
     *
     * @param ruleName the entity being validated
     * @param result the result of the validation
     * @param model the model to populate with errors
     * @return true if errors exist, false otherwise
     */
    private boolean hasValidationErrors(RuleName ruleName, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("ruleName", ruleName);
            return true;
        }

        return false;

    }

}
