package com.abelium.inatrace.components.user.api;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiUserUpdate {

	@Size(max = Lengths.NAME)
	@Schema(description = "Name")
	public String name;

	@Size(max = Lengths.SURNAME)
	@Schema(description = "Surname")
	public String surname;
	
	@Schema(description = "language")
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
