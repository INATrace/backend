package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.ActivityProof;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class StockOrderActivityProof extends BaseEntity {

	@ManyToOne
	private StockOrder stockOrder;

	@OneToOne
	private ActivityProof activityProof;

	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	public ActivityProof getActivityProof() {
		return activityProof;
	}

	public void setActivityProof(ActivityProof activityProof) {
		this.activityProof = activityProof;
	}

}
