package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.types.UserCustomerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class UserCustomerCooperative extends BaseEntity {

    @ManyToOne
    private UserCustomer userCustomer;

    @ManyToOne
    private Company company;

    @Column
    private UserCustomerType role;

    public UserCustomer getUserCustomer() {
        return userCustomer;
    }

    public void setUserCustomer(UserCustomer userCustomer) {
        this.userCustomer = userCustomer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public UserCustomerType getRole() {
        return role;
    }

    public void setRole(UserCustomerType role) {
        this.role = role;
    }
}
