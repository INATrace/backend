package com.abelium.inatrace.db.entities.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Date;

@Embeddable
@MappedSuperclass
public class FarmInformation {
    
    @Column
    private String areaUnit;

    @Column
    private BigDecimal totalCultivatedArea;

    @Deprecated // FIXME: after this gets deployed to PROD and DEMO, remove it
    @Column
    private BigDecimal coffeeCultivatedArea;

    @Deprecated // FIXME: after this gets deployed to PROD and DEMO, remove it
    @Column
    private Integer numberOfTrees;

    @Column
    private Boolean organic;

    @Column
    private BigDecimal areaOrganicCertified;

    @Column
    private Date startTransitionToOrganic;

    public BigDecimal getTotalCultivatedArea() {
        return totalCultivatedArea;
    }

    public void setTotalCultivatedArea(BigDecimal totalCultivatedArea) {
        this.totalCultivatedArea = totalCultivatedArea;
    }

    public BigDecimal getCoffeeCultivatedArea() {
        return coffeeCultivatedArea;
    }

    public void setCoffeeCultivatedArea(BigDecimal plantCultivatedArea) {
        this.coffeeCultivatedArea = plantCultivatedArea;
    }

    public Integer getNumberOfTrees() {
        return numberOfTrees;
    }

    public void setNumberOfTrees(Integer numberOfPlants) {
        this.numberOfTrees = numberOfPlants;
    }

    public Boolean getOrganic() {
        return organic;
    }

    public void setOrganic(Boolean organic) {
        this.organic = organic;
    }

    public BigDecimal getAreaOrganicCertified() {
        return areaOrganicCertified;
    }

    public void setAreaOrganicCertified(BigDecimal areaOrganicCertified) {
        this.areaOrganicCertified = areaOrganicCertified;
    }

    public Date getStartTransitionToOrganic() {
        return startTransitionToOrganic;
    }

    public void setStartTransitionToOrganic(Date startTransitionToOrganic) {
        this.startTransitionToOrganic = startTransitionToOrganic;
    }
    
    public String getAreaUnit() {
        return areaUnit;
    }
    
    public void setAreaUnit(String areaUnit) {
        this.areaUnit = areaUnit;
    }
}
