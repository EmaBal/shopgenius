package it.univpm.shopgenius.services;

import it.univpm.shopgenius.model.entities.Product;

public interface ProductService {
	
	public Product getProductById(int id);
	
	public Product getProductByName(String name);
	
	public Product create(String name, float price, int quantity, String locationDetail);

	public void delete(Product produdct);
	
	public void update(Product product);
	
	public void saveProduct(Product product);
}
