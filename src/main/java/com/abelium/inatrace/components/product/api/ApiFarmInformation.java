package com.abelium.inatrace.components.product.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Validated
public class ApiFarmInformation {
    
    @Schema(description = "Area unit")
    public String areaUnit;

    @Schema(description = "Total cultivated area (ha)")
    public BigDecimal totalCultivatedArea;

    @Schema(description = "List of plant information")
    public List<ApiFarmPlantInformation> farmPlantInformationList;

    @Schema(description = "Organic")
    public Boolean organic;

    @Schema(description = "Area is organic certified")
    public BigDecimal areaOrganicCertified;

    @Schema(description = "Start date of transition to organic")
    public Date startTransitionToOrganic;

    public BigDecimal getTotalCultivatedArea() {
        return totalCultivatedArea;
    }

    public void setTotalCultivatedArea(BigDecimal totalCultivatedArea) {
        this.totalCultivatedArea = totalCultivatedArea;
    }

    public List<ApiFarmPlantInformation> getFarmPlantInformationList() {
        if (farmPlantInformationList == null) {
            farmPlantInformationList = new ArrayList<>();
        }
        return farmPlantInformationList;
    }

    public void setFarmPlantInformationList(List<ApiFarmPlantInformation> farmPlantInformationList) {
        this.farmPlantInformationList = farmPlantInformationList;
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
