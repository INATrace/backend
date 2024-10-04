package com.abelium.inatrace.db.entities.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

import java.math.BigDecimal;
import java.util.Date;

@Embeddable
@MappedSuperclass
public class FarmInformation {
    
    @Column
    private String areaUnit;

    @Column
    private BigDecimal totalCultivatedArea;

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
