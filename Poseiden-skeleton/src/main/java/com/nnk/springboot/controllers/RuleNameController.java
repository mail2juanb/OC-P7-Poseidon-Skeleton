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


// TODO : Ajouter la documentation Javadoc

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    private final CrudService<RuleName> service;



    @GetMapping("/list")
    public String home(Model model) {

        final List<RuleName> ruleNames = service.getAll();
        log.debug("List of RuleNames found : {}", ruleNames);
        model.addAttribute("ruleNames", ruleNames);

        return "ruleName/list";

    }



    @GetMapping("/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }



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



    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final RuleName ruleName = service.getById(id);
        log.debug("RuleName with id {} = {}", id, ruleName);
        model.addAttribute("ruleName", ruleName);

        return "ruleName/update";
    }



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



    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        log.info("Delete RuleName with id {}", id);
        service.delete(id);

        return "redirect:/ruleName/list";

    }





    private boolean hasValidationErrors(RuleName ruleName, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("ruleName", ruleName);
            return true;
        }

        return false;

    }

}
