package com.abelium.inatrace.types;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    
	USER("USER"),
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
	REGIONAL_ADMIN("REGIONAL_ADMIN");
	
	private final String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
    
}
