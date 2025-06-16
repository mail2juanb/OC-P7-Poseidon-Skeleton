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




    @Test
    public void toHome() {

        // Given a request to the root URL ("/")

        // When the toHome method is called
        String viewName = homeController.toHome(model);

        // Then it should return the "home" view
        assertThat(viewName).isEqualTo("home");
    }




    @Test
    public void home() {

        // Given a request to the "/home" URL

        // When the home method is called
        String viewName = homeController.home(model);

        // Then it should return the "home" view
        assertThat(viewName).isEqualTo("home");
    }

}
