package it.univpm.shopgenius.model.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import it.univpm.shopgenius.model.entities.User;

@Repository
public class UserDAOImpl extends DefaultDao implements UserDAO {

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
