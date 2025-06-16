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
@AutoConfigureMockMvc(addFilters = false)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbstractCrudService<BidList> service;



    @Test
    public void home_ShouldReturn200AndBidListListView() throws Exception {

        // Given : a simulated bidList returned by the service
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

        // When : We perform a GET request on /bidList/list.
        // Then : The “bidList/list” view is returned with a list containing two items.
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidLists", hasSize(2)));

    }




    @Test
    public void addBidForm_ShouldReturn200AndBidListAddView() throws Exception {

        // When : We perform a GET request on /bidList/add.
        // Then : The “bidList/add” view is returned without error.
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }




    @Test
    public void validateBid_ShouldReturn302AndBidListListView() throws Exception {

        // Given : a valid BidList object with all required fields
        BidList bid = new BidList();
        bid.setId(null);
        bid.setAccount("Account1");
        bid.setType("Type1");
        bid.setBidQuantity(100.0);

        // When : We make a valid POST request to /bidList/validate.
        // Then : A redirect to /bidList/list is expected and the service is called.
        mockMvc.perform(post("/bidList/validate")
                        .param("account", bid.getAccount())
                        .param("type", bid.getType())
                        .param("bidQuantity", String.valueOf(bid.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));


        verify(service).create(any(BidList.class));
    }




    @Test
    public void validateBid_WithValidationError_ShouldReturn200AndBidListAddView() throws Exception {

        // Given : A required field (account) is empty.
        // When : the form is submitted with a validation error
        // Then : The “bidList/add” view is displayed and the error is attached to the model.
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")  // Champ vide pour déclencher une erreur
                        .param("type", "Type1")
                        .param("bidQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"));
    }




    @Test
    public void showUpdateForm_ShouldReturn200AndBidListUpdateView() throws Exception {

        // Given : an existing BidList with a given ID
        Integer id = 1;

        BidList bidList = new BidList();
        bidList.setId(id);
        bidList.setAccount("Account1");
        bidList.setType("Type1");
        bidList.setBidQuantity(100.0);

        BDDMockito.given(service.getById(id)).willReturn(bidList);

        // When : We perform a GET request on /bidList/update/{id}
        // Then : The “bidList/update” view is displayed with the expected object in the model.
        mockMvc.perform(get("/bidList/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", hasProperty("id", is(id))));  // Vérifie l'ID du BidList
    }




    @Test
    public void showUpdateForm_WithUnknownId_ShouldReturn302AndRedirectToErrorPage() throws Exception {

        // Given : No BidList found, exception triggered
        Integer unknownId = 999;
        BDDMockito.given(service.getById(unknownId)).willThrow(new IllegalArgumentException("BidList not found"));

        // When / Then : The user is redirected to /error.
        mockMvc.perform(get("/bidList/update/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }




    @Test
    public void updateBid_ShouldReturn302AndShowBidListListView() throws Exception {

        // Given : A valid BidList object for the update
        Integer id = 1;
        BidList bidList = new BidList();
        bidList.setId(id);
        bidList.setAccount("UpdatedAccount");
        bidList.setType("UpdatedType");
        bidList.setBidQuantity(150.0);

        // When : We perform a POST request on /bidList/update/{id} with valid data.
        // Then : A redirect to /bidList/list is expected and the service is called.
        mockMvc.perform(post("/bidList/update/{id}", id)
                        .param("account", bidList.getAccount())
                        .param("type", bidList.getType())
                        .param("bidQuantity", String.valueOf(bidList.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service).update(any(BidList.class));
    }




    @Test
    public void updateBid_WithValidationErrors_ShouldReturn200AndBidListUpdateView() throws Exception {

        // Given : A required field (account) is empty.
        Integer id = 1;

        // When : An invalid POST request is made to /bidList/update/{id}.
        // Then : The “bidList/update” view is displayed and the validation error is detected.
        mockMvc.perform(post("/bidList/update/{id}", id)
                        .param("account", "")
                        .param("type", "Type1")
                        .param("bidQuantity", "100.0"))
                .andExpect(status().isOk()) // Pas de redirection car erreur de validation
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"));
    }




    @Test
    public void deleteBid_ShouldReturn302AndShowBidListListView() throws Exception {

        // Given : an existing ID
        Integer id = 1;

        // When : We perform a GET request on /bidList/delete/{id}
        // Then : A redirect to /bidList/list is expected, and deletion is invoked.
        mockMvc.perform(get("/bidList/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service).delete(id);
    }




    @Test
    public void deleteBid_WithUnknownId_ShouldReturn302AndRedirectToErrorPage() throws Exception {

        // Given : Deletion fails because ID is unknown
        Integer unknownId = 999;
        BDDMockito.willThrow(new IllegalArgumentException("BidList not found")).given(service).delete(unknownId);

        // When / Then : redirect to /error
        mockMvc.perform(get("/bidList/delete/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }

}
