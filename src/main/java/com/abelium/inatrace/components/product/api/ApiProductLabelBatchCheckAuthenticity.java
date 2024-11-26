package com.abelium.inatrace.components.product.api;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiProductLabelBatchCheckAuthenticity extends ApiBaseEntity {

	@NotNull
	@Schema(description = "Batch number")
	@Pattern(regexp = "^[\\p{Alnum}]*$")
	public String number;
	
	@Schema(description = "Production date")
    public LocalDate productionDate;

	@Schema(description = "Expiry date")
	public LocalDate expiryDate;
	

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

}
