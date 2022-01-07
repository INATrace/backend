package com.abelium.inatrace.security.service;

import com.abelium.inatrace.components.user.UserService;
import com.abelium.inatrace.db.entities.common.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	
    @Autowired
    private UserService userEngine;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userEngine.fetchUserByEmail(s);
        if (user == null) {
        	throw new UsernameNotFoundException("User '" + s + "' not found");
        }
        return new CustomUserDetails(user);
    }
}
