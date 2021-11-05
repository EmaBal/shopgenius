package it.univpm.shopgenius.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univpm.shopgenius.model.dao.RoleDAO;
import it.univpm.shopgenius.model.entities.Role;

@Service
public class RoleServiceImpl {
	
	@Autowired
	RoleDAO roleDAO;
	
	public List<Role> getRoles() {
		return roleDAO.getRoles();
	}
}
