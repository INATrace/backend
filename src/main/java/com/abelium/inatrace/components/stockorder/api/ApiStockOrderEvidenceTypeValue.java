package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.tools.converters.SimpleDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Validated
public class ApiStockOrderEvidenceTypeValue {

	@ApiModelProperty(value = "The id of the Processing evidence type from the codebook", position = 1)
	private Long evidenceTypeId;

	@ApiModelProperty(value = "The field name of the Processing evidence type from the codebook", position = 2)
	private String evidenceTypeCode;

	@ApiModelProperty(value = "The date of the document upload", position = 1)
	@JsonSerialize(converter = SimpleDateConverter.Serialize.class)
	@JsonDeserialize(using = SimpleDateConverter.Deserialize.class)
	private Instant date;

	@ApiModelProperty(value = "The attached document reference of the processing evidence", position = 4)
	private ApiDocument document;

	public Long getEvidenceTypeId() {
		return evidenceTypeId;
	}

	public void setEvidenceTypeId(Long evidenceTypeId) {
		this.evidenceTypeId = evidenceTypeId;
	}

	public String getEvidenceTypeCode() {
		return evidenceTypeCode;
	}

	public void setEvidenceTypeCode(String evidenceTypeCode) {
		this.evidenceTypeCode = evidenceTypeCode;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public ApiDocument getDocument() {
		return document;
	}

	public void setDocument(ApiDocument document) {
		this.document = document;
	}

}
