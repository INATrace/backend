package com.abelium.inatrace.components.company.api;

import java.util.ArrayList;
import java.util.List;

public class ApiUserCustomerImportRowValidationError {

    private int rowIndex;

    private List<ApiUserCustomerImportColumnValidationError> columnValidationErrors;

    public ApiUserCustomerImportRowValidationError(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public List<ApiUserCustomerImportColumnValidationError> getColumnValidationErrors() {
        if (columnValidationErrors == null) {
            columnValidationErrors = new ArrayList<ApiUserCustomerImportColumnValidationError>();
        }
        return columnValidationErrors;
    }

}
