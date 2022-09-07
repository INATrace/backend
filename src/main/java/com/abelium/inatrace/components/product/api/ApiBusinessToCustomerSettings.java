package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiTimestampEntity;
import com.abelium.inatrace.components.common.api.ApiDocument;
import com.abelium.inatrace.db.entities.product.enums.FairPricesUnit;

import java.math.BigDecimal;

public class ApiBusinessToCustomerSettings extends ApiTimestampEntity {

    private String primaryColor;
    private String secondaryColor;
    private String tertiaryColor;
    private String quaternaryColor;

    private String productTitleColor;
    private String headingColor;
    private String textColor;

    private Boolean tabFairPrices;
    private Boolean tabProducers;
    private Boolean tabQuality;
    private Boolean tabFeedback;

    private Long orderFairPrices;
    private Long orderProducers;
    private Long orderQuality;
    private Long orderFeedback;

    private ApiDocument productFont;
    private ApiDocument textFont;

    private ApiDocument landingPageImage;
    private ApiDocument landingPageBackgroundImage;
    private ApiDocument headerBackgroundImage;

    private Boolean graphicFairPrices;
    private Boolean graphicIncreaseOfIncome;
    private Boolean graphicQuality;
    private FairPricesUnit graphicPriceToProducer;
    private FairPricesUnit graphicFarmGatePrice;

    private BigDecimal manualFarmGatePrice;
    private BigDecimal manualProducerPrice;
    private BigDecimal containerSize;
    private BigDecimal worldMarket;
    private BigDecimal fairTrade;
    private BigDecimal averageRegionFarmGatePrice;

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getTertiaryColor() {
        return tertiaryColor;
    }

    public void setTertiaryColor(String tertiaryColor) {
        this.tertiaryColor = tertiaryColor;
    }

    public String getQuaternaryColor() {
        return quaternaryColor;
    }

    public void setQuaternaryColor(String quaternaryColor) {
        this.quaternaryColor = quaternaryColor;
    }

    public String getProductTitleColor() {
        return productTitleColor;
    }

    public void setProductTitleColor(String productTitleColor) {
        this.productTitleColor = productTitleColor;
    }

    public String getHeadingColor() {
        return headingColor;
    }

    public void setHeadingColor(String headingColor) {
        this.headingColor = headingColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Boolean getTabFairPrices() {
        return tabFairPrices;
    }

    public void setTabFairPrices(Boolean tabFairPrices) {
        this.tabFairPrices = tabFairPrices;
    }

    public Boolean getTabProducers() {
        return tabProducers;
    }

    public void setTabProducers(Boolean tabProducers) {
        this.tabProducers = tabProducers;
    }

    public Boolean getTabQuality() {
        return tabQuality;
    }

    public void setTabQuality(Boolean tabQuality) {
        this.tabQuality = tabQuality;
    }

    public Boolean getTabFeedback() {
        return tabFeedback;
    }

    public void setTabFeedback(Boolean tabFeedback) {
        this.tabFeedback = tabFeedback;
    }

    public Long getOrderFairPrices() {
        return orderFairPrices;
    }

    public void setOrderFairPrices(Long orderFairPrices) {
        this.orderFairPrices = orderFairPrices;
    }

    public Long getOrderProducers() {
        return orderProducers;
    }

    public void setOrderProducers(Long orderProducers) {
        this.orderProducers = orderProducers;
    }

    public Long getOrderQuality() {
        return orderQuality;
    }

    public void setOrderQuality(Long orderQuality) {
        this.orderQuality = orderQuality;
    }

    public Long getOrderFeedback() {
        return orderFeedback;
    }

    public void setOrderFeedback(Long orderFeedback) {
        this.orderFeedback = orderFeedback;
    }

    public ApiDocument getProductFont() {
        return productFont;
    }

    public void setProductFont(ApiDocument productFont) {
        this.productFont = productFont;
    }

    public ApiDocument getTextFont() {
        return textFont;
    }

    public void setTextFont(ApiDocument textFont) {
        this.textFont = textFont;
    }

    public ApiDocument getLandingPageImage() {
        return landingPageImage;
    }

    public void setLandingPageImage(ApiDocument landingPageImage) {
        this.landingPageImage = landingPageImage;
    }

    public ApiDocument getLandingPageBackgroundImage() {
        return landingPageBackgroundImage;
    }

    public void setLandingPageBackgroundImage(ApiDocument landingPageBackgroundImage) {
        this.landingPageBackgroundImage = landingPageBackgroundImage;
    }

    public ApiDocument getHeaderBackgroundImage() {
        return headerBackgroundImage;
    }

    public void setHeaderBackgroundImage(ApiDocument headerBackgroundImage) {
        this.headerBackgroundImage = headerBackgroundImage;
    }

    public Boolean getGraphicFairPrices() {
        return graphicFairPrices;
    }

    public void setGraphicFairPrices(Boolean graphicFairPrices) {
        this.graphicFairPrices = graphicFairPrices;
    }

    public Boolean getGraphicIncreaseOfIncome() {
        return graphicIncreaseOfIncome;
    }

    public void setGraphicIncreaseOfIncome(Boolean graphicIncreaseOfIncome) {
        this.graphicIncreaseOfIncome = graphicIncreaseOfIncome;
    }

    public Boolean getGraphicQuality() {
        return graphicQuality;
    }

    public void setGraphicQuality(Boolean graphicQuality) {
        this.graphicQuality = graphicQuality;
    }

    public FairPricesUnit getGraphicPriceToProducer() {
        return graphicPriceToProducer;
    }

    public void setGraphicPriceToProducer(FairPricesUnit graphicPriceToProducer) {
        this.graphicPriceToProducer = graphicPriceToProducer;
    }

    public FairPricesUnit getGraphicFarmGatePrice() {
        return graphicFarmGatePrice;
    }

    public void setGraphicFarmGatePrice(FairPricesUnit graphicFarmGatePrice) {
        this.graphicFarmGatePrice = graphicFarmGatePrice;
    }

    public BigDecimal getManualFarmGatePrice() {
        return manualFarmGatePrice;
    }

    public void setManualFarmGatePrice(BigDecimal manualFarmGatePrice) {
        this.manualFarmGatePrice = manualFarmGatePrice;
    }

    public BigDecimal getManualProducerPrice() {
        return manualProducerPrice;
    }

    public void setManualProducerPrice(BigDecimal manualProducerPrice) {
        this.manualProducerPrice = manualProducerPrice;
    }

    public BigDecimal getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(BigDecimal containerSize) {
        this.containerSize = containerSize;
    }

    public BigDecimal getWorldMarket() {
        return worldMarket;
    }

    public void setWorldMarket(BigDecimal worldMarket) {
        this.worldMarket = worldMarket;
    }

    public BigDecimal getFairTrade() {
        return fairTrade;
    }

    public void setFairTrade(BigDecimal fairTrade) {
        this.fairTrade = fairTrade;
    }

    public BigDecimal getAverageRegionFarmGatePrice() {
        return averageRegionFarmGatePrice;
    }

    public void setAverageRegionFarmGatePrice(BigDecimal averageRegionFarmGatePrice) {
        this.averageRegionFarmGatePrice = averageRegionFarmGatePrice;
    }

}
