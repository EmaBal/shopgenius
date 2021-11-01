package it.univpm.shopgenius.model.dao;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class DefaultDao {
		
	private SessionFactory sessionFactory;
	private Session session;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setSession(Session session) {
		this.session = session; 
	}
	
	public Session getSession() {
		Session session = this.session;
		if (session == null) {
			try {
				session = this.sessionFactory.getCurrentSession();
			} catch (HibernateException ex) {
				session = this.sessionFactory.openSession();
			}
		}
		return session;
	}

}
