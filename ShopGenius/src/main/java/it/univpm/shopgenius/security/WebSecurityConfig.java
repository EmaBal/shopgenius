package it.univpm.shopgenius.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.univpm.shopgenius.services.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserServiceImpl();
	};
	
	/**
	 * Configurazione dell'autenticazione
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
	}
	
	/**
	 * Configurazione dell'autorizzazione
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().
			antMatchers("/").permitAll().
			antMatchers("/img").permitAll().
			antMatchers("/img/*").permitAll().
			antMatchers("/user/showForm").permitAll().
			antMatchers("/user/saveUser").permitAll().
			antMatchers("/login").permitAll().
			antMatchers("/home").permitAll().
			antMatchers("/searchProduct*").permitAll().
			antMatchers("/product/list").hasAnyAuthority("admin").
			antMatchers("/product/add").hasAnyAuthority("admin").
			antMatchers("/product/delete").hasAnyAuthority("admin").
			antMatchers("/product*").permitAll().
			antMatchers("/product").permitAll().
			antMatchers("/home").permitAll().
			antMatchers("/favorites/*").hasAnyAuthority("admin","user").
			antMatchers("/favorites").hasAnyAuthority("admin","user").
			antMatchers("/*").hasAnyAuthority("admin").
			antMatchers("/user/details").hasAnyAuthority("admin","user").
			antMatchers("/user/list").hasAnyAuthority("admin").
			antMatchers("/user/delete").hasAnyAuthority("admin").
				and().formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password").defaultSuccessUrl("/")
					.failureUrl("/login?error=true").permitAll().
				and().logout().logoutSuccessUrl("/")
					.invalidateHttpSession(true).permitAll().
			and().csrf().disable();
	}

}