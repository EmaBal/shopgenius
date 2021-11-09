package it.univpm.shopgenius.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.services.UserService;
import it.univpm.shopgenius.utils.Utilities;
@Controller
public class SecurityController 
{
	@Autowired
	String appName;
	
    @Autowired
    private UserService userService;
    
    private Utilities utilities = new Utilities();
	
    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        String errorMessage = null;
        if(error != null) {
        	errorMessage = "Wrong email or password";
        }
        
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("appName", appName);
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
			} catch (Exception e) {
		    	model.addAttribute("current_firstName", "anonymous");
		    	model.addAttribute("current_lastName", "anonymous");
			}
    	
        return "tiles_login";
    }
}

