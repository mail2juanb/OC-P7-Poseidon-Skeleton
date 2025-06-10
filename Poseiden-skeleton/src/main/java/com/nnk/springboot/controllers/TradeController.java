package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
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
 * Controller responsible for handling web requests related to Trade entities.
 * Provides endpoints for listing, creating, updating, and deleting Trades.
 */
@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/trade")
public class TradeController {

    // Injecting the generic CRUD service for Trade entities
    private final CrudService<Trade> service;


    /**
     * Display the list of all Trades.
     *
     * @param model the Spring Model to populate the view
     * @return the view name to display the list of trades
     */
    @GetMapping("/list")
    public String home(Model model) {

        final List<Trade> trades = service.getAll();
        log.debug("List of Trade found : {}", trades);
        model.addAttribute("trades", trades);

        return "trade/list";

    }


    /**
     * Display the form to add a new Trade.
     *
     * @param trade an empty Trade object to bind form inputs
     * @return the view name of the add form
     */
    @GetMapping("/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }


    /**
     * Validate and persist a new Trade.
     *
     * @param trade the Trade object populated from form inputs
     * @param result contains validation results
     * @param model the model to return data in case of validation error
     * @return redirect to list view on success, otherwise return to form
     */
    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        // NOTE: check data valid and save to db, after saving return Trade list
        // Check Account            = @NotBlank / @Size(max = 30)
        // Check Type               = @NotBlank / @Size(max = 30)
        // Check Buy Quantity       =

        log.debug("Trade account = {}", trade.getAccount());
        log.debug("Trade type = {}", trade.getType());
        log.debug("Trade Buy Quantity = {}", trade.getBuyQuantity());

        if (hasValidationErrors(trade, result, model)) {
            return "trade/add";
        }

        service.create(trade);

        return "redirect:/trade/list";
    }


    /**
     * Display the form to update an existing Trade.
     *
     * @param id the ID of the Trade to update
     * @param model the model to pass the current Trade to the view
     * @return the update form view name
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final Trade trade = service.getById(id);
        log.debug("Trade with id {} = {}", id, trade);
        model.addAttribute("trade", trade);

        return "trade/update";

    }


    /**
     * Validate and update an existing Trade.
     *
     * @param id the ID of the Trade to update
     * @param trade the updated Trade object
     * @param result holds validation errors if any
     * @param model the model to return data in case of validation error
     * @return redirect to list view on success, otherwise return to form
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {

        // NOTE: check required fields, if valid call service to update Trade and return Trade list
        // Check Account            = @NotBlank / @Size(max = 30)
        // Check Type               = @NotBlank / @Size(max = 30)
        // Check Buy Quantity       = none

        if (hasValidationErrors(trade, result, model)) {
            return "trade/update";
        }

        service.update(trade);
        log.debug("Update Trade with id {} = {}", id, trade);
        return "redirect:/trade/list";

    }


    /**
     * Delete a Trade by its ID.
     *
     * @param id the ID of the Trade to delete
     * @param model the Spring model (not used here but may be used in the future)
     * @return redirect to the Trade list view
     */
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        log.info("Delete Trade with id {}", id);
        service.delete(id);

        return "redirect:/trade/list";

    }



    /**
     * Utility method to check for validation errors and return them in the model.
     *
     * @param trade the entity being validated
     * @param result the result of the validation
     * @param model the model to populate with errors
     * @return true if errors exist, false otherwise
     */
    private boolean hasValidationErrors(Trade trade, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("trade", trade);
            return true;
        }

        return false;

    }

}
