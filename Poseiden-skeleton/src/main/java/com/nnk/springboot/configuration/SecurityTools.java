package com.nnk.springboot.configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Security utilities for accessing the logged-in user's information.
 */
public class SecurityTools {

    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return a {@link CustomUserDetails} object representing the logged-in user
     * @throws BadCredentialsException if no valid user is logged in
     */
    public static CustomUserDetails getConnectedUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        throw new BadCredentialsException("User not connected");
    }

}
