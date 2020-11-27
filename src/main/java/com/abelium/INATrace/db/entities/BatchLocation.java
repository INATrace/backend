package com.abelium.INATrace.db.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
