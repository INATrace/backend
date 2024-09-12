package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.common.api.ApiCertification;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
public class ApiCompanyPublic {

	@Schema(description = "name")
	public String name;

	@Schema(description = "interview")
	public String interview;

	@Schema(description = "company documents")
	@Valid
	public List<ApiCompanyDocument> documents;	

	@Schema(description = "company certifications")
	@Valid
	public List<ApiCertification> certifications;
	
	@Schema(description = "about the company")
	public String about;
	
	@Schema(description = "social media URL links (Facebook, Instagram, Twitter, YouTube, ...)")
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
