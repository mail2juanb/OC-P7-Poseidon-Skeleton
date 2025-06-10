package com.nnk.springboot.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller responsible for handling error pages, specifically for HTTP status 403 (Forbidden).
 * <p>
 * This controller is designed to display a custom error message for unauthorized access attempts.
 * </p>
 */
@Slf4j
@Controller
public class ErrorController {


    /**
     * Handles requests to the "/403" URL and displays a custom error page when a user is unauthorized.
     *
     * @param model the model used by the view layer, where we add an error message
     * @return the name of the view to render ("403")
     */
    @GetMapping("/403")
    public String error403(Model model) {
        log.debug("GET ERROR_403 PAGE VIEW");
        model.addAttribute("errorMsg", "You are not authorized to access this page.");
        return "403";
    }

}
