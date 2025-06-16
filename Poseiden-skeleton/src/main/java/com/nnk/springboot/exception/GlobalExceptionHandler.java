package com.nnk.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



/**
 * Global exception handler in the Poseidon application.
 * This class intercepts exceptions thrown in controllers
 * and redirects to an error page while displaying a flash message for the user.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Handles all generic exceptions that are not specifically handled.
     *
     * @param exception the captured exception
     * @param redirectAttributes the attributes used to pass a flash message to the view
     * @return a redirect to the “/error” page
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "Une erreur est survenue : " + exception.getMessage());
        return "redirect:/error";
    }


    /**
     * Handles the specific exception {@link IdLimitReachedException},
     * which is thrown when a maximum limit of identifiers (e.g., TINYINT) is reached.
     *
     * @param exception the specific exception for reaching the ID limit
     * @param redirectAttributes the attributes used to pass a flash message to the view
     * @return a redirect to the “/error” page
     */
    @ExceptionHandler(IdLimitReachedException.class)
    public String handleIdLimitReachedException(IdLimitReachedException exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        return "redirect:/error";
    }


}
