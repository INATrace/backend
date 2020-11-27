package com.abelium.INATrace.db.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.abelium.INATrace.db.base.BaseEntity;

@Entity
public class Process extends BaseEntity {

	/**
	 * production description - Briefly describe your production process. 
	 * If the ingredients are organic, describe how you achieve that 
	 * (e.g. organic seedlings, no chemical fertilizers, etc.)
	 */
	@Lob
	private String production;

	/**
	 * storage - Briefly describe your storage procedures (e.g. cold storage at farmer group aggregation
	 * centers, warehouse storage at processing site before distribution...)
	 */
	@Lob
	private String storage;
	
	/**
	 * codes of conduct - Briefly describe your company codes of conduct that your employees, 
	 * suppliers and farmers have to adhere to. Also briefly describe the general 
	 * sanitary / hygiene protocols and specific measures you put in place due coronavirus crises
	 */
	@Lob
	private String codesOfConduct;

	/**
	 * certifications and standards
	 */
	@OneToMany(mappedBy = "process", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessStandard> standards = new ArrayList<>();
	
	/**
	 * production records - pdf/image of your production records (farming records, production protocols...)
	 */
	@OneToMany(mappedBy = "process", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessDocument> records = new ArrayList<>();

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getCodesOfConduct() {
		return codesOfConduct;
	}

	public void setCodesOfConduct(String codesOfConduct) {
		this.codesOfConduct = codesOfConduct;
	}

	public List<ProcessStandard> getStandards() {
		return standards;
	}

	public void setStandards(List<ProcessStandard> standards) {
		this.standards = standards;
	}

	public List<ProcessDocument> getRecords() {
		return records;
	}

	public void setRecords(List<ProcessDocument> records) {
		this.records = records;
	}
	
	public Process copy() {
		Process p = new Process();
		p.setProduction(getProduction());
		p.setStorage(getStorage());
		p.setCodesOfConduct(getCodesOfConduct());
		p.setStandards(getStandards().stream().map(s -> s.copy()).collect(Collectors.toList()));
		p.setRecords(getRecords().stream().map(s -> s.copy()).collect(Collectors.toList()));
		return p;
	}
}
