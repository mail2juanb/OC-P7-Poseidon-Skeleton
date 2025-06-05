package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
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
@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc(addFilters = false) // <--- désactive les filtres Spring Security
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudService<Trade> service;

    // 1. Test de la méthode home : récupérer et afficher la liste des Trade
    @Test
    public void home_ShouldReturn200AndTradeListView() throws Exception {

        // Given : une liste de Trade simulée renvoyée par le service
        Trade trade1 = new Trade();
        trade1.setId(1);
        trade1.setAccount("Account1");
        trade1.setType("Type1");

        Trade trade2 = new Trade();
        trade2.setId(2);
        trade2.setAccount("Account2");
        trade2.setType("Type2");

        List<Trade> trades = Arrays.asList(trade1, trade2);
        BDDMockito.given(service.getAll()).willReturn(trades);

        // When : on effectue une requête GET sur /trade/list
        // Then : la vue "trade/list" est retournée avec une liste contenant 2 éléments
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("trades", hasSize(2)));
    }



    // 2. Test de la méthode addTradeForm : accéder au formulaire d'ajout
    @Test
    public void addTradeForm_ShouldReturnTradeAddView() throws Exception {

        // When : on effectue une requête GET sur /trade/add
        // Then : la vue "trade/add" est retournée avec le formulaire vide
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }



    // 3. Test de la méthode validate : valider et enregistrer un nouveau Trade
    @Test
    public void validateTrade_ShouldCreateTradeAndRedirectToList() throws Exception {

        // When : on soumet un formulaire POST valide à /trade/validate
        // Then : le service de création est appelé et la redirection vers /trade/list est effectuée
        mockMvc.perform(post("/trade/validate")
                        .param("account", "ValidAccount")
                        .param("type", "ValidType")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).create(any(Trade.class));
    }



    // 4. Test de la méthode validate : formulaire invalide => retour au formulaire d'ajout
    @Test
    public void validateTrade_WithValidationError_ShouldReturnAddForm() throws Exception {

        // When : on soumet un formulaire POST invalide à /trade/validate (champ "account" vide)
        // Then : le formulaire "trade/add" est renvoyé avec une erreur de validation
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "ValidType")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"));
    }



    // 5. Test de la méthode showUpdateForm : afficher le formulaire de mise à jour
    @Test
    public void showUpdateForm_ShouldReturnTradeUpdateView() throws Exception {

        // Given : un Trade existant retourné par le service
        Trade trade = new Trade();
        trade.setId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");

        BDDMockito.given(service.getById(1)).willReturn(trade);

        // When : on effectue une requête GET sur /trade/update/1
        // Then : la vue "trade/update" est retournée avec l'objet Trade dans le modèle
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", hasProperty("id", is(1))));
    }



    // 6. Test showUpdateForm : id inexistant => redirection vers page d'erreur
    @Test
    public void showUpdateForm_WithInvalidId_ShouldRedirectToError() throws Exception {

        // Given : le service renvoie une exception pour l'ID 999
        BDDMockito.given(service.getById(999)).willThrow(new IllegalArgumentException("Not found"));

        // When : on effectue une requête GET sur /trade/update/999
        // Then : redirection vers la page d’erreur
        mockMvc.perform(get("/trade/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }



    // 7. Test de la méthode updateTrade : mettre à jour un Trade existant
    @Test
    public void updateTrade_ShouldUpdateTradeAndRedirectToList() throws Exception {

        // When : on soumet un formulaire POST valide à /trade/update/1
        // Then : la méthode update du service est appelée et la redirection est effectuée
        mockMvc.perform(post("/trade/update/1")
                        .param("account", "UpdatedAccount")
                        .param("type", "UpdatedType")
                        .param("buyQuantity", "200.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).update(any(Trade.class));
    }



    // 8. Test de la méthode updateTrade : erreur de validation => retour au formulaire
    @Test
    public void updateTrade_WithValidationError_ShouldReturnUpdateForm() throws Exception {

        // When : on soumet un formulaire POST invalide (champ account vide)
        // Then : le formulaire "trade/update" est renvoyé avec erreur de validation
        mockMvc.perform(post("/trade/update/1")
                        .param("account", "")
                        .param("type", "Type")
                        .param("buyQuantity", "123.45"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"));
    }



    // 9. Test de la méthode deleteTrade : suppression d'un Trade existant
    @Test
    public void deleteTrade_ShouldDeleteTradeAndRedirectToList() throws Exception {

        // When : on effectue une requête GET sur /trade/delete/1
        // Then : le Trade est supprimé et redirection vers /trade/list
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).delete(1);
    }



    // 10. Test deleteTrade : suppression échoue car ID inexistant
    @Test
    public void deleteTrade_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : suppression échoue car ID inexistant
        BDDMockito.willThrow(new IllegalArgumentException("Not found")).given(service).delete(999);

        // When : on effectue une requête GET sur /trade/delete/999
        // Then : redirection vers la page d'erreur
        mockMvc.perform(get("/trade/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }
}
