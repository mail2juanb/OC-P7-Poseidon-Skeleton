package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller responsible for handling the login page requests.
 * <p>
 * This controller provides the login view, where the user can input their credentials.
 * It also initializes a new {@link User} object in the model for binding user input during login.
 * </p>
 */
@Slf4j
@Controller
public class LoginController {

    // Injecting the UserRepository to interact with the database.
    @Autowired
    private UserRepository userRepository;


    /**
     * Handles GET requests to the "/login" URL and renders the login page.
     * <p>
     * It adds an empty {@link User} object to the model so that it can be used for form binding
     * when the user submits their credentials.
     * </p>
     *
     * @param model the model object used to pass attributes to the view (here, a new User instance)
     * @return the name of the view to render, i.e., "login"
     */
    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("user", new User());

        return "login";
    }

    // NOTE: The POST request for login handling is managed by Spring Security.

}
