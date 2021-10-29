package it.univpm.shopgenius.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.ProductService;
import it.univpm.shopgenius.services.ProductTypeService;
import it.univpm.shopgenius.services.UserService;
import it.univpm.shopgenius.utils.Utilities;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductTypeService productTypeService;
    
    @Autowired
    private UserService userService;

    private Utilities utilities = new Utilities();
    
	@PostMapping("/search")
    public String searchProduct(@RequestParam("productName") String productName, @RequestParam(value = "error", required = false) String error, Model model) {
		try {
			Product product = productService.getProductByName(productName);
	        model.addAttribute("productName", productName);
	        return "redirect:/product";
		} catch (Exception e) {
	        model.addAttribute("error", "Prodotto non esistente");
	        return "redirect:/";
		}
    }
	
    @GetMapping("")
    public String showProduct(@RequestParam("productName") String productName, Model model) {
    	String currentUserRole = utilities.getCurrentUserMajorRole();
    	Product product = productService.getProductByName(productName);
		if (currentUserRole.equals("admin") || currentUserRole.equals("user")) {
			String email = utilities.getCurrentUserName();
			User user = userService.findUserByEmail(email);
			Set<Product> products = user.getProducts();
	    	if (products.contains(product))
	    		model.addAttribute("isProductFav", true);
	    	else
	    		model.addAttribute("isProductFav", false);
		}
    	ProductType productType = product.getProductType();
    	model.addAttribute("role",currentUserRole);
    	model.addAttribute(product);
    	String productTypeName = productType.getTypeName();
    	model.addAttribute("productType", productTypeName);
    	return "product";
    }
    
    @GetMapping("/add")
    public String addProduct(Model model) {
    	Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("productTypes", productTypeService.getTypes());
    	return "product_form";
    }
    
    @GetMapping("/update")
    public String updateProduct(@RequestParam("productId") int id, Model model) {
    	Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("productTypes", productTypeService.getTypes());
    	return "product_form";
    }
    
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult br, @RequestParam("typeName") String typeName, Model model) {
    	if (br.hasErrors()) {
    		System.out.println("has errors");
    		model.addAttribute("productTypes", productTypeService.getTypes());
    		return "product_form";
    	} else {
	    	product.setProductType(productTypeService.getProductTypeFromName(typeName));
	    	productService.saveProduct(product);
	    	return "redirect:/product/list";
    	}
    }
    
    @GetMapping("/list")
    public String manageProducts(Model model) {
        List <Product> products = productService.getProducts();
        model.addAttribute("products", products);
    	return "list_products";
    }
    
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("productId") int id) {
    	Product product = productService.getProductById(id);
    	productService.delete(product);
        return "redirect:/product/list";
    }
}
