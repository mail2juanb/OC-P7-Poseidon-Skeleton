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

        // Given: A model with ID 1 exists in the repository
        MODEL model = createModelWithId(1);
        when(repository.findById(1)).thenReturn(Optional.of(model));

        // When: Retrieving the model by ID 1
        MODEL result = service.getById(1);

        // Then: The returned model should be the expected one
        assertEquals(model, result);

    }



    @Test
    void getById_shouldThrowException_whenNotFound() {

        // Given: No model exists with ID 999
        when(repository.findById(999)).thenReturn(Optional.empty());

        // When + Then: Retrieving ID 999 should throw NotFoundIdException
        assertThrows(NotFoundIdException.class, () -> service.getById(999));

    }



    @Test
    void create_shouldSave_whenIdIsNull() {

        // Given: A model with a null ID (new entity)
        MODEL model = createModelWithNullId();

        // When: Creating the model
        service.create(model);

        // Then: The model should be saved in the repository
        verify(repository).save(model);

    }



    @Test
    void create_shouldThrowException_whenIdNotNull() {

        // Given: A model with a non-null ID (already existing entity)
        MODEL model = createModelWithId(42);

        // When + Then: Creating the model should throw NotFoundIdException and not save it
        assertThrows(NotFoundIdException.class, () -> service.create(model));
        verify(repository, never()).save(any());

    }



    @Test
    void delete_shouldDelete_whenExists() {

        // Given: A model with ID 1 exists
        when(repository.existsById(1)).thenReturn(true);

        // When: Deleting the model by ID 1
        service.delete(1);

        // Then: The repository should delete the model by ID
        verify(repository).deleteById(1);

    }



    @Test
    void delete_shouldThrow_whenNotExists() {

        // Given: No model exists with ID 99
        when(repository.existsById(99)).thenReturn(false);

        // When + Then: Deleting should throw NotFoundIdException
        assertThrows(NotFoundIdException.class, () -> service.delete(99));

    }



    @Test
    void update_shouldSaveUpdatedModel_whenIdExists() {

        // Given: A model with ID 1 exists and update data is provided
        MODEL original = createModelWithId(1);
        MODEL updateData = createModelWithId(1);
        MODEL updatedModel = createUpdatedModel();

        when(repository.findById(1)).thenReturn(Optional.of(original));
        when(original.update(updateData)).thenReturn(updatedModel);

        // When: Updating the model
        service.update(updateData);

        // Then: The repository should save the updated model
        verify(repository).save(updatedModel);

    }



    @Test
    void update_shouldThrowException_whenIdNotExist() {

        // Given: No model exists with ID 404
        MODEL model = createModelWithId(404);
        when(repository.findById(404)).thenReturn(Optional.empty());

        // When + Then: Updating should throw NotFoundIdException and nothing should be saved
        assertThrows(NotFoundIdException.class, () -> service.update(model));

        verify(repository, never()).save(any());

    }


}
