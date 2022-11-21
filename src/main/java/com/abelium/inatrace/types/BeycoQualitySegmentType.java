package com.abelium.inatrace.types;

public enum BeycoQualitySegmentType {

    SPECIALTY("Specialty"),
    COMMODITY_CONVENTIONAL("CommodityConventional"),
    LOW_GRADE("LowGrade");

    private final String qualitySegment;

    BeycoQualitySegmentType(String s) {
        this.qualitySegment = s;
    }

    @Override
    public String toString() {
        return this.qualitySegment;
    }

}
