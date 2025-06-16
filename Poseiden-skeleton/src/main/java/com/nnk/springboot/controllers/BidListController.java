package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.AbstractCrudService;
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
 * Controller responsible for handling all web requests related to BidList entity.
 * Provides endpoints for listing, adding, updating, and deleting bids.
 */
@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/bidList")
public class BidListController {

    // Injecting the generic CRUD service for BidList entities
    private final AbstractCrudService<BidList> service;


    /**
     * Displays the list of all BidList entries.
     *
     * @param model Spring's UI model used to pass data to the view
     * @return the view name of the bid list page
     */
    @RequestMapping("/list")
    public String home(Model model) {

        final List<BidList> bidLists = service.getAll();
        model.addAttribute("bidLists", bidLists);

        return "bidList/list";
    }


    /**
     * Displays the form to create a new BidList entry.
     *
     * @param bid a new BidList object bound to the form
     * @return the view name for the add form
     */
    @GetMapping("/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }


    /**
     * Handles form submission to validate and create a new BidList entry.
     *
     * @param bid the BidList object filled from the form
     * @param result holds the validation result
     * @param model Spring's UI model to pass data if validation fails
     * @return a redirect to the bid list if successful, or the add form if validation fails
     */
    @PostMapping("/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {

        if (hasValidationErrors(bid, result, model)) {
            return "bidList/add";
        }

        service.create(bid);

        return "redirect:/bidList/list";

    }


    /**
     * Displays the form to update an existing BidList entry.
     *
     * @param id the ID of the bid to update
     * @param model Spring's UI model used to pass the bid to the form
     * @return the view name for the update form
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final BidList bidList = service.getById(id);
        model.addAttribute("bidList", bidList);

        return "bidList/update";

    }


    /**
     * Handles the form submission to update an existing BidList entry.
     *
     * @param id the ID of the bid being updated
     * @param bidList the updated bid data from the form
     * @param result holds the validation result
     * @param model Spring's UI model to pass data if validation fails
     * @return a redirect to the bid list if successful, or the update form if validation fails
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {

        if (hasValidationErrors(bidList, result, model)) {
            return "bidList/update";
        }

        service.update(bidList);

        return "redirect:/bidList/list";

    }


    /**
     * Deletes a BidList entry by its ID.
     *
     * @param id the ID of the bid to delete
     * @param model Spring's UI model (not used here but can be extended)
     * @return a redirect to the bid list after deletion
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        service.delete(id);

        return "redirect:/bidList/list";

    }


    /**
     * Helper method to handle validation errors and rebind the form.
     *
     * @param bidList the bid list object being validated
     * @param result holds validation errors
     * @param model Spring's UI model to send data back to the form
     * @return true if validation errors exist, false otherwise
     */
    private boolean hasValidationErrors(BidList bidList, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("bidList", bidList);
            return true;
        }

        return false;

    }

}
