package it.univpm.shopgenius.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.shopgenius.model.dao.ProductDAO;
import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;

@Transactional
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
	public Product create(String name, float price, int quantity, String locationDetail, ProductType pType) {
		return productDAO.create(name, price, quantity, locationDetail, pType);
	}

	@Override
	public void delete(Product product) {
		productDAO.delete(product);
		
	}

	@Override
	public void update(Product product) {
		productDAO.update(product);
	}

	@Override
	public void saveProduct(Product product) {
		productDAO.saveProduct(product);
	}
	
	@Override
	public List<Product> getProducts() {
		return productDAO.getProducts();
	}
	
	@Override
	public List<Product> findProducts(String searchTerm) {
		return productDAO.findProducts(searchTerm);
	}

}
