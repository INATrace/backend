package com.abelium.inatrace.components.codebook.currencies.api;

import com.abelium.inatrace.api.ApiCodebookBaseEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public class ApiCurrencyType extends ApiCodebookBaseEntity {

    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
