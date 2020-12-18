package com.abelium.INATrace.components.company.api;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.types.Language;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiCompanyUpdate extends ApiCompany {

	@ApiModelProperty(value = "Add users with these ids", position = 12)
	public List<ApiBaseEntity> users;
	
	@ApiModelProperty(value = "Language", position = 13)
	public Language language = Language.EN;

	public List<ApiBaseEntity> getUsers() {
		return users;
	}

	public void setUsers(List<ApiBaseEntity> users) {
		this.users = users;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
