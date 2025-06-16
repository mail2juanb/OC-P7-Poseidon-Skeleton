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
@AutoConfigureMockMvc(addFilters = false)
public class TradeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudService<Trade> service;


    @Test
    public void home_ShouldReturn200AndTradeListView() throws Exception {

        // Given : a simulated trade list returned by the service
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

        // When: a GET request is made to /trade/list
        // Then: the “trade/list” view is returned with a list containing 2 items
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("trades", hasSize(2)));
    }


    @Test
    public void addTradeForm_ShouldReturnTradeAddView() throws Exception {

        // When: a GET request is made to /trade/add
        // Then: the “trade/add” view is returned with the form empty
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }


    @Test
    public void validateTrade_ShouldCreateTradeAndRedirectToList() throws Exception {

        // When: a valid POST form is submitted to /trade/validate
        // Then: the creation service is called and redirection to /trade/list is performed
        mockMvc.perform(post("/trade/validate")
                        .param("account", "ValidAccount")
                        .param("type", "ValidType")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).create(any(Trade.class));
    }


    @Test
    public void validateTrade_WithValidationError_ShouldReturnAddForm() throws Exception {

        // When: an invalid POST form is submitted to /trade/validate (empty “account” field)
        // Then: the “trade/add” form is returned with a validation error
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "ValidType")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"));
    }


    @Test
    public void showUpdateForm_ShouldReturnTradeUpdateView() throws Exception {

        // Given : an existing Trade returned by the service
        Trade trade = new Trade();
        trade.setId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");

        BDDMockito.given(service.getById(1)).willReturn(trade);

        // When: a GET request is made to /trade/update/1
        // Then: the “trade/update” view is returned with the Trade object in the model
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", hasProperty("id", is(1))));
    }


    @Test
    public void showUpdateForm_WithInvalidId_ShouldRedirectToError() throws Exception {

        // Given : The service returns an exception for ID 999.
        BDDMockito.given(service.getById(999)).willThrow(new IllegalArgumentException("Not found"));

        // When: a GET request is made to /trade/update/999
        // Then: redirection to the error page
        mockMvc.perform(get("/trade/update/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }


    @Test
    public void updateTrade_ShouldUpdateTradeAndRedirectToList() throws Exception {

        // When: a valid POST form is submitted to /trade/update/1
        // Then: the service's update method is called and redirection is performed
        mockMvc.perform(post("/trade/update/1")
                        .param("account", "UpdatedAccount")
                        .param("type", "UpdatedType")
                        .param("buyQuantity", "200.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).update(any(Trade.class));
    }


    @Test
    public void updateTrade_WithValidationError_ShouldReturnUpdateForm() throws Exception {

        // When: an invalid POST form is submitted (empty account field)
        // Then: the “trade/update” form is returned with a validation error
        mockMvc.perform(post("/trade/update/1")
                        .param("account", "")
                        .param("type", "Type")
                        .param("buyQuantity", "123.45"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"));
    }


    @Test
    public void deleteTrade_ShouldDeleteTradeAndRedirectToList() throws Exception {

        // When: a GET request is made to /trade/delete/1
        // Then: the trade is deleted and redirected to /trade/list
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service).delete(1);
    }


    @Test
    public void deleteTrade_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : Deletion fails because ID does not exist
        BDDMockito.willThrow(new IllegalArgumentException("Not found")).given(service).delete(999);

        // When: a GET request is made to /trade/delete/999
        // Then: redirection to the error page
        mockMvc.perform(get("/trade/delete/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }
}
