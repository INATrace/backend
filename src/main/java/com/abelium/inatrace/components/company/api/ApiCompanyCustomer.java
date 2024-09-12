package com.abelium.inatrace.components.company.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiCompanyCustomer extends ApiBaseEntity {
	
	@Schema(description = "Company id")
	public Long companyId;
	
	@Schema(description = "Name")
	@Size(max = Lengths.NAME)
	public String name;
	
	@Schema(description = "Official company name")
	@Size(max = Lengths.NAME)
	public String officialCompanyName;
	
	@Schema(description = "Vat id")
	@Size(max = Lengths.VAT_ID)
	public String vatId;

	@Schema(description = "Contact")
	@Size(max = Lengths.DEFAULT)
	public String contact;
	
	@Schema(description = "Phone")
	@Size(max = Lengths.PHONE_NUMBER)
	public String phone;

	@Schema(description = "Email")
	@Size(max = Lengths.EMAIL)
	public String email;
	
	@Schema(description = "Location")
	@Valid
	public ApiGeoAddress location;
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public ApiGeoAddress getLocation() {
		return location;
	}

	public void setLocation(ApiGeoAddress location) {
		this.location = location;
	}
}
