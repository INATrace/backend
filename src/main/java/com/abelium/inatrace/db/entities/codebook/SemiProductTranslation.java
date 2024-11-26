package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.TranslatedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class SemiProductTranslation extends TranslatedEntity {

    @Version
    private Long entityVersion;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @NotNull
    private SemiProduct semiProduct;

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

    public SemiProduct getSemiProduct() {
        return semiProduct;
    }

    public void setSemiProduct(SemiProduct semiProduct) {
        this.semiProduct = semiProduct;
    }
}
