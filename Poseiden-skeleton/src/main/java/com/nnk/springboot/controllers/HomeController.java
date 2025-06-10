package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller responsible for handling requests to the home page.
 * <p>
 * Provides routing to the root URL ("/") and to "/home",
 * directing users to the main home view.
 * </p>
 */
@Slf4j
@Controller
public class HomeController {


	/**
	 * Handles HTTP GET requests to the root path ("/").
	 * <p>
	 * This method logs access and returns the "home" view.
	 * </p>
	 *
	 * @param model the model used to pass attributes to the view (currently unused)
	 * @return the name of the Thymeleaf (or JSP) view to render, i.e., "home"
	 */
	@RequestMapping("/")
	public String toHome(Model model)
	{
		log.debug("GET / TO HOME PAGE VIEW");
		return "home";
	}


	/**
	 * Handles HTTP GET requests to "/home".
	 * <p>
	 * This method logs access and returns the "home" view.
	 * </p>
	 *
	 * @param model the model used to pass attributes to the view (currently unused)
	 * @return the name of the view to render, i.e., "home"
	 */
	@RequestMapping("/home")
	public String home(Model model)
	{
		log.debug("GET HOME PAGE VIEW");
		return "home";
	}


}
