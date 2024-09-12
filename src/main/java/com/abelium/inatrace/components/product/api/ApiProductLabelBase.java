package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProductLabelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public class ApiProductLabelBase extends ApiBaseEntity {

	@Schema(description = "Product id")
	public Long productId;
	
	@Schema(description = "Product label status")
    public ProductLabelStatus status;

	@Schema(description = "Product label uuid (for url)")
	public String uuid;
	
	@Size(max = Lengths.DEFAULT)
	@Schema(description = "label title", maxLength = Lengths.DEFAULT)
	public String title;

	@Schema(description = "Label language")
	public Language language;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
