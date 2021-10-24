package it.univpm.shopgenius.model.dao;

import org.hibernate.Session;

import it.univpm.shopgenius.model.entities.Role;

public interface RoleDAO {
	
	Session getSession();
	
	public void setSession(Session session);

	Role create(String name);
	
	Role update(Role role);
	
	void delete(Role role);

	Role getRole(String role_name);

}
