package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.components.company.api.ApiCompanyDocument;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiProductLabelCompanyDocument extends ApiCompanyDocument {

    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
