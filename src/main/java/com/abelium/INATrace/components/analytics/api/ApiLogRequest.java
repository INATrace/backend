package com.abelium.INATrace.components.analytics.api;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.types.RequestLogType;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiLogRequest {

	@ApiModelProperty(value = "request token", position = 0)
	public String token;

	@NotNull
	@ApiModelProperty(value = "type", position = 1)
	public RequestLogType type;
	
	@Length(max = Lengths.REQUEST_LOG_KEY)
	@ApiModelProperty(value = "log key to store (max 64 chars)", position = 2)
	public String logKey;

	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "value 1 (max 255 chars)", position = 3)
	public String value1;

	@Length(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "value 2 (max 255 chars)", position = 4)
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
	
