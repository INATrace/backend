package com.abelium.inatrace.db.entities.payment;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.common.Country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table
public class BankAccountInfo extends BaseEntity {

	@Version
	private Long entityVersion;
	
	@Column
	private String accountHoldersName;
	
	@Column
	private String accountNumber;
	
	@Column
	private String bankName;
	
	@Column
	private String string;
	
	@ManyToOne
	private Country country;

	public String getAccountHoldersName() {
		return accountHoldersName;
	}

	public void setAccountHoldersName(String accountHoldersName) {
		this.accountHoldersName = accountHoldersName;
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

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}
