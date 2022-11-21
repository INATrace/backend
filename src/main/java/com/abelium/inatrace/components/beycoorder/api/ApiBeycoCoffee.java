package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.*;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ApiBeycoCoffee {

    @ApiModelProperty(value = "Internal LOT number or name of coffee")
    @Size(min = 1)
    private String name;

    @ApiModelProperty(value = "Region of coffee beans")
    private String region;

    @ApiModelProperty(value = "Country of coffee beans")
    @Size(min = 1)
    private String country;

    @ApiModelProperty(value = "Harvest date")
    private Instant harvestAt;

    @ApiModelProperty(value = "Coffee species")
    private BeycoSpeciesType species;

    @ApiModelProperty(value = "Coffee process")
    private BeycoProcessType process;

    @ApiModelProperty(value = "Minimal screen size")
    @Min(8)
    @Max(20)
    private Integer minScreenSize;

    @ApiModelProperty(value = "Maximum screen size")
    @Min(8)
    @Max(20)
    private Integer maxScreenSize;

    @ApiModelProperty(value = "Cupping score")
    @Min(8)
    @Max(20)
    private Integer cuppingScore;

    @ApiModelProperty(value = "Varieties of coffee beans")
    private List<ApiBeycoCoffeeVariety> varieties;

    @ApiModelProperty(value = "Quality of coffee beans")
    private List<ApiBeycoCoffeeQuality> qualitySegments;

    @ApiModelProperty(value = "Grades of coffee beans")
    private List<ApiBeycoCoffeeGrade> grades;

    @ApiModelProperty(value = "Is bulk")
    private Boolean isBulk;

    @ApiModelProperty(value = "Certificates of coffee beans")
    private List<ApiBeycoCoffeeCertificate> certificates;

    @ApiModelProperty(value = "Quantity of beans")
    private BigDecimal quantity;

    @ApiModelProperty(value = "Unit of order")
    private BeycoCoffeeUnit unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Instant getHarvestAt() {
        return harvestAt;
    }

    public void setHarvestAt(Instant harvestAt) {
        this.harvestAt = harvestAt;
    }

    public BeycoSpeciesType getSpecies() {
        return species;
    }

    public void setSpecies(BeycoSpeciesType species) {
        this.species = species;
    }

    public BeycoProcessType getProcess() {
        return process;
    }

    public void setProcess(BeycoProcessType process) {
        this.process = process;
    }

    public Integer getMinScreenSize() {
        return minScreenSize;
    }

    public void setMinScreenSize(Integer minScreenSize) {
        this.minScreenSize = minScreenSize;
    }

    public Integer getMaxScreenSize() {
        return maxScreenSize;
    }

    public void setMaxScreenSize(Integer maxScreenSize) {
        this.maxScreenSize = maxScreenSize;
    }

    public Integer getCuppingScore() {
        return cuppingScore;
    }

    public void setCuppingScore(Integer cuppingScore) {
        this.cuppingScore = cuppingScore;
    }

    public List<ApiBeycoCoffeeVariety> getVarieties() {
        return varieties;
    }

    public void setVarieties(List<ApiBeycoCoffeeVariety> varieties) {
        this.varieties = varieties;
    }

    public List<ApiBeycoCoffeeQuality> getQualitySegments() {
        return qualitySegments;
    }

    public void setQualitySegments(List<ApiBeycoCoffeeQuality> qualitySegments) {
        this.qualitySegments = qualitySegments;
    }

    public List<ApiBeycoCoffeeGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<ApiBeycoCoffeeGrade> grades) {
        this.grades = grades;
    }

    public List<ApiBeycoCoffeeCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<ApiBeycoCoffeeCertificate> certificates) {
        this.certificates = certificates;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Boolean getBulk() {
        return isBulk;
    }

    public void setBulk(Boolean bulk) {
        isBulk = bulk;
    }

    public BeycoCoffeeUnit getUnit() {
        return unit;
    }

    public void setUnit(BeycoCoffeeUnit unit) {
        this.unit = unit;
    }

}
