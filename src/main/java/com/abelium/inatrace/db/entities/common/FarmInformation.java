package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.api.types.Lengths;

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

    @Column(length = Lengths.DEFAULT)
    private BigDecimal totalCultivatedArea;

    @Deprecated // FIXME: after this gets deployed to PROD and DEMO, remove it
    @Column(length = Lengths.DEFAULT)
    private BigDecimal plantCultivatedArea;

    @Deprecated // FIXME: after this gets deployed to PROD and DEMO, remove it
    @Column(length = Lengths.DEFAULT)
    private Integer numberOfPlants;

    @Column
    private Boolean organic;

    @Column(length = Lengths.DEFAULT)
    private BigDecimal areaOrganicCertified;

    @Column
    private Date startTransitionToOrganic;

    public BigDecimal getTotalCultivatedArea() {
        return totalCultivatedArea;
    }

    public void setTotalCultivatedArea(BigDecimal totalCultivatedArea) {
        this.totalCultivatedArea = totalCultivatedArea;
    }

    public BigDecimal getPlantCultivatedArea() {
        return plantCultivatedArea;
    }

    public void setPlantCultivatedArea(BigDecimal plantCultivatedArea) {
        this.plantCultivatedArea = plantCultivatedArea;
    }

    public Integer getNumberOfPlants() {
        return numberOfPlants;
    }

    public void setNumberOfPlants(Integer numberOfPlants) {
        this.numberOfPlants = numberOfPlants;
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
