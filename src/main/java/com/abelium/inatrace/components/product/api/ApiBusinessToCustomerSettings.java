package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiTimestampEntity;

public class ApiBusinessToCustomerSettings extends ApiTimestampEntity {

    private String primaryColor;
    private String secondaryColor;
    private String ternaryColor;
    private String headerColor;
    private String footerColor;

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

    public String getTernaryColor() {
        return ternaryColor;
    }

    public void setTernaryColor(String ternaryColor) {
        this.ternaryColor = ternaryColor;
    }

    public String getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public String getFooterColor() {
        return footerColor;
    }

    public void setFooterColor(String footerColor) {
        this.footerColor = footerColor;
    }
}
