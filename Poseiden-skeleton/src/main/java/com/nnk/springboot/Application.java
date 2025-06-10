package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * <h2>Main class of the Poseidon application.</h2>
 *
 * <p>It serves as the entry point for the Spring Boot application.</p>
 *
 * <h3>Overview</h3>
 * <p>This application uses Spring Boot, Spring Security, Thymeleaf, and a MySQL database (H2 for testing).</p>
 * <p>Key features:</p>
 * <ul>
 *   <li>Full CRUD on main entities</li>
 *   <li>Authentication system with Spring Security</li>
 *   <li>Thymeleaf web interface</li>
 * </ul>
 *
 * @author Michaud Jean-Baptiste
 * @version 1.0
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
