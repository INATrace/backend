package com.abelium.inatrace.components.codebook.facility.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.product.api.ApiLocation;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
public class ApiFacility extends ApiBaseEntity {

  @ApiModelProperty(value = "") // TODO: not sure what values I should add here and for consequent fields too -
                                // Rene
  private String name;

  @ApiModelProperty(value = "")
  private Boolean isCollectionFacility;

  @ApiModelProperty(value = "")
  private Boolean isPublic;

  @ApiModelProperty(value = "")
  private ApiLocation location;

  @ApiModelProperty(value = "")
  private List<ApiFacilityCompany> companies = new ArrayList<>();

  @ApiModelProperty(value = "")
  private List<ApiFacilityType> facilityTypes = new ArrayList<>();

  @ApiModelProperty(value = "")
  private List<ApiSemiProduct> facilitySemiProducts = new ArrayList<>();

}
