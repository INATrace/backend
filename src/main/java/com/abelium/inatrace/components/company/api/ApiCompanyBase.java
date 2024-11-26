package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Map;

public class ApiCompanyBase extends ApiBaseEntity {

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "company name", maxLength = Lengths.DEFAULT)
	public String name;

	@Size(max = Lengths.DEFAULT)
	@Schema(description = "company abbreviation", maxLength = Lengths.DEFAULT)
	public String abbreviation;	
	
	@Schema(description = "headquarters")
	@Valid
	public ApiAddress headquarters;

	@Size(max = 2000)
	@Schema(description = "about the company", maxLength = 2000)
	public String about;
	
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "name of manager / CEO")
	public String manager;
	
	@Size(max = Lengths.URL_PATH)
	@Schema(description = "webpage", maxLength = Lengths.URL_PATH)
	public String webPage;

	@Schema(description = "Display preferred way of payment on purchase order form")
	public Boolean displayPrefferedWayOfPayment;

	@Schema(description = "Enable adding multiple farmers for one proof document on purchase order form")
	public Boolean purchaseProofDocumentMultipleFarmers;
	
	@Size(max = Lengths.EMAIL)
	@Email
	@Schema(description = "email", maxLength = Lengths.EMAIL)
	public String email;
	
	@Size(max = Lengths.PHONE_NUMBER)
	@Schema(description = "webpage", maxLength = Lengths.PHONE_NUMBER)
	public String phone;
	
	@Schema(description = "social media URL links (Facebook, Instagram, Twitter, YouTube, ...)")
	public Map<String, String> mediaLinks;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public ApiAddress getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(ApiAddress headquarters) {
		this.headquarters = headquarters;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getWebPage() {
		return webPage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public Map<String, String> getMediaLinks() {
		return mediaLinks;
	}

	public void setMediaLinks(Map<String, String> mediaLinks) {
		this.mediaLinks = mediaLinks;
	}
	
	
}
