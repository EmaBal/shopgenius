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
	String appName; // = "SingersApp";
	
    @Autowired
    private UserService userService;
    
    private Utilities utilities = new Utilities();
	
    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        String errorMessage = null;
        if(error != null) {
        	errorMessage = "Email o password errati";
        }
        
//        if(logout != null) {
//        	// entriamo in questo caso se non specifichiamo una .logoutSuccessUrl in WebSecurityConf.configure
//        	errorMessage = "Uscita dal sistema avvenuta !!";
//        }
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("appName", appName);
        
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
    	model.addAttribute("username", utilities.getCurrentUserName());
    	
        return "tiles_login";
    }
  
//    @GetMapping(value="/logout")
//    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){    
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }
}

