package it.univpm.shopgenius.services;

import it.univpm.shopgenius.model.entities.ProductType;

public interface ProductTypeService {
	
	public void create(String name);
	
	public void delete(ProductType productType);
}
