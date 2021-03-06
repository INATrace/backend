package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.types.CompanyDocumentCategory;
import com.abelium.INATrace.types.CompanyDocumentType;

@Entity
public class CompanyDocument extends BaseEntity {

	/**
	 * Company this document applies to
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	
	/**
	 * Company translation this document applies to
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private CompanyTranslation companyTranslation;
	
    /**
     * Document type (link, file)
     */
    @Enumerated(EnumType.STRING)
    @Column(length = Lengths.ENUM)
    private CompanyDocumentType type = CompanyDocumentType.LINK;	

    /**
     * Document category
     */
    @Enumerated(EnumType.STRING)
    @Column(length = Lengths.ENUM)
    private CompanyDocumentCategory category;	

	/**
	 * Name of this document
	 */
	@Column(length = Lengths.DEFAULT)
	private String name;
    
	/**
	 * Description of this document
	 */
	@Column(length = Lengths.DEFAULT)
	private String description;
	
	/**
	 * Quote (long text)
	 */
	@Lob
	private String quote;

	/**
	 * Link
	 */
	@Column(length = Lengths.DEFAULT)
	private String link;	
	
	/**
	 * Document attached to the company
	 */
	@ManyToOne
	private Document document;


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyTranslation getCompanyTranslation() {
		return companyTranslation;
	}

	public void setCompanyTranslation(CompanyTranslation companyTranslation) {
		this.companyTranslation = companyTranslation;
	}

	public CompanyDocumentType getType() {
		return type;
	}

	public void setType(CompanyDocumentType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public CompanyDocumentCategory getCategory() {
		return category;
	}

	public void setCategory(CompanyDocumentCategory category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
