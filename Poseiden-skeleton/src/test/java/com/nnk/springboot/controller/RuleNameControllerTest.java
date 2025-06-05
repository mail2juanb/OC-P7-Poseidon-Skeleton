package com.nnk.springboot.controller;


import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
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
@WebMvcTest(RuleNameController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbstractCrudService<RuleName> service;  // Mock du service


    // 1. Test de la méthode home : récupérer et afficher la liste des RuleNames
    @Test
    public void home_ShouldReturn200AndRuleNameListView() throws Exception {

        // Given : une liste de RuleNames simulée renvoyée par le service
        RuleName rule1 = new RuleName();
        rule1.setId(1);
        rule1.setName("Rule 1");
        rule1.setDescription("Description 1");
        rule1.setJson("Json String 1");
        rule1.setTemplate("Template 1");
        rule1.setSqlStr("SQL 1");
        rule1.setSqlPart("SQL Part 1");

        RuleName rule2 = new RuleName();
        rule2.setId(2);
        rule2.setName("Rule 2");
        rule2.setDescription("Description 2");
        rule2.setJson("Json String 2");
        rule2.setTemplate("Template 2");
        rule2.setSqlStr("SQL 2");
        rule2.setSqlPart("SQL Part 2");

        List<RuleName> ruleNames = Arrays.asList(rule1, rule2);

        BDDMockito.given(service.getAll()).willReturn(ruleNames);

        // When : on effectue une requête GET sur /ruleName/list
        // Then : la vue "ruleName/list" est retournée avec une liste contenant 2 éléments
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNames", hasSize(2)));  // Vérifie la taille de la liste
    }


    // 2. Test de la méthode addRuleForm : afficher le formulaire d'ajout
    @Test
    public void addRuleForm_ShouldReturn200AndRuleNameAddView() throws Exception {

        // When : on effectue une requête GET sur /ruleName/add
        // Then : la vue "ruleName/add" est retournée sans erreur
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    // 3.1 Test de la méthode validate : valider le formulaire d'ajout et rediriger vers la liste
    @Test
    public void validateRuleName_ShouldReturn302AndRuleNameListView() throws Exception {

        // Given : un objet RuleName valide avec tous les champs requis
        RuleName rule = new RuleName();
        rule.setId(null);
        rule.setName("Rule 1");
        rule.setDescription("Description 1");
        rule.setJson("Json String 1");
        rule.setTemplate("Template 1");
        rule.setSqlStr("SQL 1");
        rule.setSqlPart("SQL Part 1");

        // When : on effectue une requête POST valide vers /ruleName/validate
        // Then : une redirection vers /ruleName/list est attendue et le service est appelé
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", rule.getName())
                        .param("description", rule.getDescription())
                        .param("json", rule.getJson())
                        .param("template", rule.getTemplate())
                        .param("sqlStr", rule.getSqlStr())
                        .param("sqlPart", rule.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).create(any(RuleName.class));  // Vérifie que la méthode create a été appelée
    }


    // 3.2 Test de la méthode validate : soumettre un formulaire invalide et revenir au formulaire d'ajout
    @Test
    public void validateRuleName_WithValidationError_ShouldReturn200AndRuleNameAddView() throws Exception {

        // Given : un champ obligatoire (name) est trop grand
        String longName = "A".repeat(126); // 126 caractères pour dépasser la limite de 125

        // When : on soumet le formulaire avec une erreur de validation
        // Then : la vue "ruleName/add" est affichée et l’erreur est attachée au modèle
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", longName)  // Champ trop long pour déclencher une erreur
                        .param("description", "Description 1")
                        .param("json", "{}")
                        .param("template", "Template 1")
                        .param("sqlStr", "SQL 1")
                        .param("sqlPart", "SQL Part 1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }


    // 4.1 Test de la méthode showUpdateForm : afficher le formulaire de mise à jour
    @Test
    public void showUpdateForm_ShouldReturn200AndRuleNameUpdateView() throws Exception {

        // Given : un RuleName existant avec un ID donné
        Integer id = 1;

        RuleName rule = new RuleName();
        rule.setId(id);
        rule.setName("Rule 1");
        rule.setDescription("Description 1");
        rule.setJson("Json String 1");
        rule.setTemplate("Template 1");
        rule.setSqlStr("SQL 1");
        rule.setSqlPart("SQL Part 1");

        BDDMockito.given(service.getById(id)).willReturn(rule);

        // When : on effectue une requête GET sur /ruleName/update/{id}
        // Then : la vue "ruleName/update" est affichée avec l’objet attendu dans le modèle
        mockMvc.perform(get("/ruleName/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", hasProperty("id", is(id))));  // Vérifie l'ID du RuleName
    }


    // 4.2 Test de la méthode showUpdateForm : l’ID fourni ne correspond à aucun RuleName existant
    @Test
    public void showUpdateForm_WithUnknownId_ShouldReturn302AndRedirectToErrorPage() throws Exception {

        // Given : aucun RuleName trouvé, déclenchement d'une exception
        Integer unknownId = 999;
        BDDMockito.given(service.getById(unknownId)).willThrow(new IllegalArgumentException("RuleName not found"));

        // When / Then : l'utilisateur est redirigé vers /error
        mockMvc.perform(get("/ruleName/update/{id}", unknownId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }


    // 5.1 Test de la méthode updateRuleName : valider le formulaire de mise à jour et rediriger vers la liste
    @Test
    public void updateRuleName_ShouldReturn302AndShowRuleNameListView() throws Exception {

        // Given : un objet RuleName valide pour la mise à jour
        Integer id = 1;
        RuleName rule = new RuleName();
        rule.setId(id);
        rule.setName("Updated Rule");
        rule.setDescription("Updated Description");
        rule.setJson("Json String Updated");
        rule.setTemplate("Updated Template");
        rule.setSqlStr("Updated SQL");
        rule.setSqlPart("Updated SQL Part");

        // When : on effectue une requête POST sur /ruleName/update/{id} avec des données valides
        // Then : une redirection vers /ruleName/list est attendue et le service est appelé
        mockMvc.perform(post("/ruleName/update/{id}", id)
                        .param("name", rule.getName())
                        .param("description", rule.getDescription())
                        .param("json", rule.getJson())
                        .param("template", rule.getTemplate())
                        .param("sqlStr", rule.getSqlStr())
                        .param("sqlPart", rule.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).update(any(RuleName.class));  // Vérifie que la méthode update a été appelée
    }


    // 5.2 Test de la méthode updateRuleName : soumettre un formulaire invalide de mise à jour
    @Test
    public void updateRuleName_WithValidationErrors_ShouldReturn200AndRuleNameUpdateView() throws Exception {

        // Given : un champ obligatoire (name) est trop grand
        Integer id = 1;
        String longName = "A".repeat(126); // 126 caractères pour dépasser la limite de 125

        // When : on effectue une requête POST invalide sur /ruleName/update/{id}
        // Then : la vue "ruleName/update" est affichée et l’erreur de validation est détectée
        mockMvc.perform(post("/ruleName/update/{id}", id)
                        .param("name", longName)
                        .param("description", "Updated Description")
                        .param("json", "{}")
                        .param("template", "Updated Template")
                        .param("sqlStr", "Updated SQL")
                        .param("sqlPart", "Updated SQL Part"))
                .andExpect(status().isOk()) // Pas de redirection car erreur de validation
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }


    // 6.1 Test de la méthode delete : supprimer un RuleName et rediriger vers la liste
    @Test
    public void deleteRuleName_ShouldReturn302AndShowRuleNameListView() throws Exception {

        // Given : un RuleName à supprimer
        Integer id = 1;
        RuleName rule = new RuleName();
        rule.setId(id);
        rule.setName("Rule 1");
        rule.setDescription("Description 1");
        rule.setJson("Json String 1");
        rule.setTemplate("Template 1");
        rule.setSqlStr("SQL 1");
        rule.setSqlPart("SQL Part 1");

        // When : on effectue une requête GET sur /ruleName/delete/{id}
        // Then : une redirection vers /ruleName/list est attendue et le service est appelé
        mockMvc.perform(get("/ruleName/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service).delete(id);  // Vérifie que la méthode delete a été appelée
    }

}


