package it.univpm.shopgenius.model.dao;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.Role;
import it.univpm.shopgenius.model.entities.User;

//@Transactional
@Repository
public class RoleDAOImpl extends DefaultDao implements RoleDAO {

	@Override
	public Role create(String name) {
		Role r = new Role();
		r.setName(name);
		this.getSession().save(r);
		
		return r;
	}

	@Override
	public Role update(Role role) {
		return (Role)this.getSession().merge(role);
	}

	@Override
	public void delete(Role role) {
		this.getSession().delete(role);
	}
	
	@Override
	public Role getRole(String role_name) {
    	CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
    	CriteriaQuery<Role> cq = cb.createQuery(Role.class);
    	Root <Role> root = cq.from(Role.class);
    	cq.select(root).where(cb.equal(root.get("name"), role_name));
    	Query query = this.getSession().createQuery(cq);
    	return (Role)query.getSingleResult();
	}

}
