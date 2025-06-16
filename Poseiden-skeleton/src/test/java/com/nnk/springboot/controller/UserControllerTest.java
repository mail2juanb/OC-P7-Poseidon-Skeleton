package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.IdLimitReachedException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;




    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void validate_WhenIdLimitReached_ShouldReturn302AndRedirectToErrorPageWithFlashMessage() throws Exception {

        // Given: the service throws an exception when creating a user
        doThrow(new IdLimitReachedException("Maximum number of entries (127) reached"))
                .when(userService).create(any(User.class));

        // When: POST to /user/validate with valid data
        // Then: Redirect to /error with flash message
        mockMvc.perform(post("/user/validate")
                            .with(csrf())
                        .param("username", "john")
                        .param("password", "Test1234!")
                        .param("fullname", "John Doe")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
                .andExpect(flash().attribute("errorMessage", "Maximum number of entries (127) reached"));

    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void home_ShouldReturnUserListViewWithUsers() throws Exception {

        // Given: a fictitious user in the returned list
        User user = new User();
        user.setUsername("user");
        user.setPassword("pass");
        user.setFullname("fullname");
        user.setRole("USER");

        when(userService.getAll()).thenReturn(List.of(user));

        // When: GET on /user/list
        // Then: View user/list with user attributes
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }


    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void addUser_ShouldReturnAddFormWithDefaultRole() throws Exception {

        // When: GET on /user/add
        // Then: user/add view returned
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void showUpdateForm_WithValidId_ShouldReturnUpdateForm() throws Exception {

        // Given : a user with ID 1
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("pass");
        user.setFullname("fullname");
        user.setRole("USER");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When: GET on /user/update/1
        // Then: View user/update with the user as a model
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WithValidData_ShouldRedirectToUserList() throws Exception {

        // Given : the user to update exists
        when(userRepository.findAll()).thenReturn(List.of());

        // When: Valid POST with CSRF
        // Then: Redirection to /user/list
        mockMvc.perform(post("/user/update/1")
                        .with(csrf())
                        .param("username", "updatedUser")
                        .param("password", "NewPass123!")
                        .param("fullname", "Updated Fullname")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WithInvalidData_ShouldReturnUpdateForm() throws Exception {

        // When: POST with invalid data (e.g., empty password)
        // Then: The form is redisplayed
        mockMvc.perform(post("/user/update/1")
                        .with(csrf())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "Invalid User")
                        .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WithValidId_ShouldRedirectToUserList() throws Exception {

        // Given : a user with ID 1
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("pass");
        user.setFullname("fullname");
        user.setRole("USER");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(List.of());

        // When: GET on /user/delete/1
        // Then: Redirection to /user/list
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WithInvalidId_ShouldRedirectToErrorPage() throws Exception {

        // Given : No users found for ID 99
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // When: GET on /user/delete/99
        // Then: Redirect to /error with flash message
        mockMvc.perform(get("/user/delete/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
                .andExpect(flash().attribute("errorMessage", containsString("Invalid user Id")));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void validate_WhenUserAuthenticated_ShouldKeepRole() throws Exception {

        // Given : a user with an “ADMIN” role
        User user = new User();
        user.setUsername("john");
        user.setPassword("Test1234!");
        user.setFullname("John Doe");
        user.setRole("ADMIN");

        // When: POST to /user/validate
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "john")
                        .param("password", "Test1234!")
                        .param("fullname", "John Doe")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Check that the service was called with the user having the “ADMIN” role
        verify(userService).create(argThat(u -> u.getRole().equals("ADMIN")));
    }


    @Test
    @WithMockUser(roles = "USER")
    void validate_WhenInvalidData_ShouldReturnAddForm() throws Exception {

        // Given : a user with invalid data (for example empty password)
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "john")
                        .param("password", "")
                        .param("fullname", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("user", "password"));
    }

}
