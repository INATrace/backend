package com.abelium.inatrace.db.entities.analytics;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(indexes = { @Index(columnList = "key1"), @Index(columnList = "key2") })
public class AnalyticsAggregate extends BaseEntity {

    /**
     * Request key 1
     */
    @Column(length = Lengths.ANALYTICS_KEY)
    private String key1;
    
    /**
     * Request key 1
     */
    @Column(length = Lengths.ANALYTICS_KEY)
    private String key2;
    
    /**
     * Integer value
     */
    @Column
    private Integer intValue;

    /**
     * Instant value
     */
    @Column
    private Instant timestampValue;
    
    // Add more values if needed
    
	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public Instant getTimestampValue() {
		return timestampValue;
	}

	public void setTimestampValue(Instant timestampValue) {
		this.timestampValue = timestampValue;
	}
}
