package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.company.Company;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class UserCustomerAssociation extends BaseEntity {

    @ManyToOne
    private UserCustomer userCustomer;

    @ManyToOne
    private Company company;

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
}
