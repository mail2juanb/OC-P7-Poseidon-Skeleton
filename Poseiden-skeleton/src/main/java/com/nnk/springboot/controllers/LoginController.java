package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
//@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;



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


}
