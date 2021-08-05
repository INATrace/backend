package com.abelium.inatrace.components.user.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.Language;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class ApiCreateUserRequest {
	
    @NotNull
	@Length(max = Lengths.EMAIL)
	@Email
    @ApiModelProperty(required = true, value = "Email (username).", position = 0)
	public String email;
	
    @NotNull
    @Length(max = Lengths.PASSWORD)
    @ApiModelProperty(required = true, value = "Password.", position = 1)
    public String password = null;

    @NotNull
    @Length(max = Lengths.NAME)
    @ApiModelProperty(required = true, value = "Name.", position = 2)
    public String name = null;

    @NotNull
    @Length(max = Lengths.SURNAME)
    @ApiModelProperty(required = true, value = "Surname.", position = 3)
    public String surname = null;
    
	@ApiModelProperty(value = "language", position = 4)
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
