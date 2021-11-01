package it.univpm.shopgenius.services;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

public interface FavoriteService {
	
	public void add(User user, Product product);
	
	public void delete(User user, Product product);
}
