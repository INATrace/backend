package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;

@Entity
public class GlobalSettings extends BaseEntity {
	
	/**
	 * Settings name
	 */
    @Column(length = Lengths.GLOBAL_SETTING_NAME, nullable = false, unique = true)
    private String name;
    
    /**
     * Settings value
     */
    @Lob
    private String value;
    
    /**
     * Can be accessed using public api
     */
    @Column
    private Boolean isPublic;

    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
}
