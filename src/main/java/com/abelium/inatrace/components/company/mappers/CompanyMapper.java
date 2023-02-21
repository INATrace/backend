package com.abelium.inatrace.components.company.mappers;

import com.abelium.inatrace.components.codebook.currencies.CurrencyTypeMapper;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.db.entities.company.Company;

public class CompanyMapper {

    public static ApiCompany toApiCompanyBase(Company entity) {
        if (entity == null) return null;

        ApiCompany apiCompany = new ApiCompany();
        apiCompany.setId(entity.getId());
        apiCompany.setName(entity.getName());
        apiCompany.setEmail(entity.getEmail());
        apiCompany.setAbbreviation(entity.getAbbreviation());
        apiCompany.setCurrency(CurrencyTypeMapper.toApiCurrencyType(entity.getCurrency()));
        apiCompany.setAllowBeycoIntegration(entity.getAllowBeycoIntegration());

        return apiCompany;
    }

    public static ApiCompany toApiCompanyId(Company entity) {
        if (entity == null) return null;

        ApiCompany apiCompany = new ApiCompany();
        apiCompany.setId(entity.getId());

        return apiCompany;
    }

}
