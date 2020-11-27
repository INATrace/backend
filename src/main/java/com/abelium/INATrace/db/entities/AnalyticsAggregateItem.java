package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;

@Entity
@Table(indexes = { @Index(columnList = "itemKey") })
public class AnalyticsAggregateItem extends BaseEntity {

	/**
	 * parent aggregate
	 */
	@ManyToOne
	private AnalyticsAggregate aggregate;
	
    /**
     * aggregate key
     */
    @Column(length = Lengths.ANALYTICS_KEY)
    private String itemKey;
    
    /**
     * Integer value
     */
    @Column
    private int intValue;

    // Add more values if needed

	public int getIntValue() {
		return intValue;
	}

	public AnalyticsAggregate getAggregate() {
		return aggregate;
	}

	public void setAggregate(AnalyticsAggregate aggregate) {
		this.aggregate = aggregate;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

}
