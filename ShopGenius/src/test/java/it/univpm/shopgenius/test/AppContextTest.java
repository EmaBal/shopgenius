package it.univpm.shopgenius.test;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import it.univpm.shopgenius.config.AppContext;

@Configuration
@ComponentScan(basePackages = { "it.univpm.shopgenius.model" })
@EnableTransactionManagement
public class AppContextTest extends AppContext {

	@Bean
	@Override
	protected Properties hibernateProperties() {
		Properties hibernateProp = super.hibernateProperties();
		hibernateProp.put("javax.persistence.schema-generation.database.action", "drop-and-create");
		return hibernateProp;
	}
}