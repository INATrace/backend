package com.abelium.inatrace.components.user.api;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Validated
public class ApiCreateUserRequest {
	
    @NotNull
	@Size(max = Lengths.EMAIL)
	@Email
    @Schema(description = "Email (username).", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = Lengths.EMAIL)
	public String email;
	
    @NotNull
    @Size(max = Lengths.PASSWORD)
    @Schema(description = "Password.", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = Lengths.PASSWORD)
    public String password = null;

    @NotNull
    @Size(max = Lengths.NAME)
    @Schema(description = "Name.", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = Lengths.NAME)
    public String name = null;

    @NotNull
    @Size(max = Lengths.SURNAME)
    @Schema(description = "Surname.", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = Lengths.SURNAME)
    public String surname = null;
    
	@Schema(description = "language")
	public Language language = Language.EN;
    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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
