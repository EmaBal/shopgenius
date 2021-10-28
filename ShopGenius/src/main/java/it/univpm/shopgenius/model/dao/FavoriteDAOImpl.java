package it.univpm.shopgenius.model.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

@Repository
public class FavoriteDAOImpl extends DefaultDao implements FavoriteDAO {

	@Override
	public List<Product> getFavoritesFromEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(User user, Product product) {
		System.out.println("Passaggio per add in FavoriteDAOImpl");
		user.addProduct(product);
	}

	@Override
	public void delete(User user, Product product) {
		user.removeProduct(product);
	}

}
