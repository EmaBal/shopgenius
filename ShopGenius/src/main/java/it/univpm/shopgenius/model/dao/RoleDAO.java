package it.univpm.shopgenius.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.univpm.shopgenius.model.entities.Role;

public interface RoleDAO {

	public Role create(String name);
	
	public Role update(Role role);
	
	public void delete(Role role);

	public Role getRole(String role_name);
	
	Session getSession();
	public void setSession(Session session);

	public List<Role> getRoles();

}
