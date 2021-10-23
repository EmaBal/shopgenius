package it.univpm.shopgenius.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.shopgenius.model.dao.UserDetailsDao;
import it.univpm.shopgenius.model.entities.Role;
import it.univpm.shopgenius.model.entities.User;

public class UserDetailsServiceImpl implements CustomUserDetailsService, UserDetailsService {

	@Autowired
	private UserDetailsDao userDetailsDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userDetailsDao.findUserByUsername(email);
		UserBuilder builder = null;
		if (user != null) {

			// qui "mappiamo" uno User della nostra app in uno User di spring
			builder = org.springframework.security.core.userdetails.User.withUsername(email);
			builder.password(user.getPassword());

//			String[] roles = new String[user.getRoles().size()];
//
//			int j = 0;
//			for (Role r : user.getRoles()) {
//				roles[j++] = r.getName();
//			}
//
//			builder.roles(roles);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}

	@Override
	public User findById(String email) {
		return this.userDetailsDao.findUserByUsername(email);
	}

	@Override
	public User create(String email, String password) {
		User newUser = this.userDetailsDao.create(email, password);
		return newUser;
	}

	@Override
	public void delete(String email) {
		User user = this.userDetailsDao.findUserByUsername(email);
		this.userDetailsDao.delete(user);
	}

	@Override
	public User update(User user) {
		return this.userDetailsDao.update(user);
	}
}
