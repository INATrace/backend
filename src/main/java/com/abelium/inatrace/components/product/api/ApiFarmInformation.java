package com.abelium.inatrace.components.product.api;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Validated
public class ApiFarmInformation {
    
    @ApiModelProperty(value = "Area unit")
    public String areaUnit;

    @ApiModelProperty(value = "Total cultivated area (ha)")
    public BigDecimal totalCultivatedArea;

    @ApiModelProperty(value = "List of plant information")
    public List<ApiFarmPlantInformation> farmPlantInformationList;

    @ApiModelProperty(value = "Organic")
    public Boolean organic;

    @ApiModelProperty(value = "Area is organic certified")
    public BigDecimal areaOrganicCertified;

    @ApiModelProperty(value = "Start date of transition to organic")
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
