package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;

public class ApiCompanyOnboardingState extends ApiBaseEntity {

    private Boolean hasCreatedProduct;

    private Boolean hasCreatedFacility;

    private Boolean hasCreatedProcessingAction;

    private Boolean hasAddedFarmers;

    public Boolean getHasCreatedProduct() {
        return hasCreatedProduct;
    }

    public void setHasCreatedProduct(Boolean hasCreatedProduct) {
        this.hasCreatedProduct = hasCreatedProduct;
    }

    public Boolean getHasCreatedFacility() {
        return hasCreatedFacility;
    }

    public void setHasCreatedFacility(Boolean hasCreatedFacility) {
        this.hasCreatedFacility = hasCreatedFacility;
    }

    public Boolean getHasCreatedProcessingAction() {
        return hasCreatedProcessingAction;
    }

    public void setHasCreatedProcessingAction(Boolean hasCreatedProcessingAction) {
        this.hasCreatedProcessingAction = hasCreatedProcessingAction;
    }

    public Boolean getHasAddedFarmers() {
        return hasAddedFarmers;
    }

    public void setHasAddedFarmers(Boolean hasAddedFarmers) {
        this.hasAddedFarmers = hasAddedFarmers;
    }

}
