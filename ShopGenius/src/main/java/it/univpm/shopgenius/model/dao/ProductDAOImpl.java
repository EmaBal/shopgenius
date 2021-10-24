package it.univpm.shopgenius.model.dao;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

@Repository
public class ProductDAOImpl extends DefaultDao implements ProductDAO {
	
	@Override
	public Product getProductById(int id) {
		 Session currentSession = this.getSession();
	     Product product = currentSession.get(Product.class, id);
	     return product;
	}
	
	@Override
	public Product getProductByName(String name) {
    	CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
    	CriteriaQuery<Product> cq = cb.createQuery(Product.class);
    	Root <Product> root = cq.from(Product.class);
    	cq.select(root).where(cb.equal(root.get("name"), name));
    	Query query = this.getSession().createQuery(cq);
    	return (Product)query.getSingleResult();
	}
	
	@Override
	public Product create(String name, float price, int quantity, String locationDetail) {
		// da mettere nel controller
		Product p = new Product();
		p.setName(name);
		p.setPrice(price);
		p.setQuantity(quantity);
		p.setLocationDetail(locationDetail);
		this.getSession().save(p);
		return p;
	}

	@Override
	public void delete(Product product) {
		 Session currentSession = this.getSession();
	     currentSession.delete(product);
	}
	
	@Override
	public void update(Product product) {
        Session currentSession = this.getSession();
        currentSession.saveOrUpdate(product);
	}
}
