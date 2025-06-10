package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


/**
 * Controller responsible for handling web requests related to User entities.
 * Provides endpoints for listing, creating, updating, and deleting Users.
 */
@Slf4j
@Controller
public class UserController {

    // Injecting the User repository for direct database access
    @Autowired
    private UserRepository userRepository;

    // Injecting the generic CRUD service for User entities
    private final AbstractCrudService<User> service;

    // Constructor for dependency injection
    public UserController(AbstractCrudService<User> service) {
        this.service = service;
    }


    /**
     * Display the list of all users. Only accessible by users with ADMIN role.
     *
     * @param model the Spring Model to populate the view
     * @return the view name to display the list of users
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/user/list")
    public String home(Model model) {

        final List<User> users = service.getAll();
        log.debug("Liste des utilisateurs récupérés : {}", users);
        model.addAttribute("users", users);

        return "user/list";
    }


    /**
     * Display the form to add a new user.
     * Automatically sets the role to 'USER' by default.
     *
     * @param user an empty User object to bind form inputs
     * @return the view name of the add form
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        user.setRole("USER"); // Valeur par défaut
        log.debug("GET PAGE USER/ADD VIEW");
        return "user/add";
    }


    /**
     * Validate and persist a new user.
     * Hashes the password before saving and assigns the default role if needed.
     *
     * @param user the User object populated from form inputs
     * @param result contains validation results
     * @param model the model to return data in case of validation error
     * @param redirectAttributes to add flash attributes for success message
     * @return redirect to list view on success, or return to form in case of validation errors
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (!result.hasErrors()) {
            // Check if the user is authenticated
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser");
            log.debug("isAuthenticated = {}", isAuthenticated);

            // Prevent setting a role other than 'USER' if the user is not authenticated
            if (!isAuthenticated) {
                user.setRole("USER");
            }

            // Explicit role validation to avoid validation errors
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER");
            }

            // Hash the password using BCrypt
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            // Create the user through the service
            service.create(user);

            // Success message for redirection
            redirectAttributes.addFlashAttribute("message", "User successfully added.");

            // Redirect based on authentication status
            return isAuthenticated ? "redirect:/user/list" : "redirect:/home";
        }

        return "user/add";
    }


    /**
     * Display the form to update an existing user.
     *
     * @param id the ID of the User to update
     * @param model the model to pass the current User to the view
     * @return the update form view name
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // Clear the password field for security reasons
        user.setPassword("");
        model.addAttribute("user", user);

        return "user/update";
    }


    /**
     * Validate and update an existing user.
     * The password is rehashed before saving.
     *
     * @param id the ID of the User to update
     * @param user the updated User object
     * @param result holds validation errors if any
     * @param model the model to return data in case of validation error
     * @return redirect to list view on success, otherwise return to form
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "user/update";
        }

        // Hash the password before updating the user
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);

        // Save the updated user
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());

        return "redirect:/user/list";
    }


    /**
     * Delete a User by its ID.
     *
     * @param id the ID of the User to delete
     * @param model the Spring model (not used here but may be used in the future)
     * @return redirect to the User list view
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());

        return "redirect:/user/list";
    }

}
