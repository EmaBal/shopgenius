package it.univpm.shopgenius.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univpm.shopgenius.model.dao.ProductTypeDAO;
import it.univpm.shopgenius.model.entities.ProductType;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	ProductTypeDAO productTypeDAO;
	
	@Override
	public void create(String name) {
		productTypeDAO.create(name);	
	}

	@Override
	public void delete(ProductType productType) {
		productTypeDAO.delete(productType);
	}

}
