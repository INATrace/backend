package com.abelium.inatrace.components.transaction.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.action_type.api.ApiActionType;
import com.abelium.inatrace.components.codebook.grade_abbreviation.api.ApiGradeAbbreviation;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.db.entities.stockorder.enums.TransactionStatus;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class ApiTransaction extends ApiBaseEntity {

    @ApiModelProperty(value = "Company that is part of the transaction", position = 1)
    private ApiCompany company;

    @ApiModelProperty(value = "Initiator user ID", position = 2)
    private Long initiationUserId;

    @ApiModelProperty(value = "Source stock order", position = 3)
    private ApiStockOrder sourceStockOrder;

    @ApiModelProperty(value = "Target stock order", position = 4)
    private ApiStockOrder targetStockOrder;

    @ApiModelProperty(value = "Semi product", position = 5)
    private ApiSemiProduct semiProduct;

    @ApiModelProperty(value = "Source facility", position = 6)
    private ApiFacility sourceFacility;

    @ApiModelProperty(value = "Target facility", position = 7)
    private ApiFacility targetFacility;

    @ApiModelProperty(value = "Is order of type processing", position = 8)
    private Boolean isProcessing;

    @ApiModelProperty(value = "Action type", position = 9)
    private ApiActionType actionType;

    @ApiModelProperty(value = "Transaction status", position = 10)
    private TransactionStatus status;

    @ApiModelProperty(value = "Shipment ID", position = 11)
    private Long shipmentId;

    @ApiModelProperty(value = "Input measurement unit type", position = 12)
    private ApiMeasureUnitType inputMeasureUnitType;

    @ApiModelProperty(value = "Output measurement unit type", position = 13)
    private ApiMeasureUnitType outputMeasureUnitType;

    @ApiModelProperty(value = "Input quantity", position = 14)
    private BigDecimal inputQuantity;

    @ApiModelProperty(value = "Output quantity", position = 15)
    private BigDecimal outputQuantity;

    @ApiModelProperty(value = "Price per unit", position = 16)
    private BigDecimal pricePerUnit;

    @ApiModelProperty(value = "Currency", position = 17)
    private String currency;

    @ApiModelProperty(value = "Grade abbreviation", position = 18)
    private ApiGradeAbbreviation gradeAbbreviation;

    @ApiModelProperty(value = "Reject comment", position = 19)
    private String rejectComment;

    public ApiCompany getCompany() {
        return company;
    }

    public void setCompany(ApiCompany company) {
        this.company = company;
    }

    public Long getInitiationUserId() {
        return initiationUserId;
    }

    public void setInitiationUserId(Long initiationUserId) {
        this.initiationUserId = initiationUserId;
    }

    public ApiStockOrder getSourceStockOrder() {
        return sourceStockOrder;
    }

    public void setSourceStockOrder(ApiStockOrder sourceStockOrder) {
        this.sourceStockOrder = sourceStockOrder;
    }

    public ApiStockOrder getTargetStockOrder() {
        return targetStockOrder;
    }

    public void setTargetStockOrder(ApiStockOrder targetStockOrder) {
        this.targetStockOrder = targetStockOrder;
    }

    public ApiSemiProduct getSemiProduct() {
        return semiProduct;
    }

    public void setSemiProduct(ApiSemiProduct semiProduct) {
        this.semiProduct = semiProduct;
    }

    public ApiFacility getSourceFacility() {
        return sourceFacility;
    }

    public void setSourceFacility(ApiFacility sourceFacility) {
        this.sourceFacility = sourceFacility;
    }

    public ApiFacility getTargetFacility() {
        return targetFacility;
    }

    public void setTargetFacility(ApiFacility targetFacility) {
        this.targetFacility = targetFacility;
    }

    public Boolean getIsProcessing() {
        return isProcessing;
    }

    public void setIsProcessing(Boolean processing) {
        isProcessing = processing;
    }

    public ApiActionType getActionType() {
        return actionType;
    }

    public void setActionType(ApiActionType actionType) {
        this.actionType = actionType;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public ApiMeasureUnitType getInputMeasureUnitType() {
        return inputMeasureUnitType;
    }

    public void setInputMeasureUnitType(ApiMeasureUnitType inputMeasureUnitType) {
        this.inputMeasureUnitType = inputMeasureUnitType;
    }

    public ApiMeasureUnitType getOutputMeasureUnitType() {
        return outputMeasureUnitType;
    }

    public void setOutputMeasureUnitType(ApiMeasureUnitType outputMeasureUnitType) {
        this.outputMeasureUnitType = outputMeasureUnitType;
    }

    public BigDecimal getInputQuantity() {
        return inputQuantity;
    }

    public void setInputQuantity(BigDecimal inputQuantity) {
        this.inputQuantity = inputQuantity;
    }

    public BigDecimal getOutputQuantity() {
        return outputQuantity;
    }

    public void setOutputQuantity(BigDecimal outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ApiGradeAbbreviation getGradeAbbreviation() {
        return gradeAbbreviation;
    }

    public void setGradeAbbreviation(ApiGradeAbbreviation gradeAbbreviation) {
        this.gradeAbbreviation = gradeAbbreviation;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }
}
