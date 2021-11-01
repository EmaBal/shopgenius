package it.univpm.shopgenius.model.dao;

import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

@Repository
public class FavoriteDAOImpl extends DefaultDao implements FavoriteDAO {

	@Override
	public void add(User user, Product product) {
		user.addProduct(product);
	}

	@Override
	public void delete(User user, Product product) {
		user.removeProduct(product);
	}

}
