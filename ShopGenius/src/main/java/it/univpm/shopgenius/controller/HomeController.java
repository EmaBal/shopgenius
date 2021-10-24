package it.univpm.shopgenius.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/")
	public String showHome(@RequestParam(value = "error", required = false) String error, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
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
		
        String errorMessage = null;
        if(error != null) {
        	errorMessage = "Prodotto non esistente";
        }
        
        model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("username", currentPrincipalName);
		
		return "home";
	}
	
//	@GetMapping("/")
//    public String homePage(@RequestParam(value = "error", required = false) String error,
//                            Model model) {
//        String errorMessage = null;
//        if(error != null) {
//        	errorMessage = "Prodotto non esistente";
//        }
//        
//        model.addAttribute("errorMessage", errorMessage);
//        return "home";
//    }
	
//	@GetMapping("/searchProduct")
//    public String searchProduct(@RequestParam("productName") String productName, Model model) {
//		try {
//			Product product = productService.getProductByName(productName);
//	        model.addAttribute("productName", productName);
//	        return "product";
//		} catch (Exception e) {
//			return "home";
//		}
	
	@GetMapping("/searchProduct")
    public String searchProduct(@RequestParam Map<String,String> requestParams, Model model) {
		String productName=requestParams.get("productName");
		String error = requestParams.get("error");
		try {
			Product product = productService.getProductByName(productName);
	        model.addAttribute("productName", productName);
	        return "product";
		} catch (Exception e) {
//			String errorMessage = null;
//	        if(error != null) {
//	        	errorMessage = "Prodotto non esistente";
//	        }
//	        
//	        model.addAttribute("errorMessage", errorMessage);
			return "redirect:/";
		}
    }
//    }
}