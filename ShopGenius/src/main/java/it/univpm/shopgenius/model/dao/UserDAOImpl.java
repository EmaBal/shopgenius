package it.univpm.shopgenius.model.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.Role;
import it.univpm.shopgenius.model.entities.User;


@Repository
public class UserDAOImpl extends DefaultDao implements UserDAO {

	private PasswordEncoder passwordEncoder;
	
	@Override
	public User create(String first_name, String last_name, String email, String password, boolean isEnabled) {
		if (first_name == null || first_name.length() == 0 || last_name == null || last_name.length() == 0 || email == null || email.length() == 0 || password == null || password.length() == 0)
			throw new RuntimeException("Empty fields not allowed");
		User u = new User();
		u.setFirstName(first_name);
		u.setLastName(last_name);
		u.setEmail(email);
		u.setPassword(password);
		u.setEnabled(isEnabled);
		this.getSession().save(u);
		return u;
	}
	
	@Override
	public User update(User user) {
		return (User)this.getSession().merge(user);
	}
	
    @Override
    public List <User> getUsers() {
        Session session = this.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery <User> cq = cb.createQuery(User.class);
        Root <User> root = cq.from(User.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void deleteUser(int id) {
    	Session session = this.getSession();
        User user = session.byId(User.class).load(id);
        if (user != null)
        	session.delete(user);
    }

    @Override
    public void saveUser(User user) {
        Session currentSession = this.getSession();
        currentSession.saveOrUpdate(user);
    }

    @Override
    public User getUser(int id) {
        Session currentSession = this.getSession();
        User user = currentSession.get(User.class, id);
        return user;
    }
    
    @Override
    public User findUserByEmail(String email) {
    	CriteriaBuilder cb = this.getSession().getCriteriaBuilder();
    	CriteriaQuery<User> cq = cb.createQuery(User.class);
    	Root <User> root = cq.from(User.class);
    	cq.select(root).where(cb.equal(root.get("email"), email));
    	Query query = this.getSession().createQuery(cq);
    	return (User)query.getSingleResult();
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
	
	@Override
	public void addRole(User user, Role role) {
		user.addRole(role);
	}

	@Override
	public void removeRole(User user, Role role) {
		user.removeRole(role);
		
	}

}
