package com.abelium.inatrace.db.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;


@Entity
public class CompanyCertification extends BaseEntity {

	/**
	 * Company 
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
	@Column(length = Lengths.DEFAULT)
    private String type;
	
	/**
	 * Certificate document
	 */
	@ManyToOne
	private Document certificate;
	
	/**
	 * Description of this certification
	 */
	@Lob
	private String description;

	/**
	 * Validity
	 */
	@Column
	private LocalDate validity;	
	

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Document getCertificate() {
		return certificate;
	}

	public void setCertificate(Document certificate) {
		this.certificate = certificate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getValidity() {
		return validity;
	}

	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}	
}
