package it.univpm.shopgenius.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listCustomers(Model model) {
        List <User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "list_users";
    }

    @GetMapping("/showForm")
    public String showFormForAdd(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<SimpleGrantedAuthority> auth = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
		for (SimpleGrantedAuthority r: auth) {
			if (r.getAuthority().equals("admin")) {
				model.addAttribute("role", r.getAuthority());
				break;
			} else if (r.getAuthority().equals("user")) {
				model.addAttribute("role", r.getAuthority());
				break;
			}
		}
		User user = new User();
		model.addAttribute("user", user);
        return "user_form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult br, @RequestParam(value = "makeAdmin", required = false) boolean makeAdmin, Model model) {
    	if (br.hasErrors()) {
    		System.out.println("TEST");
    		return "user_form";
    	} else {
	    	userService.saveUser(user);
	    	if (makeAdmin) {
	    		userService.addRole(user, "admin");
	    		userService.update(user);
	    	}
	        return "redirect:/";
    	}
    }

    @GetMapping("/updateForm")
    public String showFormForUpdate(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user_form";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") int id) {
    	userService.deleteUser(id);
        return "redirect:/user/list";
    }
    
    
}
