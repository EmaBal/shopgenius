package it.univpm.shopgenius.controller;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.ProductService;
import it.univpm.shopgenius.utils.Utilities;

@Controller
public class HomeController {
	
	@Autowired
	ProductService productService;
	
	private Utilities utilities = new Utilities();
	
	@GetMapping(value={"/","home"})
	public String showHome(@RequestParam(value = "error", required = false) String error, Model model) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String currentPrincipalName = authentication.getName();
//		Collection<SimpleGrantedAuthority> auth = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
//		for (SimpleGrantedAuthority r: auth) {
//			if (r.getAuthority().equals("admin")) {
//				model.addAttribute("role", r.getAuthority());
//				break;
//			} else if (r.getAuthority().equals("user")) {
//				model.addAttribute("role", r.getAuthority());
//				break;
//			}
//		}
		String currenUserRole = utilities.getCurrentUserMajorRole();
		if (currenUserRole.equals("admin"))
			model.addAttribute("role","admin");
		else if (currenUserRole.equals("user"))
			model.addAttribute("role","user");
		model.addAttribute("error", error);
		model.addAttribute("username", utilities.getCurrentUserName());
		return "home";
	}
	
	@GetMapping("/myapp")
	public String tilesTest() {
		return "myapp";
	}
}