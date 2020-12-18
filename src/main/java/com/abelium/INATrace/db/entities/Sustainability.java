package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;

@Entity
public class Sustainability extends BaseEntity {

	/**
	 * environmentally friendly production - Describe how your production impacts the environment 
	 * and how you mitigate the pollution, how you utilize your waste, if you practice circular 
	 * production… (max 1000 characters)
	 */
	@Lob
	private String production;

	/**
	 * sustainable packaging - Describe the environmental sustainability of your packaging – 
	 * do you use recycled packing, is your packing recyclable, do you collect the packaging and 
	 * reuse it? (max 1000 characters)
	 */
	@Lob
	private String packaging;
	
	/**
	 * CO2 footprint - If you have calculated your company CO2 footprint, please add this information. 
	 * Make sure to include your annual CO2 footprint
	 */
	@Column(length = Lengths.DEFAULT)
	private String co2Footprint;

	
	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getCo2Footprint() {
		return co2Footprint;
	}

	public void setCo2Footprint(String co2Footprint) {
		this.co2Footprint = co2Footprint;
	}

	public Sustainability copy() {
		Sustainability s = new Sustainability();
		s.setProduction(getProduction());
		s.setPackaging(getPackaging());
		s.setCo2Footprint(getCo2Footprint());
		return s;
	}
}
