package it.univpm.shopgenius.model.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.ProductType;

@Repository
public class ProductTypeDAOImpl extends DefaultDao implements ProductTypeDAO {
	
	@Override
	public void create(String name) {
		//da vedere (implementare nel controller?)
	}
	
	@Override
	public void delete(ProductType productType) {
		 Session currentSession = this.getSession();
	     currentSession.delete(productType);
	}
}
