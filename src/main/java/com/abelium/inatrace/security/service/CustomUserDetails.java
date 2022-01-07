package com.abelium.inatrace.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.types.UserRole;
import java.util.Arrays;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long dbId;
	private String email;
	private String name;
	private String surname;
	private UserRole role;
	
	
    public CustomUserDetails(Long dbId, String email, String name, String surname, UserRole role) {
		this.dbId = dbId;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.role = role;
    }
    
    public CustomUserDetails(User user) {
		this.dbId = user.getId();
		this.email = user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.role = user.getRole();
	}

	public Long getUserId() { 
    	return dbId;
    }
    
    public UserRole getUserRole() { 
    	return role;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getSurname() {
    	return surname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(role);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
