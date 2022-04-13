package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.TimestampEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
public class BusinessToCustomerSettings extends TimestampEntity {

    @Version
    private Long entityVersion;

    @Column(name = "primary_color", length = Lengths.HEX_COLOR_ALPHA)
    private String primaryColor;

    @Column(name = "secondary_color", length = Lengths.HEX_COLOR_ALPHA)
    private String secondaryColor;

    @Column(name = "ternary_color", length = Lengths.HEX_COLOR_ALPHA)
    private String ternaryColor;

    @Column(name = "header_color", length = Lengths.HEX_COLOR_ALPHA)
    private String headerColor;

    @Column(name = "footer_color", length = Lengths.HEX_COLOR_ALPHA)
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
