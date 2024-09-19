package com.abelium.inatrace.components.product;

import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.company.api.ApiUserCustomerAssociation;
import com.abelium.inatrace.components.company.api.ApiUserCustomerCooperative;
import com.abelium.inatrace.components.company.api.ApiUserCustomerLocation;
import com.abelium.inatrace.db.entities.common.*;
import com.abelium.inatrace.db.entities.company.Company;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ProductMapper extends BaseService {

    public void updateUserCustomer(UserCustomer userCustomer, ApiUserCustomer apiUserCustomer) {
        userCustomer.setName(apiUserCustomer.getName());
        userCustomer.setSurname(apiUserCustomer.getSurname());
        userCustomer.setGender(apiUserCustomer.getGender());
        userCustomer.setType(apiUserCustomer.getType());
        userCustomer.setPhone(apiUserCustomer.getPhone());
        userCustomer.setEmail(apiUserCustomer.getEmail());
        userCustomer.setHasSmartphone(apiUserCustomer.getHasSmartphone());
        // Bank
        if (userCustomer.getBank() == null) {
            userCustomer.setBank(new BankInformation());
        }
        if (apiUserCustomer.getBank() != null) {
            userCustomer.getBank().setBankName(apiUserCustomer.getBank().getBankName());
            userCustomer.getBank().setAccountNumber(apiUserCustomer.getBank().getAccountNumber());
            userCustomer.getBank().setAccountHolderName(apiUserCustomer.getBank().getAccountHolderName());
            userCustomer.getBank().setAdditionalInformation(apiUserCustomer.getBank().getAdditionalInformation());
        }
        // Farm
        if (userCustomer.getFarm() == null) {
            userCustomer.setFarm(new FarmInformation());
        }
        if (apiUserCustomer.getFarm() != null) {
            userCustomer.getFarm().setAreaUnit(apiUserCustomer.getFarm().getAreaUnit());
            userCustomer.getFarm().setTotalCultivatedArea(apiUserCustomer.getFarm().getTotalCultivatedArea());
            userCustomer.getFarm().setOrganic(apiUserCustomer.getFarm().getOrganic());
            userCustomer.getFarm().setAreaOrganicCertified(apiUserCustomer.getFarm().getAreaOrganicCertified());
            userCustomer.getFarm().setStartTransitionToOrganic(apiUserCustomer.getFarm().getStartTransitionToOrganic());
        }
        // Cooperatives
        if (userCustomer.getCooperatives() == null) {
            userCustomer.setCooperatives(new HashSet<>());
        }
        userCustomer.getCooperatives().removeIf(userCustomerCooperative -> apiUserCustomer.getCooperatives().stream().noneMatch(apiUserCustomerCooperative -> userCustomerCooperative.getId().equals(apiUserCustomerCooperative.getId())));
        for (ApiUserCustomerCooperative apiUserCustomerCooperative : apiUserCustomer.getCooperatives()) {
            UserCustomerCooperative userCustomerCooperative;
            if (apiUserCustomerCooperative.getId() == null) {
                userCustomerCooperative = new UserCustomerCooperative();
            } else {
                userCustomerCooperative = em.find(UserCustomerCooperative.class, apiUserCustomerCooperative.getId());
            }
            userCustomerCooperative.setUserCustomer(userCustomer);
            userCustomerCooperative.setCompany(getCompany(apiUserCustomerCooperative.getCompany().getId()));
            userCustomerCooperative.setRole(apiUserCustomerCooperative.getUserCustomerType());
            userCustomer.getCooperatives().add(userCustomerCooperative);
        }
        // Associations
        if (userCustomer.getAssociations() == null) {
            userCustomer.setAssociations(new HashSet<>());
        }
        userCustomer.getAssociations().removeIf(userCustomerAssociation -> apiUserCustomer.getAssociations().stream().noneMatch(apiUserCustomerAssociation -> userCustomerAssociation.getId().equals(apiUserCustomerAssociation.getId())));
        for (ApiUserCustomerAssociation apiUserCustomerAssociation : apiUserCustomer.getAssociations()) {
            UserCustomerAssociation userCustomerAssociation;
            if (apiUserCustomerAssociation.getId() == null) {
                userCustomerAssociation = new UserCustomerAssociation();
            } else {
                userCustomerAssociation = em.find(UserCustomerAssociation.class, apiUserCustomerAssociation.getId());
            }
            userCustomerAssociation.setUserCustomer(userCustomer);
            userCustomerAssociation.setCompany(getCompany(apiUserCustomerAssociation.getCompany().getId()));
            userCustomer.getAssociations().add(userCustomerAssociation);
        }
    }

    public void updateUserCustomerLocation(UserCustomerLocation userCustomerLocation, ApiUserCustomerLocation apiUserCustomerLocation) {
        if (userCustomerLocation == null) {
            userCustomerLocation = new UserCustomerLocation();
        }
        if (userCustomerLocation.getAddress() == null) {
            userCustomerLocation.setAddress(new Address());
        }
        if (apiUserCustomerLocation.getAddress() != null) {
            userCustomerLocation.getAddress().setAddress(apiUserCustomerLocation.getAddress().getAddress());
            userCustomerLocation.getAddress().setCell(apiUserCustomerLocation.getAddress().getCell());
            userCustomerLocation.getAddress().setCity(apiUserCustomerLocation.getAddress().getCity());
            userCustomerLocation.getAddress().setCountry(getCountry(apiUserCustomerLocation.getAddress().getCountry().getId()));
            userCustomerLocation.getAddress().setHondurasDepartment(apiUserCustomerLocation.getAddress().getHondurasDepartment());
            userCustomerLocation.getAddress().setHondurasFarm(apiUserCustomerLocation.getAddress().getHondurasFarm());
            userCustomerLocation.getAddress().setHondurasMunicipality(apiUserCustomerLocation.getAddress().getHondurasMunicipality());
            userCustomerLocation.getAddress().setHondurasVillage(apiUserCustomerLocation.getAddress().getHondurasVillage());
            userCustomerLocation.getAddress().setSector(apiUserCustomerLocation.getAddress().getSector());
            userCustomerLocation.getAddress().setState(apiUserCustomerLocation.getAddress().getState());
            userCustomerLocation.getAddress().setVillage(apiUserCustomerLocation.getAddress().getVillage());
            userCustomerLocation.getAddress().setOtherAddress(apiUserCustomerLocation.getAddress().getOtherAddress());
            userCustomerLocation.getAddress().setZip(apiUserCustomerLocation.getAddress().getZip());
            userCustomerLocation.setLatitude(apiUserCustomerLocation.getLatitude());
            userCustomerLocation.setLongitude(apiUserCustomerLocation.getLongitude());
            userCustomerLocation.setPubliclyVisible(apiUserCustomerLocation.getPubliclyVisible());
        }
    }

    private Company getCompany(Long id) {
        return em.find(Company.class, id);
    }

    private Country getCountry(Long id) {
        return em.find(Country.class, id);
    }

}
