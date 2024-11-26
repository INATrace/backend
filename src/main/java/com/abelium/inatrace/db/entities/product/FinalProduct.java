package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "FinalProduct.getFinalProductsForProductsIds", query = "SELECT fp From FinalProduct fp WHERE fp.product.id IN :productsIds"),
        @NamedQuery(name = "FinalProduct.countFinalProductsForProductsIds", query = "SELECT COUNT(fp) From FinalProduct fp WHERE fp.product.id IN :productsIds")
})
public class FinalProduct extends TimestampEntity {

    @Version
    private long entityVersion;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private MeasureUnitType measurementUnitType;

    @ManyToOne
    private Product product;

    @OneToMany(mappedBy = "finalProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FinalProductLabel> finalProductLabels;

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

    public Set<FinalProductLabel> getFinalProductLabels() {
        if (finalProductLabels == null) {
            finalProductLabels = new HashSet<>();
        }
        return finalProductLabels;
    }

}
