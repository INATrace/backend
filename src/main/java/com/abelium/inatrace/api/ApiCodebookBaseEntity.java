package com.abelium.inatrace.api;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Codebook base entity API model.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiCodebookBaseEntity extends ApiBaseEntity {

	@ApiModelProperty(value = "code", position = 1)
	@NotNull
	private String code;

	@ApiModelProperty(value = "label", position = 2)
	@NotNull
	private String label;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
