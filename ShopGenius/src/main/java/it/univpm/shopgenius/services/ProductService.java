package it.univpm.shopgenius.services;

import java.util.List;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;

public interface ProductService {
	
	public Product getProductById(int id);
	
	public Product getProductByName(String name);

	public void delete(Product product);
	
	public void update(Product product);
	
	public void saveProduct(Product product);

	public List<Product> getProducts();

	public Product create(String name, float price, int quantity, String locationDetail, ProductType pType);

	public List<Product> findProducts(String searchTerm);
}
