package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.CrudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudService<Rating> service;



    // 1. Test de la méthode home : récupérer et afficher la liste des Ratings
    @Test
    public void home_ShouldReturn200AndRatingListView() throws Exception {

        // Given : une liste de Rating simulée renvoyée par le service
        Rating rating1 = new Rating();
        rating1.setId(1);
        rating1.setMoodysRating("Aaa");
        rating1.setSandPRating("Aa");
        rating1.setFitchRating("A");
        rating1.setOrderNumber(1);

        Rating rating2 = new Rating();
        rating2.setId(2);
        rating2.setMoodysRating("Baa");
        rating2.setSandPRating("A");
        rating2.setFitchRating("B");
        rating2.setOrderNumber(2);

        List<Rating> ratings = Arrays.asList(rating1, rating2);

        BDDMockito.given(service.getAll()).willReturn(ratings);

        // When : on effectue une requête GET sur /rating/list
        // Then : la vue "rating/list" est retournée avec une liste contenant 2 éléments
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratings", hasSize(2)));  // Vérifie la taille de la liste
    }



    // 2. Test de la méthode addRatingForm : afficher le formulaire d'ajout de Rating
    @Test
    public void addRatingForm_ShouldReturn200AndAddView() throws Exception {

        // Given : l'utilisateur souhaite ajouter un nouveau Rating
        // When : on effectue une requête GET sur /rating/add
        // Then : la vue "rating/add" est retournée avec un formulaire vide
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    // 3. Test de la méthode validate : soumettre un formulaire valide pour ajouter un Rating
    @Test
    public void validate_ShouldReturn302AndRedirectToList() throws Exception {

        // Given : un formulaire avec des données valides pour un Rating
        // When : on effectue une requête POST sur /rating/validate
        // Then : la requête redirige vers la page de liste des Ratings
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).create(any(Rating.class)); // Vérifie que la création a été effectuée
    }



    // 4. Test de la méthode validate : soumettre un formulaire invalide pour ajouter un Rating
    @Test
    public void validate_WithValidationError_ShouldReturn200AndAddView() throws Exception {

        // Given : un formulaire avec un champ "moodysRating" trop grand
        String longMoodysRating = "A".repeat(126); // 126 caractères pour dépasser la limite de 125

        // When : on effectue une requête POST sur /rating/validate avec des données invalides
        // Then : la vue "rating/add" est retournée avec une erreur sur le champ "moodysRating"
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", longMoodysRating)  // champ trop grand = erreur de validation
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"));
    }



    // 5. Test de la méthode showUpdateForm : afficher le formulaire de mise à jour pour un Rating existant
    @Test
    public void showUpdateForm_ShouldReturn200AndUpdateView() throws Exception {

        // Given : un Rating existant avec un ID valide
        Integer id = 1;
        Rating rating = new Rating();
        rating.setId(id);
        rating.setMoodysRating("Aaa");
        rating.setSandPRating("Aa");
        rating.setFitchRating("A");
        rating.setOrderNumber(1);

        BDDMockito.given(service.getById(id)).willReturn(rating);

        // When : on effectue une requête GET sur /rating/update/{id} avec un ID valide
        // Then : la vue "rating/update" est retournée avec les données du Rating à mettre à jour
        mockMvc.perform(get("/rating/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", hasProperty("id", is(id))));
    }



    // 6. Test de la méthode showUpdateForm : afficher une erreur si l'ID du Rating est inconnu
    @Test
    public void showUpdateForm_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : un ID de Rating inexistant
        Integer id = 999;
        BDDMockito.given(service.getById(id)).willThrow(new IllegalArgumentException("Not found"));

        // When : on effectue une requête GET sur /rating/update/{id} avec un ID inconnu
        // Then : la requête redirige vers la page d'erreur
        mockMvc.perform(get("/rating/update/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }



    // 7. Test de la méthode update : soumettre un formulaire valide pour mettre à jour un Rating
    @Test
    public void update_ShouldReturn302AndRedirectToList() throws Exception {

        // Given : un formulaire valide pour mettre à jour un Rating avec un ID
        Integer id = 1;

        // When : on effectue une requête POST sur /rating/update/{id} avec des données valides
        // Then : la requête redirige vers la page de liste des Ratings
        mockMvc.perform(post("/rating/update/{id}", id)
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).update(any(Rating.class)); // Vérifie que la mise à jour a été effectuée
    }



    // 8. Test de la méthode update : soumettre un formulaire invalide pour mettre à jour un Rating
    @Test
    public void update_WithValidationError_ShouldReturn200AndUpdateView() throws Exception {

        // Given : un formulaire invalide pour mettre à jour un Rating avec un champ "moodysRating" trop grand
        Integer id = 1;
        String longMoodysRating = "A".repeat(126); // 126 caractères pour dépasser la limite de 125

        // When : on effectue une requête POST sur /rating/update/{id} avec des données invalides
        // Then : la vue "rating/update" est retournée avec une erreur sur le champ "moodysRating"
        mockMvc.perform(post("/rating/update/{id}", id)
                        .param("moodysRating", longMoodysRating)  // champ trop grand = erreur
                        .param("sandPRating", "Aa")
                        .param("fitchRating", "A")
                        .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"));
    }



    // 9. Test de la méthode delete : supprimer un Rating existant
    @Test
    public void delete_ShouldReturn302AndRedirectToList() throws Exception {

        // Given : un ID valide pour supprimer un Rating
        Integer id = 1;

        // When : on effectue une requête GET sur /rating/delete/{id} pour supprimer le Rating
        // Then : la requête redirige vers la page de liste des Ratings
        mockMvc.perform(get("/rating/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(service).delete(id); // Vérifie que la suppression a été effectuée
    }



    // 10. Test de la méthode delete : tenter de supprimer un Rating inexistant
    @Test
    public void delete_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : un ID de Rating inexistant
        Integer unknownId = 999;
        BDDMockito.willThrow(new IllegalArgumentException("Not found")).given(service).delete(unknownId);

        // When : on effectue une requête GET sur /rating/delete/{id} pour supprimer un Rating inexistant
        // Then : la requête redirige vers la page d'erreur
        mockMvc.perform(get("/rating/delete/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }

}
