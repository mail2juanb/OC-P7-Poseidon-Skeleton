package com.nnk.springboot.service;

import com.nnk.springboot.domain.DomainModel;
import com.nnk.springboot.exception.NotFoundIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public abstract class AbstractCrudServiceTest <MODEL extends DomainModel<MODEL>> {


    protected JpaRepository<MODEL, Integer> repository;
    protected AbstractCrudService<MODEL> service;

    protected abstract AbstractCrudService<MODEL> initService();
    protected abstract MODEL createModelWithId(int id);
    protected abstract MODEL createModelWithNullId();
    protected abstract MODEL createUpdatedModel();



    @BeforeEach
    void setup() {
        this.service = initService();
    }



    @Test
    void getById_shouldReturnModel_whenFound() {

        MODEL model = createModelWithId(1);
        when(repository.findById(1)).thenReturn(Optional.of(model));

        MODEL result = service.getById(1);

        assertEquals(model, result);

    }



    @Test
    void getById_shouldThrowException_whenNotFound() {

        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundIdException.class, () -> service.getById(999));

    }



    @Test
    void create_shouldSave_whenIdIsNull() {

        MODEL model = createModelWithNullId();

        service.create(model);

        verify(repository).save(model);

    }



    @Test
    void create_shouldThrowException_whenIdNotNull() {

        MODEL model = createModelWithId(42);

        assertThrows(NotFoundIdException.class, () -> service.create(model));

        verify(repository, never()).save(any());

    }



    @Test
    void delete_shouldDelete_whenExists() {

        when(repository.existsById(1)).thenReturn(true);

        service.delete(1);

        verify(repository).deleteById(1);

    }



    @Test
    void delete_shouldThrow_whenNotExists() {

        when(repository.existsById(99)).thenReturn(false);

        assertThrows(NotFoundIdException.class, () -> service.delete(99));

    }



    @Test
    void update_shouldSaveUpdatedModel_whenIdExists() {

        // Given
        MODEL original = createModelWithId(1);
        MODEL updateData = createModelWithId(1);
        MODEL updatedModel = createUpdatedModel();

        // Simule la logique métier de update() qui retourne un modèle mis à jour
        //MODEL updatedModel = mockModel(); // méthode utilitaire

//        @SuppressWarnings("unchecked")
//        MODEL updatedModel = (MODEL) mock(DomainModel.class);

        when(repository.findById(1)).thenReturn(Optional.of(original));
        when(original.update(updateData)).thenReturn(updatedModel);

        // When
        service.update(updateData);

        // Then
        verify(repository).save(updatedModel);

    }



    @Test
    void update_shouldThrowException_whenIdNotExist() {

        // Given
        MODEL model = createModelWithId(404);
        when(repository.findById(404)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(NotFoundIdException.class, () -> service.update(model));

        verify(repository, never()).save(any());

    }


}
