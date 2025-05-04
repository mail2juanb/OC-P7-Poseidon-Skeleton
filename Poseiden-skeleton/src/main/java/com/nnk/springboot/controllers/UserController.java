package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    private final AbstractCrudService<User> service;

    public UserController(AbstractCrudService<User> service) {
        this.service = service;
    }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
//        final List<User> users = userRepository.findAll();
//        model.addAttribute("users", users);

        final List<User> users = service.getAll();
        log.debug("Liste des utilisateurs récupérés : {}", users);
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        user.setRole("USER"); // Valeur par défaut
        log.debug("GET PAGE USER/ADD VIEW");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            // Vérifie si l'utilisateur est connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser");
            log.debug("isAuthenticated = {}", isAuthenticated);

            // Empêche de forcer un rôle autre que USER si non authentifié
            if (!isAuthenticated) {
                user.setRole("USER");
            }

            // Hash du mot de passe
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            userRepository.save(user);

            // Message de succès à afficher après la redirection
            redirectAttributes.addFlashAttribute("message", "User successfully added.");

            return isAuthenticated ? "redirect:/user/list" : "redirect:/home";
        }

        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
