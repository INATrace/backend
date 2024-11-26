package com.abelium.inatrace.db.entities.stockorder;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.ActivityProof;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * Connecting entity that connects stock order with particular activity proof.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class StockOrderActivityProof extends BaseEntity {

	@ManyToOne
	private StockOrder stockOrder;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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
