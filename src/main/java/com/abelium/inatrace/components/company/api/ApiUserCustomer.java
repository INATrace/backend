package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.product.api.ApiBankInformation;
import com.abelium.inatrace.components.product.api.ApiFarmInformation;
import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.types.Gender;
import com.abelium.inatrace.types.UserCustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class ApiUserCustomer extends ApiBaseEntity {

	@Schema(description = "Company id")
	public Long companyId;

	@Schema(description = "Company internal farmer ID")
	public String farmerCompanyInternalId;
	
	@Schema(description = "Type")
	public UserCustomerType type;
	
	@Schema(description = "Name")
	@Size(max = Lengths.NAME)
	public String name;
	
	@Schema(description = "Surname")
	@Size(max = Lengths.SURNAME)
	public String surname;
	
	@Schema(description = "Phone")
	@Size(max = Lengths.PHONE_NUMBER)
	public String phone;

	@Schema(description = "Email")
	@Size(max = Lengths.EMAIL)
	public String email;

	@Schema(description = "Has smartphone")
	public Boolean hasSmartphone;
	
	@Schema(description = "Location")
	public ApiUserCustomerLocation location;
	
	@Schema(description = "Gender")
	public Gender gender;

	@Schema(description = "Bank information")
	public ApiBankInformation bank;

	@Schema(description = "Farm information")
	public ApiFarmInformation farm;

	@Schema(description = "List of associations")
	public List<ApiUserCustomerAssociation> associations;

	@Schema(description = "List of cooperatives")
	public List<ApiUserCustomerCooperative> cooperatives;

	@Schema(description = "User customer certifications")
	@Valid
	public List<ApiCertification> certifications;

	@Schema(description = "User customer product types")
	@Valid
	public List<ApiProductType> productTypes;

	@Schema(description = "Plots in possession of the user customer (only applicable for type FARMER)")
	@Valid
	public List<ApiPlot> plots;

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

	public List<ApiPlot> getPlots() {
		return plots;
	}

	public void setPlots(List<ApiPlot> plots) {
		this.plots = plots;
	}

}
