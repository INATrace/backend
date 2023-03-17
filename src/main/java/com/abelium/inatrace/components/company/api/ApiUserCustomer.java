package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.product.api.ApiBankInformation;
import com.abelium.inatrace.components.product.api.ApiFarmInformation;
import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.types.Gender;
import com.abelium.inatrace.types.UserCustomerType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public class ApiUserCustomer extends ApiBaseEntity {

	@ApiModelProperty(value = "Company id", position = 1)
	public Long companyId;

	@ApiModelProperty(value = "Company internal farmer ID")
	public String farmerCompanyInternalId;
	
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

	@ApiModelProperty(value = "Has smartphone", position = 7)
	public Boolean hasSmartphone;
	
	@ApiModelProperty(value = "Location", position = 8)
	public ApiUserCustomerLocation location;
	
	@ApiModelProperty(value = "Gender", position = 9)
	public Gender gender;

	@ApiModelProperty(value = "Bank information", position = 10)
	public ApiBankInformation bank;

	@ApiModelProperty(value = "Farm information", position = 11)
	public ApiFarmInformation farm;

	@ApiModelProperty(value = "List of associations", position = 12)
	public List<ApiUserCustomerAssociation> associations;

	@ApiModelProperty(value = "List of cooperatives", position = 13)
	public List<ApiUserCustomerCooperative> cooperatives;

	@ApiModelProperty(value = "User customer certifications", position = 14)
	@Valid
	public List<ApiCertification> certifications;

	@ApiModelProperty(value = "User customer product types", position = 15)
	@Valid
	public List<ApiProductType> productTypes;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getFarmerCompanyInternalId() {
		return farmerCompanyInternalId;
	}

	public void setFarmerCompanyInternalId(String farmerCompanyInternalId) {
		this.farmerCompanyInternalId = farmerCompanyInternalId;
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

	public Boolean getHasSmartphone() {
		return hasSmartphone;
	}

	public void setHasSmartphone(Boolean hasSmartphone) {
		this.hasSmartphone = hasSmartphone;
	}

	public ApiUserCustomerLocation getLocation() {
		return location;
	}

	public void setLocation(ApiUserCustomerLocation location) {
		this.location = location;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public ApiBankInformation getBank() {
		return bank;
	}

	public void setBank(ApiBankInformation bank) {
		this.bank = bank;
	}

	public ApiFarmInformation getFarm() {
		return farm;
	}

	public void setFarm(ApiFarmInformation farm) {
		this.farm = farm;
	}

	public List<ApiUserCustomerAssociation> getAssociations() {
		return associations;
	}

	public void setAssociations(List<ApiUserCustomerAssociation> associations) {
		this.associations = associations;
	}

	public List<ApiUserCustomerCooperative> getCooperatives() {
		return cooperatives;
	}

	public void setCooperatives(List<ApiUserCustomerCooperative> cooperatives) {
		this.cooperatives = cooperatives;
	}

	public List<ApiCertification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<ApiCertification> certifications) {
		this.certifications = certifications;
	}

	public List<ApiProductType> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(List<ApiProductType> productTypes) {
		this.productTypes = productTypes;
	}
}
