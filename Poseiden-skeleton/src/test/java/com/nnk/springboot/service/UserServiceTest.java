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

        // Given: A list of users exists in the repository
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        // When: Retrieving all users
        List<User> result = userService.getAll();

        // Then: The result should contain the expected user
        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        verify(userRepository).findAll();

    }


    @Test
    public void createUser_ShouldCreateUser() {

        // Given: A new user with no ID to be saved
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When: Creating the user
        userService.create(user);

        // Then: The repository should save the user
        verify(userRepository).save(user);

    }



    @Test
    public void createUser_ShouldThrowException_WhenIdIsNotNull() {

        // Given: A user with a non-null ID
        user.setId(1);

        // When + Then: Creating the user should throw NotFoundIdException
        NotFoundIdException exception = assertThrows(NotFoundIdException.class, () -> userService.create(user));
        assertTrue(exception.getMessage().contains("id set to 1"));

    }



    @Test
    public void createUser_ShouldThrowException_WhenIdLimitIsReached() {
        // Given: The repository contains the maximum allowed number of users
        when(userRepository.count()).thenReturn(127L); // limite atteinte

        // When + Then: Creating the user should throw IdLimitReachedException
        IdLimitReachedException exception = assertThrows(IdLimitReachedException.class, () -> userService.create(user));
        assertTrue(exception.getMessage().contains("Maximum number of entries"));

    }



    @Test
    public void updateUser() {

        // Given: An existing user with ID 1
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("OldPassword1!");
        existingUser.setFullname("Old User");
        existingUser.setRole("USER");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // And: New data to update the user
        existingUser.setUsername("newUsername");
        existingUser.setPassword("NewPassword1!");
        existingUser.setFullname("Updated User");
        existingUser.setRole("ADMIN");

        // When: Updating the user
        userService.update(existingUser);

        // Then: The repository should save the updated user
        verify(userRepository).save(existingUser);
        assert existingUser.getUsername().equals("newUsername");
        assert existingUser.getPassword().equals("NewPassword1!");
        assert existingUser.getFullname().equals("Updated User");

    }



    @Test
    public void updateUser_ShouldThrowException_WhenIdNotFound() {

        // Given: A user with ID 99 that does not exist
        user.setId(99);
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // When + Then: Updating should throw NotFoundIdException
        assertThrows(NotFoundIdException.class, () -> userService.update(user));

    }



    @Test
    public void deleteUser_ShouldDeleteUser() {

        // Given: A user with ID 1 exists in the repository
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("usernameToDelete");
        existingUser.setPassword("Password1!");
        existingUser.setFullname("User To Delete");
        existingUser.setRole("USER");

        when(userRepository.existsById(1)).thenReturn(true);

        // When: Deleting the user by ID
        userService.delete(1);

        // Then: The repository should delete the user
        verify(userRepository).deleteById(1);

    }



    @Test
    public void deleteUser_ShouldThrowException_WhenIdNotFound() {

        // Given: No user exists with ID 99
        when(userRepository.existsById(99)).thenReturn(false);

        // When + Then: Deleting should throw NotFoundIdException
        assertThrows(NotFoundIdException.class, () -> userService.delete(99));

    }



    @Test
    public void findById_ReturnsUser_WhenUserExists() {

        // Given: A user with ID 1 exists
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("username");
        existingUser.setPassword("Password1!");
        existingUser.setFullname("User FullName");
        existingUser.setRole("USER");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // When: Retrieving the user by ID
        User user = userService.getById(1);

        // Then: The returned user should match the existing user
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("User FullName", user.getFullname());

    }



    @Test
    public void findById_ThrowsNotFoundIdException_WhenUserDoesNotExist() {

        // Given: No user exists with ID 1
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // When + Then: Retrieving the user should throw NotFoundIdException
        assertThrows(NotFoundIdException.class, () -> {
            userService.getById(1);
        });

    }

}
