package com.abelium.inatrace.components.product.api;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Date;

@Validated
public class ApiFarmInformation {

    @ApiModelProperty(value = "Total cultivated area (ha)")
    public BigDecimal totalCultivatedArea;

    @ApiModelProperty(value = "Area cultivated with coffee (ha)")
    public BigDecimal coffeeCultivatedArea;

    @ApiModelProperty(value = "Number of trees")
    public Integer numberOfTrees;

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

    public BigDecimal getCoffeeCultivatedArea() {
        return coffeeCultivatedArea;
    }

    public void setCoffeeCultivatedArea(BigDecimal coffeeCultivatedArea) {
        this.coffeeCultivatedArea = coffeeCultivatedArea;
    }

    public Integer getNumberOfTrees() {
        return numberOfTrees;
    }

    public void setNumberOfTrees(Integer numberOfTrees) {
        this.numberOfTrees = numberOfTrees;
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
}