package it.univpm.shopgenius.utils;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utilities {

	public Utilities() {
		
	}
	
	public String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return currentPrincipalName;
	}
		
	public String getCurrentUserMajorRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<SimpleGrantedAuthority> auth = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
		for (SimpleGrantedAuthority r: auth) {
			if (r.getAuthority().equals("admin")) {
				return "admin";
			} else if (r.getAuthority().equals("user")) {
				return "user";
			}
		}
		return "anonymous";
	}
}

//try {
//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	Collection<SimpleGrantedAuthority> auth = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
//	for (SimpleGrantedAuthority r: auth) {
//		if (r.getAuthority().equals("admin")) {
//			return "admin";
//		} else if (r.getAuthority().equals("user")) {
//			return "user";
//		}
//	}
//	return "anonymous";
//} catch (Exception e) {
//return "anonymous";
//}
//}