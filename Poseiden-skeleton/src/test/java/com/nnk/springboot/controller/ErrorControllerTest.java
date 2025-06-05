package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.ErrorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ErrorController.class)
public class ErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;




    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void givenError403_whenGet403Page_thenReturnCorrectView() throws Exception {

        // Given: No specific setup needed for this simple controller

        // When: Sending a GET request to the /403 URL
        mockMvc.perform(get("/403")
                .with(csrf())) // CSRF nécessaire pour POST même si les filtres sont désactivés
                // Then: Check if the view name is "403" and the error message is set
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attribute("errorMsg", containsString("not authorized to access")));
    }

}
