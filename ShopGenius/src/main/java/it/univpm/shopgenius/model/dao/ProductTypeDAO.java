package it.univpm.shopgenius.model.dao;

import java.util.List;

import it.univpm.shopgenius.model.entities.ProductType;

public interface ProductTypeDAO {
	
	public void create(String name);
	
	public void delete(ProductType productType);

	public List<ProductType> getTypes();

	public List<String> getTypesNames();

	public ProductType getProductTypeFromName(String pTypeName);
	
}
