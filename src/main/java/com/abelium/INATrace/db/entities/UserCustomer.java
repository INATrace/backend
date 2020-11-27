package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.types.Gender;
import com.abelium.INATrace.types.UserCustomerType;

@Entity
//@Table(indexes = { @Index(columnList = "product_id, phone", unique = true) } )
public class UserCustomer extends BaseEntity {

	/**
	 * product this collector belongs to
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	/**
	 * company reference
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	
	/**
	 * type (collector, farmer)
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private UserCustomerType type;	
	
	/**
	 * name
	 */
	@Column(length = Lengths.NAME)
	private String name;

	/**
	 * surname
	 */
	@Column(length = Lengths.SURNAME)
	private String surname;
	
	/**
	 * phone number
	 */
	@Column(length = Lengths.PHONE_NUMBER)
	private String phone;
	
	/**
	 * email
	 */
	@Column(length = Lengths.EMAIL)
	private String email;

	/**
	 * location (text description)
	 */
	@Column(length = Lengths.DEFAULT)
	private String location;
	
	/**
	 * gender
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private Gender gender;

	
	public UserCustomerType getType() {
		return type;
	}

	public void setType(UserCustomerType type) {
		this.type = type;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}	
}