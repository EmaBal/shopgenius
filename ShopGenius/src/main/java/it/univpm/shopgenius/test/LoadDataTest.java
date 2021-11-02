package it.univpm.shopgenius.test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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

public class LoadDataTest {

	public static void main(String ...args) {
		
		
//		logger.info("Entrato ...");
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
			
				// phase 1 : add data to database
				
				session.beginTransaction();

				Role admin = roleDAO.create("admin");
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
				Product coke = productDAO.create("Coca-cola", (float)1.99, 50, "B3", beverages);
				Product phone = productDAO.create("Xiaomi Redmi Note 10 Pro", (float)299.99, 5, "C2", electronics);
				Product soap = productDAO.create("Liquid soap", (float)3.50, 200, "H4", pcare);

				session.getTransaction().commit();
				session.beginTransaction();
				
				User mrossi = userDAO.create("Mario", "Rossi", "mariorossi@gmail.com", "mariorossi", true);
				Set<Role> roleSet = new HashSet<>();
				roleSet.add(user);
				mrossi.setRoles(roleSet);
				
				roleSet = new HashSet<>();
				roleSet.add(admin);
				roleSet.add(user);
				User lbianchi = userDAO.create("Luca", "Bianchi", "lucabianchi@gmail.com", "lucabianchi", true);
				lbianchi.setRoles(roleSet);
				
				session.getTransaction().commit();
				session.beginTransaction();
				
				//lbianchi.addRole(admin);
				
				session.getTransaction().commit();
				session.beginTransaction();
				
				favoriteDAO.add(mrossi, eggs);
				favoriteDAO.add(mrossi, coke);
				favoriteDAO.add(mrossi, soap);
				
				favoriteDAO.add(lbianchi, eggs);
				favoriteDAO.add(lbianchi, phone);
				
				session.getTransaction().commit();
				
//				assert mj.getAlbums().size() == 0;
//				assert rw.getAlbums().size() == 0;
//				
//				// fai refresh per ricaricare le collezioni di mj ed rw
//				session.refresh(mj);
//				session.refresh(rw);
//				
//				assert mj.getAlbums().size() == 1;
//				assert rw.getAlbums().size() == 1;
//				
//				Album tdb = favoriteDAO.create("The division bell", rw);
//				tdb.setSinger(rw);
//				favoriteDAO.update(tdb);
//				
//			
//				Instrument i1 = productDAO.findByName("Stratocaster");
//				Instrument i2 = productDAO.findByName("Moog");
//				Instrument i3 = productDAO.findByName("Stradivari");
//							
//				session.getTransaction().commit();
//				
//				session.beginTransaction();
//
//				rw.addInstrument(i1);
//				rw = userDAO.update(rw);
//
//				assert(rw.getInstruments().contains(i1));
//				assert(i1.getSingers().contains(rw));
//				
//				session.getTransaction().commit();
//				
//				session.beginTransaction();
//
//				
//				rw.addInstrument(i2);
//				rw = userDAO.update(rw);
//				
//				assert rw.getInstruments().contains(i2);
//				assert i2.getSingers().contains(rw);
//				
//				session.getTransaction().commit();
//
//				session.beginTransaction();
//
//				mj.addInstrument(i2);
//				mj.addInstrument(i3);
//				mj = userDAO.update(mj);
//				
//				assert mj.getInstruments().contains(i2) == true;
//				assert mj.getInstruments().contains(i3) == true;
//				assert i2.getSingers().contains(mj);
//				assert i3.getSingers().contains(mj);
//				
//				session.getTransaction().commit();
//				
//				session.beginTransaction();
//
//				// rimuovi tutte le entita` collegate a quella da eliminare
//				mj.getInstruments().clear();
//				for (Album a : mj.getAlbums()) {
//					favoriteDAO.delete(a);
//				}
//				mj.getAlbums().clear();
//				mj = userDAO.update(mj);
//
//				// elimina l'entita`
//				userDAO.delete(mj);
//
//				session.getTransaction().commit();
//				
//				session.beginTransaction();
//
//				// phase 2 : navigate data in the database
//				
//				List<Singer> all = userDAO.findAll();
//				
//				System.out.println("Number of singers: " + all.size());
//				for (Singer s : all) {
//					System.out.println(" - " + s.getFullName() + " : " + s.getBirthDate());
//					
//					Set<Album> albums = userDAO.getAlbums(s);
//					System.out.println("Number of albums: " + albums.size());
//					for (Album a : albums) {
//						System.out.println("  - " + a.getTitle());					
//					}
//				}
//				
//				List<Instrument> allInstruments = productDAO.findAll();
//				System.out.println("Number of instruments: " + allInstruments.size());
//				for (Instrument i : allInstruments) {
//					System.out.println(" - " + i.getFamily() + " : " + i.getName());
//					Set<Singer> singers = i.getSingers();
//					
//					if (singers == null) {
//						singers = new HashSet<Singer>();
//					}
//					
//					System.out.println("Number of singers: " + singers.size());
//					for (Singer s : singers) {
//						System.out.println("  - " + s.getFullName());
//					}
//				}
//				
//				session.getTransaction().commit();
//				
//				// phase 3 : create user
//				session.beginTransaction();
//				
//				Role r1 = roleDao.create("USER");
//				Role r2 = roleDao.create("ADMIN");
//				
//				session.getTransaction().commit();
//				
//				session.beginTransaction();
//				
//				User u1 = userDao.create("user1", userDao.encryptPassword("user1"), true);				
//				u1.addRole(r1);
//				
//				User u2 = userDao.create("user2", userDao.encryptPassword("user2"), true);
//				u2.addRole(r1);
//				u2.addRole(r2);
//				
//				userDao.update(u1);
//				userDao.update(u2);
//				session.getTransaction().commit();
			}

		} catch (Exception e) {
//			logger.error("Eccezione: " + e.getMessage());
			e.printStackTrace(System.err);
		}
//		logger.info("Esco ...");
	}
}

