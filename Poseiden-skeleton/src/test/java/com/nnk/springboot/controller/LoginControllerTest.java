package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @InjectMocks
    private LoginController loginController;




    @Test
    public void loginView() {

        // Given a login request
        User user = new User(); // Un utilisateur de base
        when(model.addAttribute("user", user)).thenReturn(null);

        // When the login page is accessed
        String viewName = loginController.login(model);

        // Then it should return the correct view name
        assertThat(viewName).isEqualTo("login");
        verify(model).addAttribute("user", new User());
    }





    @Test
    public void loginModelAttribute() {

        // Given a request to login
        User expectedUser = new User(); // L'objet user attendu

        // When the login page is accessed
        loginController.login(model);

        // Then the "user" model attribute should be set
        verify(model).addAttribute("user", expectedUser);
    }





    @Test
    public void noInteractionWithUserRepository() {

        // Given a user
        User user = new User();

        // When the login page is accessed
        loginController.login(model);

        // Then ensure no interaction with UserRepository
        verify(userRepository, never()).findAll(); // Aucun appel au repository
    }

}
