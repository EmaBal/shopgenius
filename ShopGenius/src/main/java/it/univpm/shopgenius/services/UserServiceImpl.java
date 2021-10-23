package it.univpm.shopgenius.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.shopgenius.model.dao.UserDAO;
import it.univpm.shopgenius.model.entities.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDAO userDAO;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userDAO.findUserByUsername(email);
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
		return this.userDAO.findUserByUsername(email);
	}
	
	@Override
	public User create(String email, String password) {
		User newUser = this.userDAO.create(email, password);
		return newUser;
	}
	
    @Override
    @Transactional
    public List <User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
    
	@Override
	public User update(User user) {
		return this.userDAO.update(user);
	}
}
