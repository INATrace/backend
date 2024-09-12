package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.TimestampEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductType extends TimestampEntity {

    @Version
    private Long entityVersion;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String description;

    /**
     * Translations for the product type
     */
    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTypeTranslation> productTypeTranslations;

    public Long getEntityVersion() {
        return entityVersion;
    }

    public void setEntityVersion(Long entityVersion) {
        this.entityVersion = entityVersion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String fieldName) {
        this.code = fieldName;
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

    public List<ProductTypeTranslation> getProductTypeTranslations() {
        if (productTypeTranslations == null) {
            productTypeTranslations = new ArrayList<>();
        }
        return productTypeTranslations;
    }

    public void setProductTypeTranslations(List<ProductTypeTranslation> productTypeTranslations) {
        this.productTypeTranslations = productTypeTranslations;
    }
}
