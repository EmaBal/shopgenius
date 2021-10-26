package it.univpm.shopgenius.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	
	@Override
	public List<ProductType> getTypes() {
		Session currentSession = this.getSession();
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();
		CriteriaQuery<ProductType> criteria = builder.createQuery(ProductType.class);
		criteria.from(ProductType.class);
		List<ProductType> productTypes = currentSession.createQuery(criteria).getResultList();
		return productTypes;
	}
	
	@Override
	public List<String> getTypesNames() {
		List<String> typeNamesList = new ArrayList<String>();
		for (ProductType el: getTypes()) {
			typeNamesList.add(el.getTypeName());
		}
		return typeNamesList;
	}
	
	@Override
	public ProductType getProductTypeFromName(String pTypeName) {
    	CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
    	CriteriaQuery<ProductType> cq = cb.createQuery(ProductType.class);
    	Root <ProductType> root = cq.from(ProductType.class);
    	cq.select(root).where(cb.equal(root.get("typeName"), pTypeName));
    	Query query = this.getSession().createQuery(cq);
    	return (ProductType)query.getSingleResult();
	}
}
