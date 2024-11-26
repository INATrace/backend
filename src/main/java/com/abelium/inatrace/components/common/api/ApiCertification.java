package com.abelium.inatrace.components.common.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
public class ApiCertification extends ApiBaseEntity {
	
	@Schema(description = "certification type")
	public String type;
	
	@Schema(description = "certificate for this standard")
	@Valid
	public ApiDocument certificate;
	
	@Size(max = 2000)
	@Schema(description = "description of this standard and certification", maxLength = 2000)
	public String description;
	
	@Schema(description = "validity")
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
