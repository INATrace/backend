package com.abelium.inatrace.db.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * Superclass for base codebook entities.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@MappedSuperclass
public class CodebookBaseEntity extends BaseEntity {

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	private String label;

	public CodebookBaseEntity() {
	}

	public CodebookBaseEntity(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
