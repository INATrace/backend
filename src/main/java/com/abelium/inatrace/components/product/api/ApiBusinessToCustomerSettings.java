package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiTimestampEntity;
import com.abelium.inatrace.components.common.api.ApiDocument;

public class ApiBusinessToCustomerSettings extends ApiTimestampEntity {

    private String primaryColor;
    private String secondaryColor;
    private String headingColor;
    private String textColor;
    private Boolean tabFairPrices;
    private Boolean tabProducers;
    private Boolean tabQuality;
    private Boolean tabFeedback;
    private ApiDocument font;
    private ApiDocument headerImage;
    private ApiDocument footerImage;

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

    public ApiDocument getFont() {
        return font;
    }

    public void setFont(ApiDocument font) {
        this.font = font;
    }

    public ApiDocument getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(ApiDocument headerImage) {
        this.headerImage = headerImage;
    }

    public ApiDocument getFooterImage() {
        return footerImage;
    }

    public void setFooterImage(ApiDocument footerImage) {
        this.footerImage = footerImage;
    }
}
