package com.abelium.inatrace.components.codebook.currencies;

import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.CurrencyType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Service
public class CurrencyTypeService extends BaseService {

    public List<ApiCurrencyType> getCurrencyTypeList() {
        List<CurrencyType> currencyTypeList = em.createNamedQuery("CurrencyType.getAllCurrencyTypes", CurrencyType.class).getResultList();
        return CurrencyTypeMapper.toApiCurrencyTypeList(currencyTypeList);
    }

    public CurrencyType getCurrencyType(Long id) {
        return em.find(CurrencyType.class, id);
    }

    public CurrencyType getCurrencyTypeByCode(String code) {
        return em.createNamedQuery("CurrencyType.getCurrencyTypeByCode", CurrencyType.class).setParameter("code", code).getSingleResult();
    }
}
