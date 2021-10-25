package it.univpm.shopgenius.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;
import it.univpm.shopgenius.services.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping("/product")
    public String showProduct(@RequestParam String productName, Model model) {
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
    	return "product_form";
    }
    
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
    	productService.saveProduct(product);
    	return "redirect:/";
    }
}
