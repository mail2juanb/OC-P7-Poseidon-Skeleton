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




@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/curvePoint")
public class CurvePointController {

    private final CrudService<CurvePoint> service;



    @GetMapping("/list")
    public String home(Model model) {

        final List<CurvePoint> curvePoints = service.getAll();
        log.debug("List of curvePoint found : {}", curvePoints);
        model.addAttribute("curvePoints", curvePoints);

        return "curvePoint/list";

    }



    @GetMapping("/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }



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



    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        final CurvePoint curvePoint = service.getById(id);
        log.debug("CurvePoint with id {} = {}", id, curvePoint);
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";

    }



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



    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {

        log.info("Delete CurvePoint with id {}", id);
        service.delete(id);

        return "redirect:/curvePoint/list";

    }




    private boolean hasValidationErrors(CurvePoint curvePoint, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.error("Validation errors in {} : {}", getClass().getSimpleName(), result.getAllErrors());
            model.addAttribute("curvePoint", curvePoint);
            return true;
        }

        return false;

    }
}
