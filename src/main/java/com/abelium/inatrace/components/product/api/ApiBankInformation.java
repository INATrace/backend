package com.abelium.inatrace.components.product.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;


@Validated
public class ApiBankInformation {

    @Schema(description = "Account holder name")
    public String accountHolderName;

    @Schema(description = "Account number")
    public String accountNumber;

    @Schema(description = "Bank name")
    public String bankName;

    @Schema(description = "Additional information")
    public String additionalInformation;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
