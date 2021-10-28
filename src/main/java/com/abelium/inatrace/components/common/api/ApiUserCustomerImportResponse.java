package com.abelium.inatrace.components.common.api;

import com.abelium.inatrace.components.company.api.ApiUserCustomer;

import java.util.List;

public class ApiUserCustomerImportResponse {

    private Integer successful;
    private List<ApiUserCustomer> duplicates;

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
}
