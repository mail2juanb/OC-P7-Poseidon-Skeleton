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




    // Given a login request, when the login page is accessed, then it should return the correct view name.
    @Test
    public void loginView() {

        // Given
        User user = new User(); // Un utilisateur de base
        when(model.addAttribute("user", user)).thenReturn(null);

        // When
        String viewName = loginController.login(model);

        // Then
        assertThat(viewName).isEqualTo("login"); // Vérifie que le nom de la vue est "login"
        verify(model).addAttribute("user", new User()); // Vérifie que l'attribut "user" a bien été ajouté au modèle
    }




    // Given a request to login, when the login page is accessed, then the "user" model attribute should be set.
    @Test
    public void loginModelAttribute() {

        // Given
        User expectedUser = new User(); // L'objet user attendu

        // When
        loginController.login(model);

        // Then
        verify(model).addAttribute("user", expectedUser); // Vérifie que l'attribut "user" a été ajouté
    }




     //Test to ensure no interaction with UserRepository, since it's not used in the current method.
    @Test
    public void noInteractionWithUserRepository() {

        // Given
        User user = new User();

        // When
        loginController.login(model);

        // Then
        verify(userRepository, never()).findAll(); // Aucun appel au repository
    }

}
