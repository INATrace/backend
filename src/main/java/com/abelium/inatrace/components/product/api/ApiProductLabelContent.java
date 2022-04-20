package com.abelium.inatrace.components.product.api;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.components.company.api.ApiCompany;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Validated
public class ApiProductLabelContent extends ApiProductContent {

	@ApiModelProperty(value = "company", position = 14)
	@Valid
	public ApiCompany company;
	
	@ApiModelProperty(value = "label id", position = 21)
	public Long labelId;
    
    @ApiModelProperty(value = "Product journey path")
    private List<ApiProductJourneyMarker> journeyMarkers;

	public ApiCompany getCompany() {
		return company;
	}

	public void setCompany(ApiCompany company) {
		this.company = company;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
    
    public List<ApiProductJourneyMarker> getJourneyMarkers() {
        return journeyMarkers;
    }
    
    public void setJourneyMarkers(List<ApiProductJourneyMarker> journeyMarkers) {
        this.journeyMarkers = journeyMarkers;
    }
}
