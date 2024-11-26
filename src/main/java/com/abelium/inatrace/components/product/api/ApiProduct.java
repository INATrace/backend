package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class ApiProduct extends ApiProductContent {

	@Schema(description = "company")
	public ApiCompany company;
	
	@Schema(description = "associated companies")
	@Valid
	public List<ApiProductCompany> associatedCompanies;
	
	@Schema(description = "labels")
	public List<ApiProductLabelValues> labels;

	@Schema(description = "Value chain")
	public ApiValueChain valueChain;

	@Schema(description = "Data sharing agreements")
	private List<ApiProductDataSharingAgreement> dataSharingAgreements;
    
    @Schema(description = "Product journey path")
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
