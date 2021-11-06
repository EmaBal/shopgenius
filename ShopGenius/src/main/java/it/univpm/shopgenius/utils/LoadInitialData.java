package it.univpm.shopgenius.utils;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import it.univpm.shopgenius.config.AppContext;
import it.univpm.shopgenius.model.dao.FavoriteDAO;
import it.univpm.shopgenius.model.dao.ProductDAO;
import it.univpm.shopgenius.model.dao.ProductTypeDAO;
import it.univpm.shopgenius.model.dao.RoleDAO;
import it.univpm.shopgenius.model.dao.UserDAO;
import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;
import it.univpm.shopgenius.model.entities.Role;
import it.univpm.shopgenius.model.entities.User;

public class LoadInitialData {

	public static void main(String ...args) {
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class)) {

			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			
			UserDAO userDAO = ctx.getBean(UserDAO.class);
			FavoriteDAO favoriteDAO = ctx.getBean(FavoriteDAO.class);
			ProductDAO productDAO = ctx.getBean(ProductDAO.class);
			ProductTypeDAO productTypeDAO = ctx.getBean(ProductTypeDAO.class);
			RoleDAO roleDAO = ctx.getBean(RoleDAO.class);
			
			userDAO.setPasswordEncoder(new BCryptPasswordEncoder());
			
			try (Session session = sf.openSession()) {
				
				userDAO.setSession(session);
				favoriteDAO.setSession(session);
				productDAO.setSession(session);
				productTypeDAO.setSession(session);
				roleDAO.setSession(session);
				
				session.beginTransaction();

				Role admin = roleDAO.create("admin");
				Role employee = roleDAO.create("employee");
				Role user = roleDAO.create("user");
				
				session.getTransaction().commit();
				session.beginTransaction();
				
				ProductType food = productTypeDAO.create("Food");
				ProductType beverages = productTypeDAO.create("Beverages");
				ProductType electronics = productTypeDAO.create("Electronics");
				ProductType pcare = productTypeDAO.create("Personal care");
				
				session.getTransaction().commit();
				session.beginTransaction();
				
				Product eggs = productDAO.create("Eggs 6 pz", 4, 20, "A1", food);
				Product eggs2 = productDAO.create("Eggs 4 pz", 3, 20, "A2", food);
				Product coke = productDAO.create("Coca-cola 1.5 lt", (float)1.99, 50, "B3", beverages);
				Product pepsi = productDAO.create("Pepsi 2 lt", (float)2.49, 40, "B3", beverages);
				Product phone = productDAO.create("Xiaomi Redmi Note 10 Pro 6+64GB", (float)299.99, 0, "C2", electronics);
				Product phone2 = productDAO.create("Xiaomi Redmi Note 10 4+128GB", (float)211, 3, "C3", electronics);
				Product soap = productDAO.create("Liquid soap 500 ml", (float)3.50, 200, "H4", pcare);

				session.getTransaction().commit();
				session.beginTransaction();
				
				User mrossi = userDAO.create("Mario", "Rossi", "mariorossi@gmail.com", "mariorossi", true);
				Set<Role> roleSet = new HashSet<>();
				roleSet.add(user);
				mrossi.setRoles(roleSet);
				
				roleSet = new HashSet<>();
				roleSet.add(admin);
				roleSet.add(employee);
				roleSet.add(user);
				User lbianchi = userDAO.create("Luca", "Bianchi", "lucabianchi@gmail.com", "lucabianchi", true);
				lbianchi.setRoles(roleSet);
				
				roleSet = new HashSet<>();
				roleSet.add(employee);
				roleSet.add(user);
				User gneri = userDAO.create("Giorgia", "Neri", "giorgianeri@gmail.com", "giorgianeri", true);
				gneri.setRoles(roleSet);
				
				session.getTransaction().commit();
				session.beginTransaction();
				
				favoriteDAO.add(mrossi, eggs);
				favoriteDAO.add(mrossi, coke);
				favoriteDAO.add(mrossi, soap);
				
				favoriteDAO.add(lbianchi, eggs);
				favoriteDAO.add(lbianchi, phone);
				
				favoriteDAO.add(gneri, eggs2);
				favoriteDAO.add(gneri, soap);
				
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}

