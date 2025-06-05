package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
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
@WebMvcTest(CurvePointController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudService<CurvePoint> service;



    // 1. Test de la méthode home : récupérer et afficher la liste des CurvePoints
    @Test
    public void home_ShouldReturn200AndCurvePointListView() throws Exception {

        // Given : une liste de CurvePoints simulée renvoyée par le service
        CurvePoint cp1 = new CurvePoint();
        cp1.setId(1);
        cp1.setCurveId(10);
        cp1.setTerm(1.0);
        cp1.setValue(100.0);

        CurvePoint cp2 = new CurvePoint();
        cp2.setId(2);
        cp2.setCurveId(20);
        cp2.setTerm(2.0);
        cp2.setValue(200.0);

        List<CurvePoint> list = Arrays.asList(cp1, cp2);

        BDDMockito.given(service.getAll()).willReturn(list);

        // When : on effectue une requête GET sur /curvePoint/list
        // Then : la vue "curvePoint/list" est retournée avec une liste contenant 2 éléments
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePoints", hasSize(2))); // Vérifie la taille de la liste
    }



    // 2. Test de la méthode addCurvePointForm : afficher le formulaire d'ajout de CurvePoint
    @Test
    public void addCurvePointForm_ShouldReturn200AndAddView() throws Exception {

        // Given : l'utilisateur souhaite ajouter un nouveau CurvePoint
        // When : on effectue une requête GET sur /curvePoint/add
        // Then : la vue "curvePoint/add" est retournée avec un formulaire vide
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }



    // 3. Test de la méthode validate : soumettre un formulaire valide pour ajouter un CurvePoint
    @Test
    public void validate_ShouldReturn302AndRedirectToList() throws Exception {
        // Given : un formulaire avec des données valides pour un CurvePoint
        // When : on effectue une requête POST sur /curvePoint/validate
        // Then : la requête redirige vers la page de liste des CurvePoints
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).create(any(CurvePoint.class)); // Vérifie que la création a été effectuée
    }



    // 4. Test de la méthode validate : soumettre un formulaire invalide pour ajouter un CurvePoint
    @Test
    public void validate_WithValidationError_ShouldReturn200AndAddView() throws Exception {

        // Given : un formulaire avec un champ "curveId" vide
        // When : on effectue une requête POST sur /curvePoint/validate avec des données invalides
        // Then : la vue "curvePoint/add" est retournée avec une erreur sur le champ "curveId"
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "")  // champ vide = erreur de validation
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));
    }



    // 5. Test de la méthode showUpdateForm : afficher le formulaire de mise à jour pour un CurvePoint existant
    @Test
    public void showUpdateForm_ShouldReturn200AndUpdateView() throws Exception {

        // Given : un CurvePoint existant avec un ID valide
        Integer id = 1;
        CurvePoint cp = new CurvePoint();
        cp.setId(id);
        cp.setCurveId(1);
        cp.setTerm(10.0);
        cp.setValue(20.0);

        BDDMockito.given(service.getById(id)).willReturn(cp);

        // When : on effectue une requête GET sur /curvePoint/update/{id} avec un ID valide
        // Then : la vue "curvePoint/update" est retournée avec les données du CurvePoint à mettre à jour
        mockMvc.perform(get("/curvePoint/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", hasProperty("id", is(id))));
    }



    // 6. Test de la méthode showUpdateForm : afficher une erreur si l'ID du CurvePoint est inconnu
    @Test
    public void showUpdateForm_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : un ID de CurvePoint inexistant
        Integer id = 999;
        BDDMockito.given(service.getById(id)).willThrow(new IllegalArgumentException("Not found"));

        // When : on effectue une requête GET sur /curvePoint/update/{id} avec un ID inconnu
        // Then : la requête redirige vers la page d'erreur
        mockMvc.perform(get("/curvePoint/update/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }



    // 7. Test de la méthode update : soumettre un formulaire valide pour mettre à jour un CurvePoint
    @Test
    public void update_ShouldReturn302AndRedirectToList() throws Exception {

        // Given : un formulaire valide pour mettre à jour un CurvePoint avec un ID
        Integer id = 1;

        // When : on effectue une requête POST sur /curvePoint/update/{id} avec des données valides
        // Then : la requête redirige vers la page de liste des CurvePoints
        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .param("curveId", "1")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).update(any(CurvePoint.class)); // Vérifie que la mise à jour a été effectuée
    }



    // 8. Test de la méthode update : soumettre un formulaire invalide pour mettre à jour un CurvePoint
    @Test
    public void update_WithValidationError_ShouldReturn200AndUpdateView() throws Exception {

        // Given : un formulaire invalide pour mettre à jour un CurvePoint avec un champ "curveId" vide
        Integer id = 1;

        // When : on effectue une requête POST sur /curvePoint/update/{id} avec des données invalides
        // Then : la vue "curvePoint/update" est retournée avec une erreur sur le champ "curveId"
        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .param("curveId", "")  // champ vide = erreur
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));
    }



    // 9. Test de la méthode delete : supprimer un CurvePoint existant
    @Test
    public void delete_ShouldReturn302AndRedirectToList() throws Exception {

        // Given : un ID valide pour supprimer un CurvePoint
        Integer id = 1;

        // When : on effectue une requête GET sur /curvePoint/delete/{id} pour supprimer le CurvePoint
        // Then : la requête redirige vers la page de liste des CurvePoints
        mockMvc.perform(get("/curvePoint/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).delete(id); // Vérifie que la suppression a été effectuée
    }



    // 10. Test de la méthode delete : tenter de supprimer un CurvePoint inexistant
    @Test
    public void delete_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : un ID de CurvePoint inexistant
        Integer unknownId = 999;
        BDDMockito.willThrow(new IllegalArgumentException("Not found")).given(service).delete(unknownId);

        // When : on effectue une requête GET sur /curvePoint/delete/{id} pour supprimer un CurvePoint inexistant
        // Then : la requête redirige vers la page d'erreur
        mockMvc.perform(get("/curvePoint/delete/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }

}
