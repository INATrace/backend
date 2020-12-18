package com.abelium.INATrace.components.product.api;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiBaseEntity;
import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiProductLabelBatchCheckAuthenticity extends ApiBaseEntity {

	@NotNull
	@ApiModelProperty(value = "Batch number", position = 1)
	@Pattern(regexp = "^[\\p{Alnum}]*$")
	public String number;
	
	@ApiModelProperty(value = "Production date", position = 3)
    public LocalDate productionDate;

	@ApiModelProperty(value = "Expiry date", position = 4)
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
