package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Map;

public class ApiCompanyBase extends ApiBaseEntity {

	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "company name", position = 2)
	public String name;

	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "company abbreviation", position = 3)
	public String abbreviation;	
	
	@ApiModelProperty(value = "headquarters", position = 4)
	@Valid
	public ApiAddress headquarters;

	@Size(max = 2000)
	@ApiModelProperty(value = "about the company", position = 5)
	public String about;
	
	@Size(max = Lengths.DEFAULT)
	@ApiModelProperty(value = "name of manager / CEO", position = 6)
	public String manager;
	
	@Size(max = Lengths.URL_PATH)
	@ApiModelProperty(value = "webpage", position = 7)
	public String webPage;

	@ApiModelProperty(value = "Display preferred way of payment on purchase order form")
	public Boolean displayPrefferedWayOfPayment;

	@ApiModelProperty(value = "Enable adding multiple farmers for one proof document on purchase order form")
	public Boolean purchaseProofDocumentMultipleFarmers;
	
	@Size(max = Lengths.EMAIL)
	@Email
	@ApiModelProperty(value = "email", position = 8)
	public String email;
	
	@Size(max = Lengths.PHONE_NUMBER)
	@ApiModelProperty(value = "webpage", position = 9)
	public String phone;
	
	@ApiModelProperty(value = "social media URL links (Facebook, Instagram, Twitter, YouTube, ...)", position = 10)
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
