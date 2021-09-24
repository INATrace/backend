package com.abelium.inatrace.components.company.mappers;

import com.abelium.inatrace.components.common.mappers.CountryMapper;
import com.abelium.inatrace.components.company.api.ApiAddress;
import com.abelium.inatrace.db.entities.common.Address;

public class AddressMapper {

    public static ApiAddress toApiAddress(Address entity) {
        ApiAddress apiAddress = new ApiAddress();
        apiAddress.setAddress(entity.getAddress());
        apiAddress.setCell(entity.getCell());
        apiAddress.setCity(entity.getCity());
        apiAddress.setCountry(CountryMapper.toApiCountry(entity.getCountry()));
        apiAddress.setSector(entity.getSector());
        apiAddress.setState(entity.getState());
        apiAddress.setVillage(entity.getVillage());
        apiAddress.setZip(entity.getZip());
        return apiAddress;
    }

}
