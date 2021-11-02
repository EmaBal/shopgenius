package it.univpm.shopgenius.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;

public interface ProductDAO {
	
	public Product getProductById(int id);
	
	public Product getProductByName(String name);

	public void delete(Product produdct);
	
	public void update(Product product);

	public void saveProduct(Product product);

	public List<Product> getProducts();
	
	Session getSession();
	public void setSession(Session session);

	public Product create(String name, float price, int quantity, String locationDetail, ProductType pType);

	public List<Product> findProducts(String searchTerm);
}
