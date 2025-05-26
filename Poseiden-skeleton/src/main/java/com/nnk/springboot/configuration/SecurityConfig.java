package com.nnk.springboot.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Original -- private static final String[] PERMIT_ALL = {"/css/**", "/login"};
    //private static final String[] PERMIT_ALL = {"/css/**", "/**"};                                        // NOTE: Work accept all
    private static final String[] PERMIT_ALL = {"/css/**", "/", "/home", "/403", "/user/add", "/user/validate"};                               // NOTE: Work accept only


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers(PERMIT_ALL))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PERMIT_ALL).permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/bidList/list")
                        .failureUrl("/home?error")  // <- redirection en cas d’échec
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")                       // <- personnalisée ici
                        .logoutSuccessUrl("/home?logout")          // <- redirection après logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403") // Redirection vers page 403 personnalisée
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                )
                .build();
    }


}
