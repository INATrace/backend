package com.abelium.inatrace.components.analytics.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.RequestLogType;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiLogRequest {

	@Schema(description = "request token")
	public String token;

	@NotNull
	@Schema(description = "type")
	public RequestLogType type;
	
	@Size(max = Lengths.REQUEST_LOG_KEY)
	@Schema(description = "log key to store (max 64 chars)")
	public String logKey;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "value 1 (max 255 chars)")
	public String value1;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "value 2 (max 255 chars)")
	public String value2;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RequestLogType getType() {
		return type;
	}

	public void setType(RequestLogType type) {
		this.type = type;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = logKey;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
}
	
