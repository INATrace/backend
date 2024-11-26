package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class ApiFinalProduct extends ApiBaseEntity {

    @Schema(description = "Name of final product")
    private String name;

    @Schema(description = "Description of final product")
    private String description;

    @Schema(description = "The Product that this final product belongs")
    private ApiProductBase product;

    @Schema(description = "Measurement unit type of final product")
    private ApiMeasureUnitType measurementUnitType;

    private List<ApiProductLabelBase> labels;

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

    public ApiProductBase getProduct() {
        return product;
    }

    public void setProduct(ApiProductBase product) {
        this.product = product;
    }

    public ApiMeasureUnitType getMeasurementUnitType() {
        return measurementUnitType;
    }

    public void setMeasurementUnitType(ApiMeasureUnitType measurementUnitType) {
        this.measurementUnitType = measurementUnitType;
    }

    public List<ApiProductLabelBase> getLabels() {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        return labels;
    }

    public void setLabels(List<ApiProductLabelBase> labels) {
        this.labels = labels;
    }

}
