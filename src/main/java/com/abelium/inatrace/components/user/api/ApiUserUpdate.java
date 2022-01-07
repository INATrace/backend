package com.abelium.inatrace.components.user.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.Language;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiUserUpdate {

	@Length(max = Lengths.NAME)
	@ApiModelProperty(value = "Name", position = 1)
	public String name;
	
	@Length(max = Lengths.SURNAME)
	@ApiModelProperty(value = "Surname", position = 2)
	public String surname;
	
	@ApiModelProperty(value = "language", position = 3)
	public Language language = Language.EN;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
