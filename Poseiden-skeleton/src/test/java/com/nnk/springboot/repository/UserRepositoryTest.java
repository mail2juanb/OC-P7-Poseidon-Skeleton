package com.nnk.springboot.repository;


import com.nnk.springboot.domain.User;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@DataJpaTest
// We dont want the H2 in-memory database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User user = new User();


    @BeforeEach
    void setUp() {
        user.setUsername("johndoe");
        user.setPassword("Password12345!");
        user.setFullname("John Doe");
        user.setRole("USER");
        userRepository.save(user);
    }



    @Test
    void save_shouldSaveUser() {
        User saved = userRepository.save(user);
        assertNotNull(saved.getId());
        assertTrue(userRepository.findById(saved.getId()).isPresent());
    }



    @Test
    void update_shouldUpdateUser() {
        user.setFullname("John D. Updated");
        user.setRole("ADMIN");

        User updated = userRepository.save(user);

        assertEquals("John D. Updated", updated.getFullname());
        assertEquals("ADMIN", updated.getRole());
    }



    @Test
    void findAll_shouldReturnUserList() {
        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.contains(user));
    }



    @Test
    void findById_shouldReturnUser_withValidId() {
        Optional<User> result = userRepository.findById(user.getId());
        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
    }



    @Test
    void findById_shouldReturnEmpty_withoutValidId() {
        Optional<User> result = userRepository.findById(user.getId() + 100);
        assertTrue(result.isEmpty());
    }



    @Test
    void delete_shouldDeleteUser() {
        userRepository.deleteById(user.getId());
        Optional<User> deleted = userRepository.findById(user.getId());
        assertTrue(deleted.isEmpty());
    }



    @Test
    void save_shouldFail_withInvalidPassword() {
        User invalidUser = new User();
        invalidUser.setUsername("janedoe");
        invalidUser.setPassword("abc"); // Mot de passe invalide
        invalidUser.setFullname("Jane Doe");
        invalidUser.setRole("USER");

        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(invalidUser);
            userRepository.flush(); // Force Hibernate à valider les contraintes immédiatement
        });
    }



}
