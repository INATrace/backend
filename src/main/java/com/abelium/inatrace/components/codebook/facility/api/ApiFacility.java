package com.abelium.inatrace.components.codebook.facility.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.product.api.ApiLocation;

import io.swagger.annotations.ApiModelProperty;

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
  private ApiFacilityCompany company;

  @ApiModelProperty(value = "Facility type")
  private ApiFacilityType facilityType;

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

  public ApiLocation getFacilityLocation() {
    return facilityLocation;
  }

  public void setFacilityLocation(ApiFacilityLocation facilityLocation) {
    this.facilityLocation = facilityLocation;
  }

  public ApiFacilityCompany getCompany() {
    return company;
  }

  public void setCompany(ApiFacilityCompany company) {
    this.company = company;
  }

  public ApiFacilityType getFacilityType() {
    return facilityType;
  }

  public void setFacilityType(ApiFacilityType facilityType) {
    this.facilityType = facilityType;
  }

  public ApiFacility() {
    super();
  }

  public ApiFacility(String name, Boolean isCollectionFacility, Boolean isPublic, ApiFacilityLocation facilityLocation,
      ApiFacilityCompany company, ApiFacilityType facilityType) {
    super();
    this.name = name;
    this.isCollectionFacility = isCollectionFacility;
    this.isPublic = isPublic;
    this.facilityLocation = facilityLocation;
    this.company = company;
    this.facilityType = facilityType;
  }

}
