package it.univpm.shopgenius.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.FavoriteService;
import it.univpm.shopgenius.services.ProductService;
import it.univpm.shopgenius.services.UserService;
import it.univpm.shopgenius.utils.Utilities;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FavoriteService favoriteService;
	
	Utilities utilities = new Utilities();
	
	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails)principal).getUsername();
		return userService.findUserByEmail(email);
	}
	
	@GetMapping("")
	public String viewFavorites(Model model) {
		User user = getCurrentUser();
		Set<Product> favorites = user.getProducts();
		model.addAttribute("favorites", favorites);
		
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
    	model.addAttribute("username", utilities.getCurrentUserName());
		return "tiles_favorites";
	}
	
    @GetMapping("/delete")
    public String deleteFav(@RequestParam("productId") int id, HttpServletRequest request) {
    	User user = getCurrentUser();
    	favoriteService.delete(user, productService.getProductById(id));
    	userService.update(user);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
	@GetMapping("/add")
	public String addFav(@RequestParam("productName") String productName, Model model) {
		User user = getCurrentUser();
		favoriteService.add(user, productService.getProductByName(productName));
		userService.update(user);
		model.addAttribute("productName", productName);
		return "redirect:/product";
	}
}
