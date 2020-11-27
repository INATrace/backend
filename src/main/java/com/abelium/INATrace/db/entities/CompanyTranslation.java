package com.abelium.INATrace.db.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.components.company.types.CompanyTranslatables;
import com.abelium.INATrace.db.base.TranslatedEntity;
import com.abelium.INATrace.types.Language;

@Entity
@Table(indexes = { @Index(columnList = "company_id, language", unique = true) })
public class CompanyTranslation extends TranslatedEntity implements CompanyTranslatables {

	protected CompanyTranslation() {
	}
	
	public CompanyTranslation(Language language) {
		super(language);
	}
	
	/**
	 * company this translation applies to
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	
	/**
	 * company name
	 */
	@Column(length = Lengths.DEFAULT)
	private String name;
	
	/**
	 * company abbreviation
	 */
	@Column(length = Lengths.DEFAULT)
	private String abbreviation;
	
	/**
	 * about the company - briefly describe your company and its history. 
	 * Include the company mission and vision as well as core values. (max 2000 characters)
	 */
	@Lob
	private String about;
	
	/**
	 * interview - 
	 * (max 2000 characters)
	 */
	@Lob
	private String interview;
	
	/**
	 * webpage
	 */
	@Column(length = Lengths.URL_PATH)
	private String webPage;

	/**
	 * social media URL links (Facebook, Instagram, Twitter, YouTube, ...)
	 */
	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "medium")
    @Column(name = "link")	
    @CollectionTable
    private Map<String, String> mediaLinks = new HashMap<>();
	
	/**
	 * documents (images)
	 */
	@OneToMany(mappedBy = "companyTranslation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyDocument> documents = new ArrayList<>();
	
	/**
	 * certifications
	 */
	@OneToMany(mappedBy = "companyTranslation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyCertification> certifications = new ArrayList<>();	


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getInterview() {
		return interview;
	}

	public void setInterview(String interview) {
		this.interview = interview;
	}

	public String getWebPage() {
		return webPage;
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

	public List<CompanyDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<CompanyDocument> documents) {
		this.documents = documents;
	}

	public List<CompanyCertification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<CompanyCertification> certifications) {
		this.certifications = certifications;
	}
}
