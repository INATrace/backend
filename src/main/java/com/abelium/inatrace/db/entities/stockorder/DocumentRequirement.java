package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.processingevidencefield.ProcessingEvidenceField;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class DocumentRequirement extends TimestampEntity {
	
	@Version
	private Long entityVersion;

	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private Boolean isRequired;
	
	@ManyToOne
	private StockOrder stockOrder;
	
	@ManyToOne
	private SemiProduct semiProduct;
	
	@OneToMany(mappedBy = "documentRequirement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScoreImpact> scoreImpacts = new ArrayList<>();
	
	@OneToMany(mappedBy = "documentRequirement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProcessingEvidenceField> fields = new ArrayList<>();
	
	@OneToOne
	private ScoreTarget scoreTarget;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public SemiProduct getSemiProduct() {
		return semiProduct;
	}

	public void setSemiProduct(SemiProduct semiProduct) {
		this.semiProduct = semiProduct;
	}

	public List<ScoreImpact> getScoreImpacts() {
		return scoreImpacts;
	}

	public void setScoreImpacts(List<ScoreImpact> scoreImpacts) {
		this.scoreImpacts = scoreImpacts;
	}

	public List<ProcessingEvidenceField> getFields() {
		return fields;
	}

	public void setFields(List<ProcessingEvidenceField> fields) {
		this.fields = fields;
	}

	public ScoreTarget getScoreTarget() {
		return scoreTarget;
	}

	public void setScoreTarget(ScoreTarget scoreTarget) {
		this.scoreTarget = scoreTarget;
	}
	
}
