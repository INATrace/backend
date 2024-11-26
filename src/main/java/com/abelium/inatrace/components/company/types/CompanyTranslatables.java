package com.abelium.inatrace.components.company.types;

import com.abelium.inatrace.db.entities.company.CompanyCertification;
import com.abelium.inatrace.db.entities.company.CompanyDocument;

import java.util.Map;
import java.util.Set;

public interface CompanyTranslatables {

	String getName();
	void setName(String name);

	String getAbbreviation();
	void setAbbreviation(String abbreviation);

	String getAbout();
	void setAbout(String about);

	String getInterview();
	void setInterview(String interview);

	String getWebPage();
	void setWebPage(String webPage);

	Map<String, String> getMediaLinks();
	void setMediaLinks(Map<String, String> mediaLinks);
	
	Set<CompanyDocument> getDocuments();
	void setDocuments(Set<CompanyDocument> documents);
	
	Set<CompanyCertification> getCertifications();
	void setCertifications(Set<CompanyCertification> certifications);
	
}
