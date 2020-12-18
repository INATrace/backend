package com.abelium.INATrace.components.company.types;

import java.util.List;
import java.util.Map;

import com.abelium.INATrace.db.entities.CompanyCertification;
import com.abelium.INATrace.db.entities.CompanyDocument;

public interface CompanyTranslatables {

	public String getName();
	public void setName(String name);

	public String getAbbreviation();
	public void setAbbreviation(String abbreviation);

	public String getAbout();
	public void setAbout(String about);

	public String getInterview();
	public void setInterview(String interview);

	public String getWebPage();
	public void setWebPage(String webPage);

	public Map<String, String> getMediaLinks();
	public void setMediaLinks(Map<String, String> mediaLinks);
	
	public List<CompanyDocument> getDocuments();
	public void setDocuments(List<CompanyDocument> documents);
	
	public List<CompanyCertification> getCertifications();
	public void setCertifications(List<CompanyCertification> certifications);
	
}
