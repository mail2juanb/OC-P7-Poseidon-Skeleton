package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;



    // Given a request to the root URL ("/"), when the toHome method is called, then it should return the "home" view.
    @Test
    public void toHome() {

        // Given
        // Pas de préparation particulière nécessaire pour cette méthode

        // When
        String viewName = homeController.toHome(model);

        // Then
        assertThat(viewName).isEqualTo("home"); // Vérifie que la vue retournée est "home"
    }



    // Given a request to the "/home" URL, when the home method is called,then it should return the "home" view.
    @Test
    public void home() {

        // Given
        // Pas de préparation particulière nécessaire pour cette méthode

        // When
        String viewName = homeController.home(model);

        // Then
        assertThat(viewName).isEqualTo("home"); // Vérifie que la vue retournée est "home"
    }

}
