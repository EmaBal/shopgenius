package it.univpm.shopgenius.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
			@SuppressWarnings("unused")
			Product product = productService.getProductByName(productName);
	        model.addAttribute("productName", productName);
	        return "redirect:/product";
		} catch (Exception e) {
	        model.addAttribute("error", "Product not found");
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
//    	ProductType productType = product.getProductType();
    	model.addAttribute("role",currentUserRole);
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
			} catch (Exception e) {
		    	model.addAttribute("current_firstName", "anonymous");
		    	model.addAttribute("current_lastName", "anonymous");
			}
    	model.addAttribute(product);
//    	String productTypeName = productType.getTypeName();
//    	model.addAttribute("productType", productTypeName);
    	return "tiles_product";
    }
    
    @GetMapping("/add")
    public String addProduct(@RequestParam(value = "error", required = false) String error, Model model) {
    	Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("productTypes", productTypeService.getTypes());
        model.addAttribute("error", error);
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
			} catch (Exception e) {
		    	model.addAttribute("current_firstName", "anonymous");
		    	model.addAttribute("current_lastName", "anonymous");
			}
    	return "tiles_product_form";
    }
    
    @GetMapping("/update")
    public String updateProduct(@RequestParam("productId") int id, Model model) {
    	Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("productTypes", productTypeService.getTypes());
        model.addAttribute("isUpdating", true);
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
			} catch (Exception e) {
		    	model.addAttribute("current_firstName", "anonymous");
		    	model.addAttribute("current_lastName", "anonymous");
			}
    	return "tiles_product_form";
    }
    
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult br, @RequestParam("typeName") String typeName, @RequestParam(value="isUpdating", required = false, defaultValue = "false") boolean isUpdating, @RequestParam(value="oldProductName", required=false) String oldProductName, Model model) {
    	if (br.hasErrors()) {
    		model.addAttribute("productTypes", productTypeService.getTypes());
        	model.addAttribute("role",utilities.getCurrentUserMajorRole());
    		try {
    	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
    	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
    			} catch (Exception e) {
    		    	model.addAttribute("current_firstName", "anonymous");
    		    	model.addAttribute("current_lastName", "anonymous");
    			}
    		return "tiles_product_form";
    	} else {
	    	product.setProductType(productTypeService.getProductTypeFromName(typeName));
	    	if (isUpdating == false) {
		    	try {
			    	productService.getProductByName(product.getName());
			    	model.addAttribute("error", "Product already inserted");
	    			return "redirect:/product/add";
		    	} catch (Exception e) {
			    	productService.saveProduct(product);
			    	productService.update(product);
			    	return "redirect:/product/list";
		    	}
	    	} else {
	    		if (oldProductName.equals(product.getName())) {
			    	productService.saveProduct(product);
			    	productService.update(product);
			    	return "redirect:/product/list";
	    		} else {
	    			try {
				    	productService.getProductByName(product.getName());
				    	model.addAttribute("error", "Product already inserted");
				    	model.addAttribute("productId", product.getId());
		    			return "redirect:/product/update";
			    	} catch (Exception e) {
				    	productService.saveProduct(product);
				    	productService.update(product);
				    	return "redirect:/product/list";
			    	}
	    		}
	    	}
    	}
    }
    
    @GetMapping("/list")
    public String manageProducts(Model model) {
        List <Product> products = productService.getProducts();
        model.addAttribute("products", products);
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
			} catch (Exception e) {
		    	model.addAttribute("current_firstName", "anonymous");
		    	model.addAttribute("current_lastName", "anonymous");
			}
    	return "tiles_list_products";
    }
    
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("productId") int id) {
    	Product product = productService.getProductById(id);
    	productService.delete(product);
        return "redirect:/product/list";
    }
    
    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> searchAutocomplete(@RequestParam(value="term", required = false, defaultValue="") String term) {
    	List<String> suggestions = new ArrayList<String>();
    	List<Product> productByTerm = productService.findProducts(term);
    	for (Product product : productByTerm) {
			suggestions.add(product.getName());
		}
    	return suggestions;
    }
}
