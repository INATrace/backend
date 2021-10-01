package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.stockorder.enums.ScoreImpactType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ScoreImpact extends BaseEntity {
	
	@Column
	private Float score;
	
	@Enumerated(EnumType.STRING)
	@Column(length = Lengths.ENUM)
	private ScoreImpactType type;

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public ScoreImpactType getType() {
		return type;
	}

	public void setType(ScoreImpactType type) {
		this.type = type;
	}
}
