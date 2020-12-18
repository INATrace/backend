package com.abelium.INATrace.components.product.api;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.components.common.api.ApiDocument;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelBatch extends ApiBaseEntity {
	
	@ApiModelProperty(value = "Label id", position = 1)
	public Long labelId;

	@ApiModelProperty(value = "Batch number", position = 2)
	@Pattern(regexp = "^[\\p{Alnum}]*$")
	public String number;
	
	@ApiModelProperty(value = "Production date", position = 3)
    public LocalDate productionDate;

	@ApiModelProperty(value = "Expiry date", position = 4)
	public LocalDate expiryDate;
	
	@ApiModelProperty(value = "batch farming location", position = 5)
	@Valid
    public List<ApiLocation> locations;

	@ApiModelProperty(value = "batch photo", position = 6)
	public ApiDocument photo;	

	@ApiModelProperty(value = "enable authenticity check", position = 7)
	public Boolean checkAuthenticity;

	@ApiModelProperty(value = "enable tracing origin", position = 8)
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
