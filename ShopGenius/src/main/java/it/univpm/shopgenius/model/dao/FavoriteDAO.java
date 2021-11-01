package it.univpm.shopgenius.model.dao;

import org.hibernate.Session;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

public interface FavoriteDAO {
	
	public void delete(User user, Product product);

	public void add(User user, Product product);
	
	Session getSession();
	public void setSession(Session session);
	
}
