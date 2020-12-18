package com.abelium.INATrace.types;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    
	USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("USER"),
    ACCOUNTANT("USER");
	
	private String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
    
}

