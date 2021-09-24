package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ScoreTarget extends BaseEntity {
	
	@Column
	private Integer fairness;
	
	@Column
	private Integer provenance;
	
	@Column
	private Integer quality;
	
	@Column
	private String qualityLevel;
	
	@Column
	private Boolean womenShare;
	
	@Column
	private Integer order;
	
	@Column
	private Integer payment;

	public Integer getFairness() {
		return fairness;
	}

	public void setFairness(Integer fairness) {
		this.fairness = fairness;
	}

	public Integer getProvenance() {
		return provenance;
	}

	public void setProvenance(Integer provenance) {
		this.provenance = provenance;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public String getQualityLevel() {
		return qualityLevel;
	}

	public void setQualityLevel(String qualityLevel) {
		this.qualityLevel = qualityLevel;
	}

	public Boolean getWomenShare() {
		return womenShare;
	}

	public void setWomenShare(Boolean womenShare) {
		this.womenShare = womenShare;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	
}
