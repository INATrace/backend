package com.abelium.inatrace.db.entities.company;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.components.company.types.CompanyTranslatables;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.Address;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.types.CompanyStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(indexes = { @Index(columnList = "name") })
public class Company extends BaseEntity implements CompanyTranslatables {
	
	@Version
	private long entityVersion;
	
	/**
	 * status - registered first, activated after admin activation, deactivated
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM, nullable = false)
	private CompanyStatus status = CompanyStatus.REGISTERED;
	
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
	 * high-resolution logo of the company (jpeg, jpg, png)
	 */
	@OneToOne
	private Document logo;
	
	@Embedded
	private Address headquarters;
	
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
	 * name of manager / CEO
	 */
	@Column(length = Lengths.DEFAULT)
	private String manager;

	/**
	 * email
	 */
	@Column(length = Lengths.EMAIL)
	private String email;	

	/**
	 * phone number
	 */
	@Column(length = Lengths.PHONE_NUMBER)
	private String phone;	
	
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
	 * a list of users
	 */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyUser> users = new ArrayList<>();

	/**
	 * documents (images)
	 */
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyDocument> documents = new ArrayList<>();
	
	/**
	 * certifications
	 */
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyCertification> certifications = new ArrayList<>();

	/**
	 * translations of fields
	 */
	@OneToMany(mappedBy = "company")
	private List<CompanyTranslation> translations = new ArrayList<>();
	
	/**
	 * facilities
	 */
	@OneToMany(mappedBy = "company")
  private List<Facility> facilities = new ArrayList<>();
  
  public CompanyStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyStatus status) {
		this.status = status;
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

	public Document getLogo() {
		return logo;
	}

	public void setLogo(Document logo) {
		this.logo = logo;
	}

	public Address getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(Address headquarters) {
		this.headquarters = headquarters;
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
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

	public Map<String, String> getMediaLinks() {
		return mediaLinks;
	}

	public void setMediaLinks(Map<String, String> mediaLinks) {
		this.mediaLinks = mediaLinks;
	}

	public List<CompanyUser> getUsers() {
		return users;
	}

	public void setUsers(List<CompanyUser> users) {
		this.users = users;
	}

	public List<CompanyDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<CompanyDocument> documents) {
		this.documents = documents;
	}

	public List<CompanyTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<CompanyTranslation> translations) {
		this.translations = translations;
	}

	public List<CompanyCertification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<CompanyCertification> certifications) {
		this.certifications = certifications;
	}
	
	public List<Facility> getFacilities() {
    return facilities;
  }

  public void setFacilities(List<Facility> facilities) {
    this.facilities = facilities;
  }
  
}
