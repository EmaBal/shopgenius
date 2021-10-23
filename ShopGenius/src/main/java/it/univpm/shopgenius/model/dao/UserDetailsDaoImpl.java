package it.univpm.shopgenius.model.dao;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.User;

//@Transactional
@Repository
public class UserDetailsDaoImpl extends DefaultDao implements UserDetailsDao {

	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findUserByUsername(String email) {
		return this.getSession().get(User.class, email);
	}

	@Override
	public User create(String email, String password) {
		User u = new User();
		u.setEmail(email);
		u.setPassword(password);
		this.getSession().save(u);
		
		return u;
	}

	@Override
	public User update(User user) {
		return (User)this.getSession().merge(user);
	}

	@Override
	public void delete(User user) {
		this.getSession().delete(user);
		
	}

	@Override
	public String encryptPassword(String password) {
		return this.passwordEncoder.encode(password);
	}
	
	@Override
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public PasswordEncoder getpasswordEncoder() {
		return this.passwordEncoder;
	}

}
