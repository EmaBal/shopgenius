package it.univpm.shopgenius.model.dao;

import it.univpm.shopgenius.model.entities.Product;

public interface ProductDAO {
	
	public Product getProductById(int id);
	
	public Product getProductByName(String name);
	
	public Product create(String name, float price, int quantity, String locationDetail);

	public void delete(Product produdct);
	
	public void update(Product product);
}
