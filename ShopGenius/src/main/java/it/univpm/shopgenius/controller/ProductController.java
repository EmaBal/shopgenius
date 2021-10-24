package it.univpm.shopgenius.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.univpm.shopgenius.services.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
//    @GetMapping("/product")
//    public String showProduct(Model model) {
//    	Product product = productService.getProduct()
//    }
}
