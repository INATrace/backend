package com.abelium.inatrace.components.common.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;

@Validated
public class ApiGlobalSettingsValue {

    @Schema(description = "Global settings value")
    @Size(max = 1000)
    public String value;

    @Schema(description = "Can be accessed via public api")
    public Boolean isPublic;
    
    public ApiGlobalSettingsValue() {}

    public ApiGlobalSettingsValue(String value) {
		this.value = value;    	
    }
    
    public ApiGlobalSettingsValue(String value, Boolean isPublic) {
		this.value = value;
		this.isPublic = isPublic;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
}
