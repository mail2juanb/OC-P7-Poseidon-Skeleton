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


// TODO : Ajouter la documentation Javadoc

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/trade")
public class TradeController {

    private final CrudService<Trade> service;



    @GetMapping("/list")
    public String home(Model model) {

        final List<Trade> trades = service.getAll();
        log.debug("List of Trade found : {}", trades);
        model.addAttribute("trades", trades);

        return "trade/list";

    }



    @GetMapping("/add")
    public String addTrade(Trade trade) {
        return "trade/add";
    }



    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        // NOTE: check data valid and save to db, after saving return Trade list
        // Check Account            = @NotBlank
        // Check Type               = @NotBlank
        // Check Buy Quantity       =

        log.debug("Trade account = {}", trade.getAccount());
        log.debug("Trade type = {}", trade.getType());
        log.debug("Trade Buy Quantity = {}", trade.getBuyQuantity());

        if (hasValidationErrors(trade, result, model)) {
            return "trade/add";
        }

        // Try catch supprimé car attrapé par le GlobalExceptionHandler
//        try {
//            service.create(trade);
//        } catch (Exception exception){
//            log.error("Service error in {} during updateTrade : {}", exception.getClass().getSimpleName(), exception.getMessage());
//            return "trade/add";
//        }
        service.create(trade);

        return "redirect:/trade/list";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final Trade trade = service.getById(id);
        log.debug("Trade with id {} = {}", id, trade);
        model.addAttribute("trade", trade);

        return "trade/update";

    }


    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {

        // NOTE: check required fields, if valid call service to update Trade and return Trade list
        // Check Account            = @NotBlank
        // Check Type               = @NotBlank
        // Check Buy Quantity       = none

        if (hasValidationErrors(trade, result, model)) {
            return "trade/update";
        }

        // Try catch supprimé car attrapé par le GlobalExceptionHandler
//        try {
//            service.update(trade);
//        } catch (Exception exception){
//            log.error("Service error in {} during updateTrade : {}", exception.getClass().getSimpleName(), exception.getMessage());
//            return "trade/update";
//        }
        service.update(trade);
        log.debug("Update Trade with id {} = {}", id, trade);
        return "redirect:/trade/list";

    }


    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        log.info("Delete Trade with id {}", id);
        service.delete(id);

        return "redirect:/trade/list";

    }



    private boolean hasValidationErrors(Trade trade, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("trade", trade);
            return true;
        }

        return false;

    }

}
