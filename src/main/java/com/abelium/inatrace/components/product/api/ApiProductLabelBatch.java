package com.abelium.inatrace.components.product.api;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.common.api.ApiDocument;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiProductLabelBatch extends ApiBaseEntity {
	
	@Schema(description = "Label id")
	public Long labelId;

	@Schema(description = "Batch number")
	@Pattern(regexp = "^\\p{Alnum}*$")
	public String number;
	
	@Schema(description = "Production date")
    public LocalDate productionDate;

	@Schema(description = "Expiry date")
	public LocalDate expiryDate;
	
	@Schema(description = "batch farming location")
	@Valid
    public List<ApiLocation> locations;

	@Schema(description = "batch photo")
	public ApiDocument photo;	

	@Schema(description = "enable authenticity check")
	public Boolean checkAuthenticity;

	@Schema(description = "enable tracing origin")
	public Boolean traceOrigin;

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public List<ApiLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<ApiLocation> locations) {
		this.locations = locations;
	}

	public ApiDocument getPhoto() {
		return photo;
	}

	public void setPhoto(ApiDocument photo) {
		this.photo = photo;
	}

	public Boolean getCheckAuthenticity() {
		return checkAuthenticity;
	}

	public void setCheckAuthenticity(Boolean checkAuthenticity) {
		this.checkAuthenticity = checkAuthenticity;
	}

	public Boolean getTraceOrigin() {
		return traceOrigin;
	}

	public void setTraceOrigin(Boolean traceOrigin) {
		this.traceOrigin = traceOrigin;
	}
}
