package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.product.Product;
import com.abelium.inatrace.types.Gender;
import com.abelium.inatrace.types.UserCustomerType;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
	@NamedQuery(name = "UserCustomer.getUserCustomerByCompanyIdAndType", query = "SELECT u FROM UserCustomer u WHERE u.company.id = :companyId AND u.type = :type"),
	@NamedQuery(name = "UserCustomer.getUserCustomerById", query = "SELECT u FROM UserCustomer u WHERE u.id = :userCustomerId")
})
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
	 * company farmer internal id
	 */
	@Column(length = Lengths.DEFAULT)
	private String farmerCompanyInternalId;
	
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

	@Column
	private Boolean hasSmartphone;

	/**
	 * location (text description)
	 */
	@Column(length = Lengths.DEFAULT)
	private String location;

	@OneToOne
	private UserCustomerLocation userCustomerLocation;
	
	/**
	 * gender
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private Gender gender;

	@Column
	private BankInformation bank;

	@Column
	private FarmInformation farm;

	@OneToMany(mappedBy = "userCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserCustomerAssociation> associations;

	@OneToMany(mappedBy = "userCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserCustomerCooperative> cooperatives;
	
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

	public String getFarmerCompanyInternalId() {
		return farmerCompanyInternalId;
	}

	public void setFarmerCompanyInternalId(String farmerCompanyInternalId) {
		this.farmerCompanyInternalId = farmerCompanyInternalId;
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

	public Boolean getHasSmartphone() {
		return hasSmartphone;
	}

	public void setHasSmartphone(Boolean hasSmartphone) {
		this.hasSmartphone = hasSmartphone;
	}

	public UserCustomerLocation getUserCustomerLocation() {
		return userCustomerLocation;
	}

	public void setUserCustomerLocation(UserCustomerLocation userCustomerLocation) {
		this.userCustomerLocation = userCustomerLocation;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public BankInformation getBank() {
		return bank;
	}

	public void setBank(BankInformation bank) {
		this.bank = bank;
	}

	public FarmInformation getFarm() {
		return farm;
	}

	public void setFarm(FarmInformation farm) {
		this.farm = farm;
	}

	public List<UserCustomerAssociation> getAssociations() {
		return associations;
	}

	public void setAssociations(List<UserCustomerAssociation> associations) {
		this.associations = associations;
	}

	public List<UserCustomerCooperative> getCooperatives() {
		return cooperatives;
	}

	public void setCooperatives(List<UserCustomerCooperative> cooperatives) {
		this.cooperatives = cooperatives;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
