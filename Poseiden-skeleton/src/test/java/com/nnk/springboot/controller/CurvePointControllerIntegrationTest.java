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
public class CurvePointControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudService<CurvePoint> service;




    @Test
    public void home_ShouldReturn200AndCurvePointListView() throws Exception {

        // Given : a simulated CurvePoints list returned by the service
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

        // When: a GET request is made to /curvePoint/list
        // Then: the “curvePoint/list” view is returned with a list containing 2 items
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePoints", hasSize(2))); // Vérifie la taille de la liste
    }




    @Test
    public void addCurvePointForm_ShouldReturn200AndAddView() throws Exception {

        // Given: the user wants to add a new CurvePoint
        // When: a GET request is made to /curvePoint/add
        // Then: the “curvePoint/add” view is returned with an empty form
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }




    @Test
    public void validate_ShouldReturn302AndRedirectToList() throws Exception {
        // Given: a form with valid data for a CurvePoint
        // When: a POST request is made to /curvePoint/validate
        // Then: the request redirects to the CurvePoints list page
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).create(any(CurvePoint.class));
    }




    @Test
    public void validate_WithValidationError_ShouldReturn200AndAddView() throws Exception {

        // Given: a form with an empty “curveId” field
        // When: a POST request is made to /curvePoint/validate with invalid data
        // Then: the “curvePoint/add” view is returned with an error on the “curveId” field
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));
    }




    @Test
    public void showUpdateForm_ShouldReturn200AndUpdateView() throws Exception {

        // Given : an existing CurvePoint with a valid ID
        Integer id = 1;
        CurvePoint cp = new CurvePoint();
        cp.setId(id);
        cp.setCurveId(1);
        cp.setTerm(10.0);
        cp.setValue(20.0);

        BDDMockito.given(service.getById(id)).willReturn(cp);

        // When: a GET request is made to /curvePoint/update/{id} with a valid ID
        // Then: the “curvePoint/update” view is returned with the CurvePoint data to be updated
        mockMvc.perform(get("/curvePoint/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", hasProperty("id", is(id))));
    }




    @Test
    public void showUpdateForm_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : a CurvePoint ID that does not exist
        Integer id = 999;
        BDDMockito.given(service.getById(id)).willThrow(new IllegalArgumentException("Not found"));

        // When: a GET request is made to /curvePoint/update/{id} with an unknown ID
        // Then: the request redirects to the error page
        mockMvc.perform(get("/curvePoint/update/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }




    @Test
    public void update_ShouldReturn302AndRedirectToList() throws Exception {

        // Given: a valid form to update a CurvePoint with an ID
        Integer id = 1;

        // When: a POST request is made to /curvePoint/update/{id} with valid data
        // Then: the request redirects to the CurvePoints list page
        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .param("curveId", "1")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).update(any(CurvePoint.class));
    }




    @Test
    public void update_WithValidationError_ShouldReturn200AndUpdateView() throws Exception {

        // Given: an invalid form for updating a CurvePoint with an empty “curveId” field
        Integer id = 1;

        // When: a POST request is made to /curvePoint/update/{id} with invalid data
        // Then: the “curvePoint/update” view is returned with an error on the “curveId” field
        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .param("curveId", "")
                        .param("term", "2.0")
                        .param("value", "3.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));
    }




    @Test
    public void delete_ShouldReturn302AndRedirectToList() throws Exception {

        // Given: a valid ID to delete a CurvePoint
        Integer id = 1;

        // When: a GET request is made to /curvePoint/delete/{id} to delete the CurvePoint.
        // Then: the request redirects to the CurvePoints list page.
        mockMvc.perform(get("/curvePoint/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service).delete(id);
    }




    @Test
    public void delete_WithUnknownId_ShouldRedirectToError() throws Exception {

        // Given : a non-existent CurvePoint ID
        Integer unknownId = 999;
        BDDMockito.willThrow(new IllegalArgumentException("Not found")).given(service).delete(unknownId);

        // When: a GET request is made to /curvePoint/delete/{id} to delete a non-existent CurvePoint
        // Then: the request redirects to the error page
        mockMvc.perform(get("/curvePoint/delete/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }

}
