package it.univpm.shopgenius.model.dao;

import java.util.List;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

public interface FavoriteDAO {
	
	public List<Product> getFavoritesFromEmail(String email);
	
	public void delete(User user, Product product);

	public void add(User user, Product product);
	
}
