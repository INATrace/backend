package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.company.Company;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Intermediate entity between value chain and company.
 *
 * @author Borche Paspalovski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = {@Index(columnList = "valueChain_id, company_id", unique = true) })
public class CompanyValueChain extends BaseEntity {

	@ManyToOne(optional = false)
	private Company company;

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	public CompanyValueChain() {
		super();
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

}
