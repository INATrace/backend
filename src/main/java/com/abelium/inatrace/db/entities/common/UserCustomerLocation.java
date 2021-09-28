package com.abelium.inatrace.db.entities.common;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserCustomerLocation extends Location {

    @Column
    private Boolean isPubliclyVisible;

    public Boolean getPubliclyVisible() {
        return isPubliclyVisible;
    }

    public void setPubliclyVisible(Boolean publiclyVisible) {
        isPubliclyVisible = publiclyVisible;
    }
}
