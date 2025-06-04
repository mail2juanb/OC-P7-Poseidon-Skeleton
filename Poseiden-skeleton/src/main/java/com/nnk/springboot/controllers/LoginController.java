package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
//@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

//    @GetMapping("login")
//    public ModelAndView login() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("login");
//        log.debug("COUCOUC ON EST LAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
//        return mav;
//    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        log.debug("GET PAGE LOGIN VIEW");
        return "login";
    }

    // NOTE : PostMapping is managed by Spring Security in SecurityConfig

    // FIXME : A implémenter correctement. Je ne sais pas encore où. JE NE SAIS PAS A QUOI CA SERT. DELETE ?
//    @GetMapping("secure/article-details")
//    public ModelAndView getAllUserArticles() {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("users", userRepository.findAll());
//        mav.setViewName("user/list");
//        return mav;
//    }

    // FIXME : Utilisé pour gérer le cas d'un utilisateur qui voudrait aller sur une page dont il n'a pas accès. DELETE ?
//    @GetMapping("error")
//    public ModelAndView error() {
//        ModelAndView mav = new ModelAndView();
//        String errorMessage= "You are not authorized for the requested data.";
//        mav.addObject("errorMsg", errorMessage);
//        mav.setViewName("403");
//        return mav;
//    }
}
