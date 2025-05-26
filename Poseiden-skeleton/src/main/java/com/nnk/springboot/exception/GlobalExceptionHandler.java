package com.nnk.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, RedirectAttributes redirectAttributes) {
        log.error("Une erreur est survenue : {}", exception.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", "Une erreur est survenue : " + exception.getMessage());
        return "redirect:/error";
    }

    @ExceptionHandler(IdLimitReachedException.class)
    public String handleIdLimitReachedException(IdLimitReachedException exception, RedirectAttributes redirectAttributes) {
        log.warn("Limite atteinte : {}", exception.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        return "redirect:/error";
    }


}
