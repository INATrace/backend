package com.abelium.INATrace.components.company.api;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.api.types.Lengths;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiCompanyCustomer extends ApiBaseEntity {
	
	@ApiModelProperty(value = "Company id", position = 1)
	public Long companyId;
	
	@ApiModelProperty(value = "Name", position = 2)
	@Length(max = Lengths.NAME)
	public String name;
	
	@ApiModelProperty(value = "Official company name", position = 3)
	@Length(max = Lengths.NAME)
	public String officialCompanyName;
	
	@ApiModelProperty(value = "Vat id", position = 4)
	@Length(max = Lengths.VAT_ID)
	public String vatId;

	@ApiModelProperty(value = "Contact", position = 5)
	@Length(max = Lengths.DEFAULT)
	public String contact;
	
	@ApiModelProperty(value = "Phone", position = 6)
	@Length(max = Lengths.PHONE_NUMBER)
	public String phone;

	@ApiModelProperty(value = "Email", position = 7)
	@Length(max = Lengths.EMAIL)
	public String email;
	
	@ApiModelProperty(value = "Location", position = 8)
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
