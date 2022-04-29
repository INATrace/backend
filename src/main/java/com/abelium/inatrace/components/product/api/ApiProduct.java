package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public class ApiProduct extends ApiProductContent {

	@ApiModelProperty(value = "company", position = 14)
	public ApiCompany company;
	
	@ApiModelProperty(value = "associated companies", position = 14)
	@Valid
	public List<ApiProductCompany> associatedCompanies;
	
	@ApiModelProperty(value = "labels", position = 15)
	public List<ApiProductLabelValues> labels;

	@ApiModelProperty(value = "Value chain")
	public ApiValueChain valueChain;

	@ApiModelProperty(value = "Data sharing agreements")
	private List<ApiProductDataSharingAgreement> dataSharingAgreements;
    
    @ApiModelProperty(value = "Product journey path")
    private List<ApiProductJourneyMarker> journeyMarkers;

	public ApiCompany getCompany() {
		return company;
	}

	public void setCompany(ApiCompany company) {
		this.company = company;
	}

	public List<ApiProductCompany> getAssociatedCompanies() {
		return associatedCompanies;
	}

	public void setAssociatedCompanies(List<ApiProductCompany> associatedCompanies) {
		this.associatedCompanies = associatedCompanies;
	}

	public List<ApiProductLabelValues> getLabels() {
		return labels;
	}

	public void setLabels(List<ApiProductLabelValues> labels) {
		this.labels = labels;
	}

	public ApiValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ApiValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public List<ApiProductDataSharingAgreement> getDataSharingAgreements() {
		return dataSharingAgreements;
	}

	public void setDataSharingAgreements(List<ApiProductDataSharingAgreement> dataSharingAgreements) {
		this.dataSharingAgreements = dataSharingAgreements;
	}
    
    public List<ApiProductJourneyMarker> getJourneyMarkers() {
        return journeyMarkers;
    }
    
    public void setJourneyMarkers(List<ApiProductJourneyMarker> journeyMarkers) {
        this.journeyMarkers = journeyMarkers;
    }
}
