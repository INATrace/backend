package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingevidencefield.api.ApiProcessingEvidenceField;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class ApiDocumentRequirement extends ApiBaseEntity {

    @ApiModelProperty(value = "Document name", position = 1)
    public String name;

    @ApiModelProperty(value = "Document description", position = 2)
    public String description;

    @ApiModelProperty(value = "Is document required", position = 3)
    public Boolean isRequired;

    // TODO: Is bidirectional connection really required?
//    @ApiModelProperty(value = "Stock order", position = 4)
//    public ApiStockOrder stockOrder;
//    @ApiModelProperty(value = "Semi product", position = 5)
//    public ApiSemiProduct semiProduct;

    @ApiModelProperty(value = "Score impact", position = 6)
    public List<ApiScoreImpact> scoreImpacts = new ArrayList<>();

    @ApiModelProperty(value = "Processing evidence fields", position = 7)
    public List<ApiProcessingEvidenceField> fields = new ArrayList<>();

    @ApiModelProperty(value = "Score target", position = 34)
    public ApiScoreTarget scoreTarget;

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

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public List<ApiScoreImpact> getScoreImpacts() {
        return scoreImpacts;
    }

    public void setScoreImpacts(List<ApiScoreImpact> scoreImpacts) {
        this.scoreImpacts = scoreImpacts;
    }

    public List<ApiProcessingEvidenceField> getFields() {
        return fields;
    }

    public void setFields(List<ApiProcessingEvidenceField> fields) {
        this.fields = fields;
    }

    public ApiScoreTarget getScoreTarget() {
        return scoreTarget;
    }

    public void setScoreTarget(ApiScoreTarget scoreTarget) {
        this.scoreTarget = scoreTarget;
    }
}
