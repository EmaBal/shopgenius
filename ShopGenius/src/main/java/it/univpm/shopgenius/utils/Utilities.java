package it.univpm.shopgenius.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		List<String> authList = new ArrayList<String>();
		for (SimpleGrantedAuthority r: auth) {
			authList.add(r.getAuthority());
		}
		if (authList.contains("admin")) {
			return "admin";
			} else if (authList.contains("employee")) {
				return "employee";
				}  else if (authList.contains("user")) {
					return "user";
				}
		return "anonymous";
	}

}