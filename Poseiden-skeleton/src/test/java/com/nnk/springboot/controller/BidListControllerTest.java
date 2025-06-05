package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.AbstractCrudService;
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
@WebMvcTest(BidListController.class)
@AutoConfigureMockMvc(addFilters = false) // <--- désactive les filtres Spring Security
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbstractCrudService<BidList> service;  // Mock de la dépendance service


    // 1. Test de la méthode home : récupérer et afficher la liste des BidList
    @Test
    public void home_ShouldReturn200AndBidListListView() throws Exception {

        // Given : une liste de bidList simulée renvoyée par le service
        BidList bid1 = new BidList();
        bid1.setId(1);
        bid1.setAccount("Account1");
        bid1.setType("Type1");
        bid1.setBidQuantity(100.0);

        BidList bid2 = new BidList();
        bid2.setId(2);
        bid2.setAccount("Account2");
        bid2.setType("Type2");
        bid2.setBidQuantity(200.0);

        List<BidList> bidLists = Arrays.asList(bid1, bid2);

        BDDMockito.given(service.getAll()).willReturn(bidLists);

        // When : on effectue une requête GET sur /bidList/list
        // Then : la vue "bidList/list" est retournée avec une liste contenant 2 éléments
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidLists", hasSize(2)));  // Vérifie la taille de la liste

    }



    // 2. Test de la méthode addBidForm : afficher le formulaire d'ajout
    @Test
    public void addBidForm_ShouldReturn200AndBidListAddView() throws Exception {

        // When : on effectue une requête GET sur /bidList/add
        // Then : la vue "bidList/add" est retournée sans erreur
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }



    // 3.1 Test de la méthode validate : valider le formulaire d'ajout et rediriger vers la liste
    @Test
    public void validateBid_ShouldReturn302AndBidListListView() throws Exception {

        // Given : un objet BidList valide avec tous les champs requis
        BidList bid = new BidList();
        bid.setId(null);
        bid.setAccount("Account1");
        bid.setType("Type1");
        bid.setBidQuantity(100.0);

        // When : on effectue une requête POST valide vers /bidList/validate
        // Then : une redirection vers /bidList/list est attendue et le service est appelé
        mockMvc.perform(post("/bidList/validate")
                        .param("account", bid.getAccount())
                        .param("type", bid.getType())
                        .param("bidQuantity", String.valueOf(bid.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));


        verify(service).create(any(BidList.class));  // Vérifie que la méthode create a été appelée
    }



    // 3.2 Test de la méthode validate : soumettre un formulaire invalide et revenir au formulaire d'ajout
    @Test
    public void validateBid_WithValidationError_ShouldReturn200AndBidListAddView() throws Exception {

        // Given : un champ obligatoire (account) est vide
        // When : on soumet le formulaire avec une erreur de validation
        // Then : la vue "bidList/add" est affichée et l’erreur est attachée au modèle
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")  // Champ vide pour déclencher une erreur
                        .param("type", "Type1")
                        .param("bidQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"));;
    }



    // 4.1 Test de la méthode showUpdateForm : afficher le formulaire de mise à jour
    @Test
    public void showUpdateForm_ShouldReturn200AndBidListUpdateView() throws Exception {

        // Given : un BidList existant avec un ID donné
        Integer id = 1;

        BidList bidList = new BidList();
        bidList.setId(id);
        bidList.setAccount("Account1");
        bidList.setType("Type1");
        bidList.setBidQuantity(100.0);

        BDDMockito.given(service.getById(id)).willReturn(bidList);

        // When : on effectue une requête GET sur /bidList/update/{id}
        // Then : la vue "bidList/update" est affichée avec l’objet attendu dans le modèle
        mockMvc.perform(get("/bidList/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", hasProperty("id", is(id))));  // Vérifie l'ID du BidList
    }



    // 4.2 Test de la méthode showUpdateForm : l’ID fourni ne correspond à aucun BidList existant
    @Test
    public void showUpdateForm_WithUnknownId_ShouldReturn302AndRedirectToErrorPage() throws Exception {

        // Given : aucun BidList trouvé, déclenchement d'une exception
        Integer unknownId = 999;
        BDDMockito.given(service.getById(unknownId)).willThrow(new IllegalArgumentException("BidList not found"));

        // When / Then : l'utilisateur est redirigé vers /error
        mockMvc.perform(get("/bidList/update/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }



    // 5.1 Test de la méthode updateBid : valider le formulaire de mise à jour et rediriger vers la liste
    @Test
    public void updateBid_ShouldReturn302AndShowBidListListView() throws Exception {

        // Given : un objet BidList valide pour la mise à jour
        Integer id = 1;
        BidList bidList = new BidList();
        bidList.setId(id);
        bidList.setAccount("UpdatedAccount");
        bidList.setType("UpdatedType");
        bidList.setBidQuantity(150.0);

        // When : on effectue une requête POST sur /bidList/update/{id} avec des données valides
        // Then : une redirection vers /bidList/list est attendue et le service est appelé
        mockMvc.perform(post("/bidList/update/{id}", id)
                        .param("account", bidList.getAccount())
                        .param("type", bidList.getType())
                        .param("bidQuantity", String.valueOf(bidList.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service).update(any(BidList.class));  // Vérifie que la méthode update a été appelée
    }



    // 5.2 Test de la méthode updateBid : soumettre un formulaire invalide de mise à jour
    @Test
    public void updateBid_WithValidationErrors_ShouldReturn200AndBidListUpdateView() throws Exception {

        // Given : un champ obligatoire (account) est vide
        Integer id = 1;

        // When : on effectue une requête POST invalide sur /bidList/update/{id}
        // Then : la vue "bidList/update" est affichée et l’erreur de validation est détectée
        mockMvc.perform(post("/bidList/update/{id}", id)
                        .param("account", "")
                        .param("type", "Type1")
                        .param("bidQuantity", "100.0"))
                .andExpect(status().isOk()) // Pas de redirection car erreur de validation
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"));
    }



    // 6.1 Test de la méthode deleteBid : supprimer un BidList et rediriger vers la liste
    @Test
    public void deleteBid_ShouldReturn302AndShowBidListListView() throws Exception {

        // Given : un ID existant
        Integer id = 1;

        // When : on effectue une requête GET sur /bidList/delete/{id}
        // Then : une redirection vers /bidList/list est attendue et la suppression est invoquée
        mockMvc.perform(get("/bidList/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service).delete(id);  // Vérifie que la méthode delete a été appelée
    }



    // 6.2 Test de deleteBid avec ID inconnu : redirection vers /error
    @Test
    public void deleteBid_WithUnknownId_ShouldReturn302AndRedirectToErrorPage() throws Exception {

        // Given : suppression échoue car ID inconnu
        Integer unknownId = 999;
        BDDMockito.willThrow(new IllegalArgumentException("BidList not found")).given(service).delete(unknownId);

        // When / Then : redirection vers /error
        mockMvc.perform(get("/bidList/delete/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }

}
