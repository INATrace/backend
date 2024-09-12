package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.company.api.ApiCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Validated
public class ApiProductLabelContent extends ApiProductContent {

	@Schema(description = "company")
	@Valid
	public ApiCompany company;
	
	@Schema(description = "label id")
	public Long labelId;
    
    @Schema(description = "Product journey path")
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
