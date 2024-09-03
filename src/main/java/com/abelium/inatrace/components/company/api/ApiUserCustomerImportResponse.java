package com.abelium.inatrace.components.company.api;

import java.util.ArrayList;
import java.util.List;

public class ApiUserCustomerImportResponse {

    private Integer successful;
    private List<ApiUserCustomer> duplicates;

    private List<ApiUserCustomerImportRowValidationError> validationErrors;

    public Integer getSuccessful() {
        return successful;
    }

    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }

    public List<ApiUserCustomer> getDuplicates() {
        return duplicates;
    }

    public void setDuplicates(List<ApiUserCustomer> duplicates) {
        this.duplicates = duplicates;
    }

    public List<ApiUserCustomerImportRowValidationError> getValidationErrors() {
        if (validationErrors == null) {
            validationErrors = new ArrayList<ApiUserCustomerImportRowValidationError>();
        }
        return validationErrors;
    }

}
