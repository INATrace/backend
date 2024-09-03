package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.company.types.UserCustomerImportCellErrorType;

public class ApiUserCustomerImportColumnValidationError {

    private String cellAddress;

    private UserCustomerImportCellErrorType errorType;

    public ApiUserCustomerImportColumnValidationError(String cellAddress, UserCustomerImportCellErrorType errorType) {
        this.cellAddress = cellAddress;
        this.errorType = errorType;
    }

    public String getCellAddress() {
        return cellAddress;
    }

    public void setCellAddress(String cellAddress) {
        this.cellAddress = cellAddress;
    }

    public UserCustomerImportCellErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(UserCustomerImportCellErrorType errorType) {
        this.errorType = errorType;
    }

}
