package it.univpm.shopgenius.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univpm.shopgenius.model.dao.RoleDAO;
import it.univpm.shopgenius.model.dao.UserDAO;
import it.univpm.shopgenius.model.entities.Role;
import it.univpm.shopgenius.model.entities.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private RoleDAO roleDAO;

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//		User user = userDAO.findUserByUsername(email);
		User user = userDAO.findUserByEmail(email);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
//		UserBuilder builder = null;
//		if (user != null) {
//
//			// qui "mappiamo" uno User della nostra app in uno User di spring
//			builder = org.springframework.security.core.userdetails.User.withUsername(email);
//			builder.disabled(!user.isEnabled());
//			builder.password(user.getPassword());
//
////			String[] roles = new String[user.getRoles().size()];
////
////			int j = 0;
////			for (Role r : user.getRoles()) {
////				roles[j++] = r.getName();
////			}
////
////			builder.roles(roles);
//		} else {
//			throw new UsernameNotFoundException("User not found.");
//		}
//		return builder.build();
	}
	
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), new BCryptPasswordEncoder().encode(user.getPassword()),
                user.isEnabled(), true, true, true, authorities);
    }

	@Override
	public User findById(String email) {
		return this.userDAO.findUserByUsername(email);
	}
	
	@Override
	public User create(String first_name, String last_name, String email, String password, boolean isEnabled) {
		User newUser = this.userDAO.create(first_name, last_name, email, password, false);
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
    	user.setEnabled(true);
    	Role role = roleDAO.getRole("user");
    	Set<Role> roles = new HashSet<Role>();
    	roles.add(role);
    	user.setRoles(roles);
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
	
	@Override
	public User findUserByEmail(String email) {
		return userDAO.findUserByEmail(email);
	}
}
