package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.entities.common.Location;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class BatchLocation extends Location {
	
	/**
	 * Batch for this location
	 */
	@ManyToOne
	private ProductLabelBatch batch;

	public ProductLabelBatch getBatch() {
		return batch;
	}

	public void setBatch(ProductLabelBatch batch) {
		this.batch = batch;
	}
}
