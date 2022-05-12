package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.common.Document;

import javax.persistence.*;

@Entity
@Table
public class BusinessToCustomerSettings extends TimestampEntity {

    @Version
    private Long entityVersion;

    @Column(name = "primary_color", length = Lengths.HEX_COLOR)
    private String primaryColor;

    @Column(name = "secondary_color", length = Lengths.HEX_COLOR)
    private String secondaryColor;

    @Column(name = "tertiary_color", length = Lengths.HEX_COLOR)
    private String tertiaryColor;

    @Column(name = "quaternary_color", length = Lengths.HEX_COLOR)
    private String quaternaryColor;

    @Column(name = "heading_color", length = Lengths.HEX_COLOR)
    private String headingColor;

    @Column(name = "text_color", length = Lengths.HEX_COLOR)
    private String textColor;

    @Column(name = "tab_fair_prices")
    private Boolean tabFairPrices;

    @Column(name = "tab_producers")
    private Boolean tabProducers;

    @Column(name = "tab_quality")
    private Boolean tabQuality;

    @Column(name = "tab_feedback")
    private Boolean tabFeedback;

    @OneToOne
    private Document productFont;

    @OneToOne
    private Document textFont;

    @OneToOne
    private Document headerImage;

    @OneToOne
    private Document headerBackgroundImage;

    @OneToOne
    private Document footerImage;

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

    public Document getProductFont() {
        return productFont;
    }

    public void setProductFont(Document productFont) {
        this.productFont = productFont;
    }

    public Document getTextFont() {
        return textFont;
    }

    public void setTextFont(Document textFont) {
        this.textFont = textFont;
    }

    public Document getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(Document headerImage) {
        this.headerImage = headerImage;
    }

    public Document getHeaderBackgroundImage() {
        return headerBackgroundImage;
    }

    public void setHeaderBackgroundImage(Document headerBackgroundImage) {
        this.headerBackgroundImage = headerBackgroundImage;
    }

    public Document getFooterImage() {
        return footerImage;
    }

    public void setFooterImage(Document footerImage) {
        this.footerImage = footerImage;
    }

    public BusinessToCustomerSettings copy() {
        BusinessToCustomerSettings businessToCustomerSettings = new BusinessToCustomerSettings();

        businessToCustomerSettings.setPrimaryColor(getPrimaryColor());
        businessToCustomerSettings.setSecondaryColor(getSecondaryColor());
        businessToCustomerSettings.setTertiaryColor(getTertiaryColor());
        businessToCustomerSettings.setQuaternaryColor(getQuaternaryColor());
        businessToCustomerSettings.setHeadingColor(getHeadingColor());
        businessToCustomerSettings.setTextColor(getTextColor());
        businessToCustomerSettings.setTabFairPrices(getTabFairPrices());
        businessToCustomerSettings.setTabProducers(getTabProducers());
        businessToCustomerSettings.setTabQuality(getTabQuality());
        businessToCustomerSettings.setTabFeedback(getTabFeedback());
        businessToCustomerSettings.setProductFont(getProductFont());
        businessToCustomerSettings.setTextFont(getTextFont());
        businessToCustomerSettings.setHeaderImage(getHeaderImage());
        businessToCustomerSettings.setHeaderBackgroundImage(getHeaderBackgroundImage());
        businessToCustomerSettings.setFooterImage(getFooterImage());

        return businessToCustomerSettings;
    }
}
