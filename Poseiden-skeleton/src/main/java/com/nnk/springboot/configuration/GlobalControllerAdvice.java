package com.nnk.springboot.configuration;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 * Global controller that automatically injects the logged-in user's information
 * into the template for all Thymeleaf views.
 */
@ControllerAdvice
public class GlobalControllerAdvice {


    /**
     * Adds the logged-in user's information (username and role) to the model
     * so that it can be accessed from the HTML views.
     * If no user is logged in, the attributes will be {@code null}.
     *
     * @param model the model used by the view
     */
    @ModelAttribute
    public void addUserToModel(Model model) {
        try {
            CustomUserDetails user = SecurityTools.getConnectedUser();
            model.addAttribute("userConnected", user.getUsername());
            model.addAttribute("userRole", user.getRole());
        } catch (Exception e) {
            // Si l'utilisateur n'est pas connecté, on ignore silencieusement
            model.addAttribute("userConnected", null);
            model.addAttribute("userRole", null);
        }

    }
}
