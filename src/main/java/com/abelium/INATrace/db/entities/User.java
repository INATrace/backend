package com.abelium.INATrace.db.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.GrantedAuthority;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.TimestampEntity;
import com.abelium.INATrace.types.Language;
import com.abelium.INATrace.types.UserRole;
import com.abelium.INATrace.types.UserStatus;

@Entity
@Audited
@Table(indexes = { @Index(columnList = "status"), @Index(columnList = "email", unique = true) })
public class User extends TimestampEntity {

	@Version
	private long entityVersion;
	
    /**
     * Email - username
     */
    @Column(unique = true, length = Lengths.EMAIL)
    private String email;

	/**
	 * password
	 */
	@NotAudited
	private String password;    
    
	/**
	 * status - unconfirmed first, confirmed_email after email confirmation, 
	 * active after admin activation, deactivated
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM, nullable = false)
	private UserStatus status = UserStatus.UNCONFIRMED;

	/**
	 * name -> to profile?
	 */
	@Column(length = Lengths.NAME)
	private String name;

	/**
	 * surname -> to profile?
	 */
	@Column(length = Lengths.SURNAME)
	private String surname;
	
	/**
	 * language
	 */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
	private Language language = Language.EN;

	/**
	 * user role
	 */
    @Enumerated(EnumType.STRING)
    @Column(length = Lengths.ENUM)
	private UserRole role = UserRole.USER;


    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(getRole());
	}
    
}