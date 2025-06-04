package com.nnk.springboot.configuration;


import com.nnk.springboot.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * Custom implementation of {@link UserDetails} used by Spring Security
 * to represent an authenticated user with their roles and credentials.
 */
@Data
public class CustomUserDetails implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String role;


    /**
     * Builds a {@link CustomUserDetails} object from an application user.
     *
     * @param user the application user
     */
    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }


    /**
     * Returns the user's authorities (roles).
     *
     * @return a collection containing the user's authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }


    /**
     * Indicates whether the user's account has expired.
     *
     * @return true if the account has not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }


    /**
     * Indicates whether the user's account is locked.
     *
     * @return true if the account is unlocked
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }


    /**
     * Indicates whether the user's credentials have expired.
     *
     * @return true if the credentials are valid
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }


    /**
     * Indicates whether the user is activated.
     *
     * @return true if the account is activated
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


}
