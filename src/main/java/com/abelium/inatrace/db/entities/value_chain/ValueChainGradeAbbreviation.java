package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.GradeAbbreviationType;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Intermediate entity between value chain and grade abbreviation type.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = { @Index(columnList = "valueChain_id, gradeAbbreviationType_id", unique = true) })
public class ValueChainGradeAbbreviation extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private GradeAbbreviationType gradeAbbreviationType;

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public GradeAbbreviationType getGradeAbbreviationType() {
		return gradeAbbreviationType;
	}

	public void setGradeAbbreviationType(GradeAbbreviationType gradeAbbreviationType) {
		this.gradeAbbreviationType = gradeAbbreviationType;
	}

}
