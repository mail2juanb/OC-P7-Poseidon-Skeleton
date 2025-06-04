package com.nnk.springboot.configuration;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Custom implementation of {@link UserDetailsService} used by Spring Security.
 * It allows user information to be loaded from the database
 * during authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    /**
     * Loads a user's details from their username.
     * Used by Spring Security during authentication.
     *
     * @param username the username of the user to be authenticated
     * @return a {@link UserDetails} object containing the user's security information
     * @throws UsernameNotFoundException if no user with that name is found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found : " + username));

        return new CustomUserDetails(user);

    }


}
