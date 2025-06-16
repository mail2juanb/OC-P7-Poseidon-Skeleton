package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDomainTest {

    @Test
    void update_shouldUpdateFullnamePasswordAndRole() {

        // Given: two User instances, one original and one with updated values
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

        // When: the original user is updated with the new user's values
        original.update(updated);

        // Then: the original user's password, fullname, and role should be updated but the username should remain unchanged
        assertEquals("John Updated", original.getFullname());
        assertEquals("NewPass!2024", original.getPassword());
        assertEquals("ADMIN", original.getRole());
        assertEquals("johndoe", original.getUsername());
    }

}
