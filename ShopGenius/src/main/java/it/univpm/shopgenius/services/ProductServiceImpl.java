package it.univpm.shopgenius.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univpm.shopgenius.model.dao.ProductDAO;
import it.univpm.shopgenius.model.entities.Product;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;
    
	@Override
	public Product getProductById(int id) {
		return productDAO.getProductById(id);
	}

	@Override
	public Product getProductByName(String name) {
		return productDAO.getProductByName(name);
	}

	@Override
	public Product create(String name, float price, int quantity, String locationDetail) {
		return productDAO.create(name, price, quantity, locationDetail);
	}

	@Override
	public void delete(Product produdct) {
		productDAO.delete(produdct);
		
	}

	@Override
	public void update(Product product) {
		productDAO.update(product);
	}

}
