package com.abelium.inatrace.components.common.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

/**
 * ActivityProof API model object.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Validated
public class ApiActivityProof extends ApiBaseEntity {

	@ApiModelProperty(value = "The formal creation date of the document", position = 1)
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	private Instant formalCreationDate;

	@ApiModelProperty(value = "Date until the document is valid", position = 2)
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	private Instant validUntil;

	@ApiModelProperty(value = "The type of the activity proof", position = 3)
	private String type;

	@ApiModelProperty(value = "The attached document reference of the activity proof", position = 4)
	private ApiDocument document;

	public Instant getFormalCreationDate() {
		return formalCreationDate;
	}

	public void setFormalCreationDate(Instant formalCreationDate) {
		this.formalCreationDate = formalCreationDate;
	}

	public Instant getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Instant validUntil) {
		this.validUntil = validUntil;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ApiDocument getDocument() {
		return document;
	}

	public void setDocument(ApiDocument document) {
		this.document = document;
	}

}
