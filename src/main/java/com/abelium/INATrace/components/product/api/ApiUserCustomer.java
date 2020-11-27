package com.abelium.INATrace.components.product.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.types.Gender;
import com.abelium.INATrace.types.UserCustomerType;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiUserCustomer extends ApiBaseEntity {

	@ApiModelProperty(value = "Company id", position = 1)
	public Long companyId;	
	
	@ApiModelProperty(value = "Type", position = 2)
	public UserCustomerType type;
	
	@ApiModelProperty(value = "Name", position = 3)
	@Length(max = Lengths.NAME)
	public String name;
	
	@ApiModelProperty(value = "Surname", position = 4)
	@Length(max = Lengths.SURNAME)
	public String surname;
	
	@ApiModelProperty(value = "Phone", position = 5)
	@Length(max = Lengths.PHONE_NUMBER)
	public String phone;

	@ApiModelProperty(value = "Email", position = 6)
	@Length(max = Lengths.EMAIL)
	public String email;
	
	@ApiModelProperty(value = "Location", position = 7)
	@Length(max = Lengths.DEFAULT)
	public String location;
	
	@ApiModelProperty(value = "Gender", position = 8)
	public Gender gender;
	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public UserCustomerType getType() {
		return type;
	}

	public void setType(UserCustomerType type) {
		this.type = type;
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
