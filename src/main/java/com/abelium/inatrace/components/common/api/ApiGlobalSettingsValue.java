package com.abelium.inatrace.components.common.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiGlobalSettingsValue {

    @ApiModelProperty(value = "Global settings value", position = 0)
    @Length(max = 1000)
    public String value;

    @ApiModelProperty(value = "Can be accessed via public api", position = 1)
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
