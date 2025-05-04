package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController
{
	@RequestMapping("/")
	public String toHome(Model model)
	{
		log.debug("GET / TO HOME PAGE VIEW");
		return "home";
	}

	@RequestMapping("/home")
	public String home(Model model)
	{
		log.debug("GET HOME PAGE VIEW");
		return "home";
	}

	// FIXME : A priori c'est inutile
	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
