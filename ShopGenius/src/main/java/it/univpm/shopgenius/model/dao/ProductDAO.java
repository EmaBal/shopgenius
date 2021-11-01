package it.univpm.shopgenius.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.univpm.shopgenius.model.entities.Product;

public interface ProductDAO {
	
	public Product getProductById(int id);
	
	public Product getProductByName(String name);
	
	public Product create(String name, float price, int quantity, String locationDetail);

	public void delete(Product produdct);
	
	public void update(Product product);

	public void saveProduct(Product product);

	public List<Product> getProducts();
	
	Session getSession();
	public void setSession(Session session);
}
