package it.univpm.shopgenius.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.FavoriteService;
import it.univpm.shopgenius.services.ProductService;
import it.univpm.shopgenius.services.UserService;

@Controller
public class FavoriteController {

	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FavoriteService favoriteService;
	
	public User getLoggedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetails)principal).getUsername();
		return userService.findUserByEmail(email);
	}
	
	@GetMapping("/favorites")
	public String viewFavorites(Model model) {
		User user = getLoggedUser();
		Set<Product> favorites = user.getProducts();
		model.addAttribute("favorites", favorites);
		return "fav_list";
	}
	
    @GetMapping("/favorites/delete")
    public String deleteFav(@RequestParam("productId") int id) {
    	User user = getLoggedUser();
    	favoriteService.delete(user, productService.getProductById(id));
        return "redirect:/favorites";
    }
    
	@GetMapping("/addFav")
	public String addFav(@RequestParam("productName") String productName, Model model) {
		User user = getLoggedUser();
		favoriteService.add(user, productService.getProductByName(productName));
		model.addAttribute(productName);
		return "product";
	}
}
