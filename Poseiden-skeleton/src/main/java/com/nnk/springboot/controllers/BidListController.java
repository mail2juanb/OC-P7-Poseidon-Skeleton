package com.nnk.springboot.controllers;

import com.nnk.springboot.configuration.SecurityTools;
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



@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/bidList")
public class BidListController {

    private final AbstractCrudService<BidList> service;


    @RequestMapping("/list")
    public String home(Model model) {

//        final String userConnected = SecurityTools.getConnectedUser().getUsername();
//        log.debug("User Connected : {}", userConnected);
//        model.addAttribute("userConnected", userConnected);

        final List<BidList> bidLists = service.getAll();
        log.debug("List of BidList found : {}", bidLists);
        model.addAttribute("bidLists", bidLists);

        return "bidList/list";

    }



    @GetMapping("/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }



    @PostMapping("/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {

        // NOTE: check data valid and save to db, after saving return bid list
        // Check Account            = @NotBlank / @Size(max = 30)
        // Check Type               = @NotBlank / @Size(max = 30)
        // Check Buy Quantity       =

        log.debug("BidList account = {}", bid.getAccount());
        log.debug("BidList type = {}", bid.getType());
        log.debug("BidList Bid Quantity = {}", bid.getBidQuantity());

        if (hasValidationErrors(bid, result, model)) {
            return "bidList/add";
        }

        service.create(bid);

        return "redirect:/bidList/list";

    }



    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        // NOTE: get Bid by Id and to model then show to the form
        final BidList bidList = service.getById(id);
        log.debug("BidList with id {} = {}", id, bidList);
        model.addAttribute("bidList", bidList);

        return "bidList/update";

    }



    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {

        // NOTE: check required fields, if valid call service to update Bid and return list Bid
        // Check Account            = @NotBlank / @Size(max = 30)
        // Check Type               = @NotBlank / @Size(max = 30)
        // Check Bid Quantity       = none

        if (hasValidationErrors(bidList, result, model)) {
            return "bidList/update";
        }

        service.update(bidList);
        log.debug("Update BidList with id {} = {}", id, bidList);
        return "redirect:/bidList/list";

    }



    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        // NOTE: Find Bid by Id and delete the bid, return to Bid list
        log.info("Delete bidList with id {}", id);
        service.delete(id);

        return "redirect:/bidList/list";

    }



    private boolean hasValidationErrors(BidList bidList, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("bidList", bidList);
            return true;
        }

        return false;

    }

}
