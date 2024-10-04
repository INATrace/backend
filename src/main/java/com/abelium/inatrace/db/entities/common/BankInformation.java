package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.api.types.Lengths;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public class BankInformation {

    @Column(length = Lengths.DEFAULT)
    private String accountHolderName;

    @Column(length = Lengths.DEFAULT)
    private String accountNumber;

    @Column(length = Lengths.DEFAULT)
    private String bankName;

    @Column(length = Lengths.DEFAULT)
    private String additionalInformation;

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
