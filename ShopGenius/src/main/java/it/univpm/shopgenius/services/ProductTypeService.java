package it.univpm.shopgenius.services;

import java.util.List;

import it.univpm.shopgenius.model.entities.ProductType;

public interface ProductTypeService {
	
	public ProductType create(String name);
	
	public void delete(ProductType productType);

	public List<ProductType> getTypes();

	public List<String> getTypesNames();

	ProductType getProductTypeFromName(String pTypeName);
}
