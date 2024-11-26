package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.TranslatedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class ProductTypeTranslation extends TranslatedEntity {

	@Version
	private Long entityVersion;

	@Column
	private String name;

	@Column
	private String description;

	@ManyToOne
	@NotNull
	private ProductType productType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
}
