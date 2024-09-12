package com.abelium.inatrace.db.entities.analytics;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;

import jakarta.persistence.*;

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
