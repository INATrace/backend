package com.abelium.INATrace.components.common.api;

import java.time.LocalDate;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiCertification {
	
	@ApiModelProperty(value = "certification type", position = 1)
	public String type;
	
	@ApiModelProperty(value = "certificate for this standard", position = 2)
	@Valid
	public ApiDocument certificate;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "description of this standard and certification", position = 3)
	public String description;
	
	@ApiModelProperty(value = "validity", position = 4)
	public LocalDate validity;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ApiDocument getCertificate() {
		return certificate;
	}

	public void setCertificate(ApiDocument certificate) {
		this.certificate = certificate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getValidity() {
		return validity;
	}

	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}
}
