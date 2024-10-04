package com.abelium.inatrace.db.entities.company;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.GeoAddress;
import com.abelium.inatrace.db.entities.product.Product;
import jakarta.persistence.*;

@Entity
@Table
public class CompanyCustomer extends BaseEntity {

	/**
	 * product this company belongs to
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	/**
	 * company reference
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	
	/**
	 * name
	 */
	@Column(length = Lengths.NAME)
	private String name;

	/**
	 * official company name
	 */
	@Column(length = Lengths.NAME)
	private String officialCompanyName;
	
	/**
	 * vat id
	 */
	@Column(length = Lengths.VAT_ID)
	private String vatId;

	/**
	 * contact
	 */
	@Column(length = Lengths.DEFAULT)
	private String contact;
	
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
	 * location
	 */
	@Embedded
	private GeoAddress location;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOfficialCompanyName() {
		return officialCompanyName;
	}

	public void setOfficialCompanyName(String officialCompanyName) {
		this.officialCompanyName = officialCompanyName;
	}

	public String getVatId() {
		return vatId;
	}

	public void setVatId(String vatId) {
		this.vatId = vatId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public GeoAddress getLocation() {
		return location;
	}

	public void setLocation(GeoAddress location) {
		this.location = location;
	}
}
