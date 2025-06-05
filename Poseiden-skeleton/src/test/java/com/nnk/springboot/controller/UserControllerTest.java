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



    /**
     * 1. Test de la méthode validate :
     * Lorsqu'on atteint la limite d'enregistrements (127),
     * une exception personnalisée est levée et redirige vers la page d'erreur.
     */
    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void validate_WhenIdLimitReached_ShouldReturn302AndRedirectToErrorPageWithFlashMessage() throws Exception {

        // Given : le service lève une exception lors de la création d’un utilisateur
        doThrow(new IdLimitReachedException("Maximum number of entries (127) reached"))
                .when(userService).create(any(User.class));

        // When : POST vers /user/validate avec des données valides
        // Then : Redirection vers /error avec message flash
        mockMvc.perform(post("/user/validate")
                            .with(csrf()) // CSRF nécessaire pour POST même si les filtres sont désactivés
                        .param("username", "john")
                        .param("password", "Test1234!")
                        .param("fullname", "John Doe")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
                .andExpect(flash().attribute("errorMessage", "Maximum number of entries (127) reached"));

    }




    /**
     * 2. Test de la méthode home :
     * Retourne la vue contenant la liste des utilisateurs.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void home_ShouldReturnUserListViewWithUsers() throws Exception {

        // Given : un utilisateur fictif dans la liste retournée
        User user = new User();
        user.setUsername("user");
        user.setPassword("pass");
        user.setFullname("fullname");
        user.setRole("USER");

        when(userService.getAll()).thenReturn(List.of(user));

        // When : GET sur /user/list
        // Then : Vue user/list avec attributs utilisateurs
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }



    /**
     * 3. Test de la méthode addUser :
     * Retourne la page d’ajout d’utilisateur avec le rôle "USER" par défaut.
     */
    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void addUser_ShouldReturnAddFormWithDefaultRole() throws Exception {

        // Given : aucun prérequis

        // When : GET sur /user/add
        // Then : Vue user/add retournée
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }



    /**
     * 4. Test de la méthode showUpdateForm :
     * Affiche le formulaire de mise à jour pour l'utilisateur existant.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void showUpdateForm_WithValidId_ShouldReturnUpdateForm() throws Exception {

        // Given : un utilisateur avec ID 1
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("pass");
        user.setFullname("fullname");
        user.setRole("USER");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When : GET sur /user/update/1
        // Then : Vue user/update avec l’utilisateur en modèle
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }



    /**
     * 5. Test de la méthode updateUser :
     * Met à jour l’utilisateur si les données sont valides, puis redirige.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WithValidData_ShouldRedirectToUserList() throws Exception {

        // Given : l’utilisateur à mettre à jour existe
        when(userRepository.findAll()).thenReturn(List.of());

        // When : POST valide avec CSRF
        // Then : Redirection vers /user/list
        mockMvc.perform(post("/user/update/1")
                        .with(csrf())
                        .param("username", "updatedUser")
                        .param("password", "NewPass123!")
                        .param("fullname", "Updated Fullname")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }



    /**
     * 6. Test de la méthode updateUser :
     * Si les données sont invalides (ex : champ vide), le formulaire est réaffiché.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WithInvalidData_ShouldReturnUpdateForm() throws Exception {

        // Given : aucun utilisateur nécessaire

        // When : POST avec des données invalides (ex. mot de passe vide)
        // Then : Le formulaire est réaffiché
        mockMvc.perform(post("/user/update/1")
                        .with(csrf())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "Invalid User")
                        .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }



    /**
     * 7. Test de la méthode deleteUser :
     * Supprime l’utilisateur avec ID valide et redirige vers la liste.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WithValidId_ShouldRedirectToUserList() throws Exception {

        // Given : un utilisateur avec ID 1
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("pass");
        user.setFullname("fullname");
        user.setRole("USER");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(List.of());

        // When : GET sur /user/delete/1
        // Then : Redirection vers /user/list
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }



    /**
     * 8. Test de la méthode deleteUser :
     * Si l’ID est invalide, une exception est levée.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WithInvalidId_ShouldRedirectToErrorPage() throws Exception {

        // Given : aucun utilisateur trouvé pour l'ID 99
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // When : GET sur /user/delete/99
        // Then : Redirection vers /error avec message flash
        mockMvc.perform(get("/user/delete/99"))
                .andExpect(status().is3xxRedirection())  // Vérifie la redirection
                .andExpect(redirectedUrl("/error"))  // Vérifie l'URL de la redirection
                .andExpect(flash().attribute("errorMessage", containsString("Invalid user Id")));  // Vérifie le message d'erreur dans le flash
    }



    /**
     * 9. Test de la méthode validate : Cas où l'utilisateur est authentifié et le rôle est maintenu.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void validate_WhenUserAuthenticated_ShouldKeepRole() throws Exception {
        // Given : un utilisateur avec un rôle "ADMIN"
        User user = new User();
        user.setUsername("john");
        user.setPassword("Test1234!");
        user.setFullname("John Doe");
        user.setRole("ADMIN");

        // When : POST vers /user/validate
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "john")
                        .param("password", "Test1234!")
                        .param("fullname", "John Doe")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        // Vérifiez que le service a bien été appelé avec l'utilisateur ayant le rôle "ADMIN"
        verify(userService).create(argThat(u -> u.getRole().equals("ADMIN")));
    }



    /**
     * 10. Test de la méthode validate : Cas où les données sont invalides (erreurs de validation).
     */
    @Test
    @WithMockUser(roles = "USER")
    void validate_WhenInvalidData_ShouldReturnAddForm() throws Exception {
        // Given : un utilisateur avec des données invalides (par exemple, mot de passe vide)
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("username", "john")
                        .param("password", "")  // Mot de passe vide
                        .param("fullname", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))  // Formulaire de création d'utilisateur réaffiché
                .andExpect(model().attributeHasFieldErrors("user", "password"));  // Vérifie qu'il y a une erreur sur le champ "password"
    }

}
