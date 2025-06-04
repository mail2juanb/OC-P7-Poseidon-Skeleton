package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.IdLimitReachedException;
import com.nnk.springboot.exception.NotFoundIdException;
import com.nnk.springboot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("Password1234!");
        user.setFullname("Test User");
        user.setRole("USER");
    }



    @Test
    public void getAllUsers_ShouldReturnList() {
        // Arrange
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        verify(userRepository).findAll();
    }


    @Test
    public void createUser() {
        // Arrange : on prépare le mock pour save()
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act : appel de la méthode create() du service
        userService.create(user);

        // Assert : on vérifie que save() a bien été appelé
        verify(userRepository).save(user);
    }



    @Test
    public void createUser_ShouldThrowException_WhenIdIsNotNull() {
        // Arrange
        user.setId(1);

        // Act & Assert
        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> userService.create(user));
        assertTrue(exception.getMessage().contains("id set to 1"));
    }



    @Test
    public void createUser_ShouldThrowException_WhenIdLimitIsReached() {
        // Arrange
        when(userRepository.count()).thenReturn(127L); // limite atteinte

        // Act & Assert
        IdLimitReachedException exception = assertThrows(IdLimitReachedException.class, () -> userService.create(user));
        assertTrue(exception.getMessage().contains("Maximum number of entries"));
    }



    @Test
    public void updateUser() {
        // Arrange : on crée un utilisateur avec un id
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("OldPassword1!");
        existingUser.setFullname("Old User");
        existingUser.setRole("USER");

        // Mock le findById pour retourner l'utilisateur existant
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        // Mock le save pour retourner l'utilisateur mis à jour
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Mise à jour des informations de l'utilisateur
        existingUser.setUsername("newUsername");
        existingUser.setPassword("NewPassword1!");
        existingUser.setFullname("Updated User");
        existingUser.setRole("ADMIN");

        // Act : appelle la méthode update()
        userService.update(existingUser);

        // Assert : Vérifie que save() a bien été appelée avec les bons paramètres
        verify(userRepository).save(existingUser);
        // Vérifie que l'utilisateur a bien été mis à jour dans l'objet (ce n'est pas nécessaire mais peut être ajouté)
        assert existingUser.getUsername().equals("newUsername");
        assert existingUser.getPassword().equals("NewPassword1!");
        assert existingUser.getFullname().equals("Updated User");
    }



    @Test
    public void updateUser_ShouldThrowException_WhenIdNotFound() {
        // Arrange
        user.setId(99);
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundIdException.class, () -> userService.update(user));
    }



    @Test
    public void deleteUser() {
        // Arrange : Créer un utilisateur existant
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("usernameToDelete");
        existingUser.setPassword("Password1!");
        existingUser.setFullname("User To Delete");
        existingUser.setRole("USER");

        // Mock de existsById pour retourner true, indiquant que l'utilisateur existe
        when(userRepository.existsById(1)).thenReturn(true);

        // Act : Appeler la méthode delete()
        userService.delete(1);  // L'id est 1, ce qui correspond à l'utilisateur à supprimer

        // Assert : Vérifie que deleteById() a bien été appelée avec l'id de l'utilisateur
        verify(userRepository).deleteById(1);  // Vérifie que le delete a bien été effectué
    }



    @Test
    public void deleteUser_ShouldThrowException_WhenIdNotFound() {
        // Arrange
        when(userRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundIdException.class, () -> userService.delete(99));
    }



    @Test
    public void findById_ReturnsUser_WhenUserExists() {
        // Arrange : Créer un utilisateur existant
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("username");
        existingUser.setPassword("Password1!");
        existingUser.setFullname("User FullName");
        existingUser.setRole("USER");

        // Mock de findById pour retourner l'utilisateur existant
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // Act : Appeler la méthode getById()
        User user = userService.getById(1);

        // Assert : Vérifie que l'utilisateur retourné est le même que celui simulé
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("User FullName", user.getFullname());
    }



    @Test
    public void findById_ThrowsNotFoundIdException_WhenUserDoesNotExist() {
        // Arrange : Mock de findById pour retourner un Optional vide
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act et Assert : Vérifie que l'exception NotFoundIdException est lancée
        assertThrows(NotFoundIdException.class, () -> {
            userService.getById(1);  // Essaye de récupérer un utilisateur inexistant
        });
    }

}
