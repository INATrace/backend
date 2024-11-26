package com.abelium.inatrace.db.entities.value_chain;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Intermediate entity between value chain and semi-product.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
@Table(indexes = {@Index(columnList = "valueChain_id, semiProduct_id", unique = true) })
public class ValueChainSemiProduct extends BaseEntity {

	@ManyToOne(optional = false)
	private ValueChain valueChain;

	@ManyToOne(optional = false)
	private SemiProduct semiProduct;

	public ValueChainSemiProduct() {
		super();
	}

	public ValueChainSemiProduct(ValueChain valueChain, SemiProduct semiProduct) {
		super();
		this.valueChain = valueChain;
		this.semiProduct = semiProduct;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public SemiProduct getSemiProduct() {
		return semiProduct;
	}

	public void setSemiProduct(SemiProduct semiProduct) {
		this.semiProduct = semiProduct;
	}

}
