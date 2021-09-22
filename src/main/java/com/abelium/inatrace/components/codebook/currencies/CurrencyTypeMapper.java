package com.abelium.inatrace.components.codebook.currencies;

import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.db.entities.codebook.CurrencyType;

import java.util.List;
import java.util.stream.Collectors;

public final class CurrencyTypeMapper {

    private CurrencyTypeMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<ApiCurrencyType> toApiCurrencyTypeList(List<CurrencyType> currencyTypeList) {
        return currencyTypeList.stream().map(CurrencyTypeMapper::toApiCurrencyType).collect(Collectors.toList());
    }

    public static ApiCurrencyType toApiCurrencyType(CurrencyType currencyType) {
        ApiCurrencyType apiCurrencyType = new ApiCurrencyType();
        if (currencyType != null) {
            apiCurrencyType.setId(currencyType.getId());
            apiCurrencyType.setCode(currencyType.getCode());
            apiCurrencyType.setLabel(currencyType.getLabel());
            apiCurrencyType.setEnabled(currencyType.getEnabled());
        }
        return apiCurrencyType;
    }
}
