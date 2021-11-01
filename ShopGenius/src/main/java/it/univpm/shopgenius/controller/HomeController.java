package it.univpm.shopgenius.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.services.ProductService;
import it.univpm.shopgenius.services.UserService;
import it.univpm.shopgenius.utils.Utilities;

@Controller
public class HomeController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	private Utilities utilities = new Utilities();
	
	@GetMapping(value={"/","home"})
	public String showHome(@RequestParam(value = "error", required = false) String error, Model model) {
		String currenUserRole = utilities.getCurrentUserMajorRole();
		model.addAttribute("role",currenUserRole);
		model.addAttribute("error", error);
		try {
    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
		} catch (Exception e) {
	    	model.addAttribute("current_firstName", "anonymous");
	    	model.addAttribute("current_lastName", "anonymous");
		}
		
		return "tiles_home";
	}
}