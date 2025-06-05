package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDomainTest {

    @Test
    void update_shouldUpdateFullnamePasswordAndRole() {
        User original = new User();
        original.setUsername("johndoe");
        original.setPassword("Password12345!");
        original.setFullname("John Doe");
        original.setRole("USER");

        User updated = new User();
        updated.setUsername("johndoe");
        updated.setPassword("NewPass!2024");
        updated.setFullname("John Updated");
        updated.setRole("ADMIN");

        // Act
        original.update(updated);

        // Assert
        assertEquals("John Updated", original.getFullname());
        assertEquals("NewPass!2024", original.getPassword());
        assertEquals("ADMIN", original.getRole());
        assertEquals("johndoe", original.getUsername());
    }

}
