package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.types.ProductLabelStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ProductLabel extends BaseEntity {
	
	@Version
	private long entityVersion;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductLabelContent content;
	
	/**
	 * label status
	 */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private ProductLabelStatus status = ProductLabelStatus.UNPUBLISHED;

	/**
	 * label uuid (for url)
	 */
	@Column(unique = true, length = Lengths.UID)
	private String uuid = labelUUID();
	
	/**
	 * label title
	 */
	@Column(length = Lengths.DEFAULT)
	private String title;
	
	/**
	 * product's fields displayed for this label
	 */
	@ElementCollection(targetClass = ProductLabelField.class)
    @CollectionTable
    @OrderColumn
    private List<ProductLabelField> fields = new ArrayList<>(0);
	
	public static String labelUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public ProductLabelContent getContent() {
		return content;
	}

	public void setContent(ProductLabelContent content) {
		this.content = content;
	}

	public ProductLabelStatus getStatus() {
		return status;
	}

	public void setStatus(ProductLabelStatus status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ProductLabelField> getFields() {
		return fields;
	}

	public void setFields(List<ProductLabelField> fields) {
		this.fields = fields;
	}
}