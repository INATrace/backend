package com.abelium.inatrace.components.beycoorder.api;

import com.abelium.inatrace.types.BeycoIncoterms;
import com.abelium.inatrace.types.BeycoCurrency;
import com.abelium.inatrace.types.BeycoPriceUnit;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class ApiBeycoOrderCoffees {

    @ApiModelProperty(value = "Coffee description fields")
    private ApiBeycoCoffee coffee;

    @ApiModelProperty(value = "Is fixed price")
    private Boolean isFixedPrice;

    @ApiModelProperty(value = "Incoterms")
    private BeycoIncoterms incoterms;

    @ApiModelProperty(value = "Price of order")
    private BigDecimal price;

    @ApiModelProperty(value = "Price unit")
    private BeycoPriceUnit priceUnit;

    @ApiModelProperty(value = "Used currency")
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

}
