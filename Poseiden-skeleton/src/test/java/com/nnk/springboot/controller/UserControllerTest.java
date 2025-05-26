package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.IdLimitReachedException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;


    @WithMockUser(roles = "ADMIN")
    @Test
    void validate_whenIdLimitReached_thenRedirectToErrorWith409() throws Exception {
        // Simule l’exception lancée par le service
        doThrow(new IdLimitReachedException("Maximum number of entries (127) reached"))
                .when(userService).create(any(User.class));

        mockMvc.perform(post("/user/validate")
                        .with(csrf()) // nécessaire pour POST sous Spring Security
                        .param("username", "john")
                        .param("password", "Test1234!")
                        .param("fullname", "John Doe")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
                .andExpect(flash().attribute("errorMessage", "Maximum number of entries (127) reached"));
    }



}
