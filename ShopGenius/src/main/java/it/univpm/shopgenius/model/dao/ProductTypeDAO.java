package it.univpm.shopgenius.model.dao;

import it.univpm.shopgenius.model.entities.ProductType;

public interface ProductTypeDAO {
	
	public void create(String name);
	
	public void delete(ProductType productType);
	
}
