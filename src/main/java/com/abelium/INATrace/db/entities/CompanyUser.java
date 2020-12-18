package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.types.CompanyUserRole;

@Entity
public class CompanyUser extends BaseEntity {
	
	@Version
	private long entityVersion;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private CompanyUserRole role;
	
	@ManyToOne
	@NotNull
	private User user;
	
	@ManyToOne
	@NotNull
	private Company company;

	public CompanyUserRole getRole() {
		return role;
	}

	public void setRole(CompanyUserRole role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}
