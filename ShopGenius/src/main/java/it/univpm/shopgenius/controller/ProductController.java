package it.univpm.shopgenius.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.ProductService;
import it.univpm.shopgenius.services.ProductTypeService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductTypeService productTypeService;

	@PostMapping("/searchProduct")
    public String searchProduct(@RequestParam("productName") String productName, @RequestParam(value = "error", required = false) String error, Model model) {
		try {
	        model.addAttribute("productName", productName);
	        return "redirect:/product";
		} catch (Exception e) {
	        model.addAttribute("error", "Prodotto non esistente");
	        return "redirect:/";
		}
    }
	
    @GetMapping("/product")
    public String showProduct(@RequestParam("productName") String productName, Model model) {
    	Product product = productService.getProductByName(productName);
    	ProductType productType = product.getProductType();
    	model.addAttribute(product);
    	String productTypeName = productType.getTypeName();
    	model.addAttribute("productType", productTypeName);
    	return "product";
    }
    
    @GetMapping("/addProduct")
    public String addProduct(Model model) {
    	Product product = new Product();
        model.addAttribute("product", product);
//        model.addAttribute("productTypesNames", productTypeService.getTypesNames());
        model.addAttribute("productTypes", productTypeService.getTypes());
    	return "product_form";
    }
    
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product, @RequestParam("typeName") String typeName) {
//    	product.setProductType(productType.get)
    	product.setProductType(productTypeService.getProductTypeFromName(typeName));
    	productService.saveProduct(product);
    	return "redirect:/";
    }
}
