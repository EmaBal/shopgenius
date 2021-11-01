package it.univpm.shopgenius.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = { "it.univpm.shopgenius.model" })

@EnableTransactionManagement
public class AppContext {
	private static Logger logger = LoggerFactory.getLogger(AppContext.class);

	@Bean
	public DataSource dataSource() {
		try {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
			ds.setUrl("jdbc:mysql://localhost:3306/shopgenius?createDatabaseIfNotExist=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
			ds.setUsername("root");
			ds.setPassword("");
			return ds;
		} catch (Exception e) {
			logger.error("DataSource bean cannot be created!!", e);
			logger.error("DataSource bean cannot be created!!", e);

			return null;
			
		}
	}
	 
	@Bean
	protected Properties hibernateProperties() {
		Properties hibernateProp = new Properties();
		hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		hibernateProp.put("hibernate.format_sql", true);
		hibernateProp.put("hibernate.use_sql_comments", true);
		hibernateProp.put("hibernate.show_sql", true);
		hibernateProp.put("hibernate.max_fetch_depth", 3);
		hibernateProp.put("hibernate.jdbc.batch_size", 10);
		hibernateProp.put("hibernate.jdbc.fetch_size", 50);
	    hibernateProp.put("hibernate.enable_lazy_load_no_trans", true);
//	    hibernateProp.put("hibernate.hbm2ddl.auto", "create");
		hibernateProp.put("javax.persistence.schema-generation.database.action", "update");
//		hibernateProp.put("javax.persistence.sql-load-script-source", "/data.sql");
//		hibernateProp.put("hibernate.hbm2ddl.import_files", "/data.sql");
		return hibernateProp;
	}

	@Bean
	@Autowired
	public SessionFactory sessionFactory(DataSource dataSource, Properties hibernateProperties) throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setPackagesToScan("it.univpm.shopgenius.model");
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	
	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) throws IOException {
		return new HibernateTransactionManager(sessionFactory);
	}
}
