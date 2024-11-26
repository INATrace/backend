package com.abelium.inatrace.components.company.api;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.types.Language;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiCompanyUpdate extends ApiCompany {

	@Schema(description = "Add users with these ids")
	public List<ApiBaseEntity> users;
	
	@Schema(description = "Language")
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
