package it.univpm.shopgenius.model.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;

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
		Query query = this.getSession().createNamedQuery("Product_GetProductByName", Product.class);
		query.setParameter("name", name);
		return (Product)query.getSingleResult();
	}
	
	@Override
	public Product create(String name, float price, int quantity, String locationDetail, ProductType pType) {
		if (name == null || name.length() == 0 || price < 0 || quantity < 0 || locationDetail == null || locationDetail.length() == 0)
			throw new RuntimeException("Empty fields not allowed");
		Product p = new Product();
		p.setName(name);
		p.setPrice(price);
		p.setQuantity(quantity);
		p.setLocationDetail(locationDetail);
		p.setProductType(pType);
		this.getSession().save(p);
		return p;
	}

	@Override
	public void delete(Product product) {
		 Session currentSession = this.getSession();
		 if(product.getName() != null)
			 currentSession.delete(product);
	}
	
	@Override
	public void update(Product product) {
        Session currentSession = this.getSession();
        currentSession.saveOrUpdate(product);
	}
	
    @Override
    public void saveProduct(Product product) {
        Session currentSession = this.getSession();
        currentSession.saveOrUpdate(product);
    }
    
    @Override
    public List<Product> getProducts() {
        Session session = this.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery <Product> cq = cb.createQuery(Product.class);
        Root <Product> root = cq.from(Product.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }
    
    @Override
    public List<Product> findProducts(String searchTerm) {
		Query query = this.getSession().createNamedQuery("Product_FindProductsBySearchTerm", Product.class);
		query.setParameter("name", searchTerm);
		return (List<Product>)query.getResultList();
    }
}
