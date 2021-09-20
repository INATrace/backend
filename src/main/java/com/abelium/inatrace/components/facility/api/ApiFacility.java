package com.abelium.inatrace.components.facility.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ApiFacility extends ApiBaseEntity {

	@ApiModelProperty(value = "Facility name")
	private String name;

	@ApiModelProperty(value = "Is collection facility")
	private Boolean isCollectionFacility;

	@ApiModelProperty(value = "Is public facility")
	private Boolean isPublic;

	@ApiModelProperty(value = "Facility location")
	private ApiFacilityLocation facilityLocation;

	@ApiModelProperty(value = "Facility company")
	private ApiCompanyBase company;

	@ApiModelProperty(value = "Facility type")
	private ApiFacilityType facilityType;

	@ApiModelProperty(value = "List of semi product ID's for this facility")
	private List<ApiSemiProduct> facilitySemiProductList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsCollectionFacility() {
		return isCollectionFacility;
	}

	public void setIsCollectionFacility(Boolean isCollectionFacility) {
		this.isCollectionFacility = isCollectionFacility;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public ApiFacilityLocation getFacilityLocation() {
		return facilityLocation;
	}

	public void setFacilityLocation(ApiFacilityLocation facilityLocation) {
		this.facilityLocation = facilityLocation;
	}

	public ApiCompanyBase getCompany() {
		return company;
	}

	public void setCompany(ApiCompanyBase company) {
		this.company = company;
	}

	public ApiFacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(ApiFacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public List<ApiSemiProduct> getFacilitySemiProductList() {
		return facilitySemiProductList;
	}

	public void setFacilitySemiProductList(List<ApiSemiProduct> facilitySemiProductList) {
		this.facilitySemiProductList = facilitySemiProductList;
	}

	public ApiFacility() {
		super();
	}

	public ApiFacility(String name, Boolean isCollectionFacility, Boolean isPublic,
			ApiFacilityLocation facilityLocation, ApiCompanyBase company, ApiFacilityType facilityType) {
		super();
		this.name = name;
		this.isCollectionFacility = isCollectionFacility;
		this.isPublic = isPublic;
		this.facilityLocation = facilityLocation;
		this.company = company;
		this.facilityType = facilityType;
	}

}