package com.nnk.springboot.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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

    //private static final String[] PERMIT_ALL = {"/css/**", "/login"};
    private static final String[] PERMIT_ALL = {"/css/*", "/"};


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/css/**", "/login"))
                .csrf(csrf -> csrf.ignoringRequestMatchers(PERMIT_ALL))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PERMIT_ALL).permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                        .defaultSuccessUrl("/bidList/list")
                        .permitAll()
                )

//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout=true")
//                        .deleteCookies("JSESSIONID")
//                        .invalidateHttpSession(true)
//                        .permitAll()
//                )
//                .exceptionHandling(Customizer.withDefaults()
//                )
//                .sessionManagement(session -> session
//                        .maximumSessions(1)
//                )
                .build();
    }


}
