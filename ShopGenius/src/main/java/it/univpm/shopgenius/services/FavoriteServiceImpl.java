package it.univpm.shopgenius.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.shopgenius.model.dao.FavoriteDAO;
import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

@Transactional
@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	FavoriteDAO favoriteDAO;
	
	@Transactional
	@Override
	public void add(User user, Product product) {
		favoriteDAO.add(user, product);
		
	}
	
	@Transactional
	@Override
	public void delete(User user, Product product) {
		favoriteDAO.delete(user, product);
	}

}
