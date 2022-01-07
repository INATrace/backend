package com.abelium.inatrace.components.product.mapper;

import com.abelium.inatrace.components.company.mappers.AddressMapper;
import com.abelium.inatrace.components.product.api.ApiLocation;
import com.abelium.inatrace.db.entities.common.Location;

public class LocationMapper {

    public static ApiLocation toApiLocation(Location entity) {

        if(entity == null) return null;
        ApiLocation apiLocation = new ApiLocation();
        apiLocation.setAddress(AddressMapper.toApiAddress(entity.getAddress()));
        apiLocation.setLatitude(entity.getLatitude());
        apiLocation.setLongitude(entity.getLongitude());
        apiLocation.setPinName(entity.getPinName());
        apiLocation.setNumberOfFarmers(entity.getNumberOfFarmers());
        return apiLocation;
    }

}
