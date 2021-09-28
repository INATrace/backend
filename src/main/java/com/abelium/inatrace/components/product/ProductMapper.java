package com.abelium.inatrace.components.product;

import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.product.api.ApiUserCustomer;
import com.abelium.inatrace.components.product.api.ApiUserCustomerAssociation;
import com.abelium.inatrace.db.entities.common.Country;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.common.UserCustomerAssociation;
import com.abelium.inatrace.db.entities.common.UserCustomerLocation;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends BaseService {

    public UserCustomer toUserCustomer(ApiUserCustomer apiUserCustomer) {
        UserCustomer userCustomer = new UserCustomer();
        userCustomer.setCompany(new Company());
        userCustomer.getCompany().setId(apiUserCustomer.getCompanyId());
        userCustomer.setProduct(new Product());
        userCustomer.getProduct().setId(apiUserCustomer.getProductId());
        userCustomer.setUserCustomerLocation(new UserCustomerLocation());
        userCustomer.getUserCustomerLocation().setPubliclyVisible(apiUserCustomer.getLocation().getPubliclyVisible());
        return userCustomer;
    }

    public UserCustomerAssociation toUserCustomerAssociation(ApiUserCustomerAssociation apiUserCustomerAssociation) {
        UserCustomerAssociation userCustomerAssociation = new UserCustomerAssociation();
        userCustomerAssociation.setCompany(getCompany(apiUserCustomerAssociation.getCompany().getId()));
        userCustomerAssociation.setUserCustomer(getUserCustomer(apiUserCustomerAssociation.getUserCustomer().getId()));
        return userCustomerAssociation;
    }

    private Company getCompany(Long id) {
        return em.find(Company.class, id);
    }

    private Country getCountry(Long id) {
        return em.find(Country.class, id);
    }

    private UserCustomer getUserCustomer(Long id) {
        return em.find(UserCustomer.class, id);
    }
}
