package com.abelium.inatrace.components.transaction.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.product.api.ApiFinalProduct;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.db.entities.stockorder.enums.TransactionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ApiTransaction extends ApiBaseEntity {

    @Schema(description = "Company that is part of the transaction")
    private ApiCompany company;

    @Schema(description = "Initiator user ID")
    private Long initiationUserId;

    @Schema(description = "Source stock order")
    private ApiStockOrder sourceStockOrder;

    // Used when retrieving Stock order history - using this we can navigate to the next processing
    @Schema(description = "Target stock order")
    private ApiStockOrder targetStockOrder;

    @Schema(description = "Semi product")
    private ApiSemiProduct semiProduct;

    @Schema(description = "Final product")
    private ApiFinalProduct finalProduct;

    @Schema(description = "Source facility")
    private ApiFacility sourceFacility;

    @Schema(description = "Is order of type processing")
    private Boolean isProcessing;

    @Schema(description = "Transaction status")
    private TransactionStatus status;

    @Schema(description = "Shipment ID")
    private Long shipmentId;

    @Schema(description = "Input measurement unit type")
    private ApiMeasureUnitType inputMeasureUnitType;

    @Schema(description = "Input quantity")
    private BigDecimal inputQuantity;

    @Schema(description = "Output quantity")
    private BigDecimal outputQuantity;

    @Schema(description = "Price per unit")
    private BigDecimal pricePerUnit;

    @Schema(description = "Currency")
    private String currency;

    @Schema(description = "Reject comment")
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

    public ApiFinalProduct getFinalProduct() {
        return finalProduct;
    }

    public void setFinalProduct(ApiFinalProduct finalProduct) {
        this.finalProduct = finalProduct;
    }

    public ApiFacility getSourceFacility() {
        return sourceFacility;
    }

    public void setSourceFacility(ApiFacility sourceFacility) {
        this.sourceFacility = sourceFacility;
    }

    public Boolean getIsProcessing() {
        return isProcessing;
    }

    public void setIsProcessing(Boolean processing) {
        isProcessing = processing;
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

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
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

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

}
