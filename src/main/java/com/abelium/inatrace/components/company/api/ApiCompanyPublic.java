package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.common.api.ApiCertification;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
public class ApiCompanyPublic {

	@ApiModelProperty(value = "name", position = 1)
	public String name;

	@ApiModelProperty(value = "interview", position = 2)
	public String interview;

	@ApiModelProperty(value = "company documents", position = 3)
	@Valid
	public List<ApiCompanyDocument> documents;	

	@ApiModelProperty(value = "company certifications", position = 4)
	@Valid
	public List<ApiCertification> certifications;
	
	@ApiModelProperty(value = "about the company", position = 5)
	public String about;
	
	@ApiModelProperty(value = "social media URL links (Facebook, Instagram, Twitter, YouTube, ...)", position = 6)
	public Map<String, String> mediaLinks;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterview() {
		return interview;
	}

	public void setInterview(String interview) {
		this.interview = interview;
	}

	public List<ApiCompanyDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<ApiCompanyDocument> documents) {
		this.documents = documents;
	}

	public List<ApiCertification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<ApiCertification> certifications) {
		this.certifications = certifications;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Map<String, String> getMediaLinks() {
		return mediaLinks;
	}

	public void setMediaLinks(Map<String, String> mediaLinks) {
		this.mediaLinks = mediaLinks;
	}
}
