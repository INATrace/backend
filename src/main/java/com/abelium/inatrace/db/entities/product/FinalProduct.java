package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class FinalProduct extends TimestampEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private MeasureUnitType measurementUnitType;

    @ManyToOne
    private Product product;

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

    public MeasureUnitType getMeasurementUnitType() {
        return measurementUnitType;
    }

    public void setMeasurementUnitType(MeasureUnitType measurementUnitType) {
        this.measurementUnitType = measurementUnitType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
