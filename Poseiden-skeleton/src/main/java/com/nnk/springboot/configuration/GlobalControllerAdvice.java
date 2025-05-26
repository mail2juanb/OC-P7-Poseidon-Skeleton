package com.nnk.springboot.configuration;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

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
