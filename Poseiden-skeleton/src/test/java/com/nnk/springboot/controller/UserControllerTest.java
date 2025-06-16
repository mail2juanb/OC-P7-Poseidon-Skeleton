package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;


    // Setup before each test to initialize the SecurityContext
    @BeforeEach
    void setUp() {

        // Configure a SecurityContext to simulate the absence of authentication (unauthenticated user)
        SecurityContext securityContext = org.mockito.Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }



    @Test
    void validate_WhenNotAuthenticated_ShouldAssignDefaultRoleUser(){

        // Given: A user with no role provided, not authenticated
        User user = new User();
        user.setUsername("guest");
        user.setPassword("Guest123!");
        user.setFullname("Guest User");

        // And: No field errors
        when(bindingResult.hasErrors()).thenReturn(false);

        // Simulate an unauthenticated user
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("anonymousUser", null)
        );

        // When: Calling validate without authentication
        String view = userController.validate(user, bindingResult, model, redirectAttributes);

        // Then: Role should be USER and redirection to /home
        assertEquals("redirect:/home", view);
        verify(userService).create(argThat(u -> "USER".equals(u.getRole())));
    }



    @Test
    void validate_UserIsAuthenticated_ShouldRedirectToList() {

        // Given: A user is authenticated (username = "user", no authorities)
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = new User();
        user.setUsername("user");
        user.setPassword("Password123");

        // And: There are no validation errors in the BindingResult
        when(bindingResult.hasErrors()).thenReturn(false);

        // When: The validate method is called with the user object
        String result = userController.validate(user, bindingResult, model, redirectAttributes);

        // Then: The user should be redirected to /home
        assertEquals("redirect:/home", result);
    }



    @Test
    void validate_ValidationErrors_ShouldReturnToForm() {

        // Given: A user with invalid data (username = "invalidUser", password = "123")
        when(bindingResult.hasErrors()).thenReturn(true);

        User user = new User();
        user.setUsername("invalidUser");
        user.setPassword("123");

        // When: The validate method is called with the user object
        String result = userController.validate(user, bindingResult, model, redirectAttributes);

        // Then: The user should be returned to the form (user/add page)
        assertEquals("user/add", result);
    }

}
