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

@RequiredArgsConstructor
@Slf4j
@Controller
public class TradeController {

    // NOTE: Inject Trade service
    //private final AbstractCrudService<Trade> service;
    private final CrudService<Trade> service;

    // NOTE : L'annotation @RequiredArgsConstructor remplace le constructeur
//    public TradeController(AbstractCrudService<Trade> service) {
//        this.service = service;
//    }

    // TODO : Ajouter la documentation Javadoc

    // TODO : Remplacer l'annotation RequestMapping par un GetMapping. La request est à mettre au niveau
    //  de la classe afin de définir un emplacement spécifique @RequestMapping("/trade"). Puis les autres méthodes
    //  doivent être corrigées et retirer le /trade/.

    // TODO : Implémenter un générateur / une méthode pour éviter de répéter du log error


    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // NOTE: find all Trade, add to model
        final List<Trade> trades = service.getAll();
        log.info("Liste des Trade récupérés : {}", trades);
        model.addAttribute("trades", trades);

        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        // NOTE: check data valid and save to db, after saving return Trade list
        // Check Account            = @NotBlank
        // Check Type               = @NotBlank
        // Check Buy Quantity       =

        log.debug("Trade account = {}", trade.getAccount());
        log.debug("Trade type = {}", trade.getType());
        log.debug("Trade Buy Quantity = {}", trade.getBuyQuantity());

        if (result.hasErrors()) {
            log.error("Validation errors found on {} : {}", getClass().getSimpleName() , result.getAllErrors());
            model.addAttribute("trade", trade);
            return "trade/add";
        }

        try {
            service.create(trade);
        } catch (Exception exception){
            log.error("Service error in {} during updateTrade : {}", exception.getClass().getSimpleName(), exception.getMessage());
            return "trade/add";
        }

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        // NOTE: get Trade by id and to model then show to the form
        final Trade trade = service.getById(id);
        log.debug("Trade ayant l'id {} = {}", id, trade);
        model.addAttribute("trade", trade);

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {

        // NOTE: check required fields, if valid call service to update Trade and return Trade list
        // Check Account            = @NotBlank
        // Check Type               = @NotBlank
        // Check Buy Quantity       =

        if (result.hasErrors()) {
            log.error("Validation errors found on {} : {}", getClass().getSimpleName() , result.getAllErrors());
            model.addAttribute("trade", trade);
            return "trade/update";
        }

        try {
            service.update(trade);
        } catch (Exception exception){
            log.error("Service error in {} during updateTrade : {}", exception.getClass().getSimpleName(), exception.getMessage());
            return "trade/update";
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        // NOTE: Find Trade by id and delete the Trade, return to Trade list
        service.delete(id);

        return "redirect:/trade/list";
    }

}
