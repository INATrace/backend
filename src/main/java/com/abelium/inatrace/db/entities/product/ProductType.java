package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.base.TimestampEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class ProductType extends TimestampEntity {

    @Version
    private Long entityVersion;

    @Column
    private String name;

    @Column
    private String description;

    public Long getEntityVersion() {
        return entityVersion;
    }

    public void setEntityVersion(Long entityVersion) {
        this.entityVersion = entityVersion;
    }

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
}
