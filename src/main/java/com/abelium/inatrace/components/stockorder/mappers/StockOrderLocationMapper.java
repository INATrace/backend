package com.abelium.inatrace.components.stockorder.mappers;

import com.abelium.inatrace.components.company.mappers.AddressMapper;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrderLocation;
import com.abelium.inatrace.db.entities.stockorder.StockOrderLocation;

public class StockOrderLocationMapper {

    public static ApiStockOrderLocation toApiStockOrderLocation(StockOrderLocation entity) {
        if(entity == null) return null;
        ApiStockOrderLocation apiStockOrderLocation = new ApiStockOrderLocation();
        apiStockOrderLocation.setId(entity.getId());
        apiStockOrderLocation.setAddress(AddressMapper.toApiAddress(entity.getAddress()));
        apiStockOrderLocation.setLatitude(entity.getLatitude());
        apiStockOrderLocation.setLongitude(entity.getLongitude());
        apiStockOrderLocation.setNumberOfFarmers(entity.getNumberOfFarmers());
        apiStockOrderLocation.setPinName(entity.getPinName());
        return apiStockOrderLocation;
    }
}
