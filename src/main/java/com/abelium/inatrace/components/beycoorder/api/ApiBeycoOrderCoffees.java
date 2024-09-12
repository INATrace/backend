package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoIncoterms;
import com.abelium.inatrace.types.BeycoCurrency;
import com.abelium.inatrace.types.BeycoPriceUnit;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public class ApiBeycoOrderCoffees {

    @Schema(description = "Coffee description fields")
    private ApiBeycoCoffee coffee;

    @Schema(description = "Is fixed price")
    private Boolean isFixedPrice;

    @Schema(description = "Incoterms")
    private BeycoIncoterms incoterms;

    @Schema(description = "Custom incoterms")
    private String customIncoterms;

    @Min(0)
    @Max(999999999999999999L)
    @Schema(description = "Price of order")
    private BigDecimal price;

    @Schema(description = "Price unit")
    private BeycoPriceUnit priceUnit;

    @Schema(description = "Used currency")
    private BeycoCurrency currency;

    public ApiBeycoCoffee getCoffee() {
        return coffee;
    }

    public void setCoffee(ApiBeycoCoffee coffee) {
        this.coffee = coffee;
    }

    public BeycoIncoterms getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(BeycoIncoterms incoterms) {
        this.incoterms = incoterms;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BeycoPriceUnit getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(BeycoPriceUnit priceUnit) {
        this.priceUnit = priceUnit;
    }

    public BeycoCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BeycoCurrency currency) {
        this.currency = currency;
    }

    public Boolean getIsFixedPrice() {
        return isFixedPrice;
    }

    public void setIsFixedPrice(Boolean isFixedPrice) {
        this.isFixedPrice = isFixedPrice;
    }

    public String getCustomIncoterms() {
        return customIncoterms;
    }

    public void setCustomIncoterms(String customIncoterms) {
        this.customIncoterms = customIncoterms;
    }
}
