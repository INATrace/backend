package com.abelium.inatrace.components.codebook.currencies;

import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.currencies.CurrencyService;
import com.abelium.inatrace.db.entities.codebook.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Lazy
@Service
public class CurrencyTypeService extends BaseService {

    @Autowired
    private CurrencyService currencyService;

    public List<ApiCurrencyType> getCurrencyTypeList() {
        List<CurrencyType> currencyTypeList = em.createNamedQuery("CurrencyType.getAllCurrencyTypes", CurrencyType.class).getResultList();
        return CurrencyTypeMapper.toApiCurrencyTypeList(currencyTypeList);
    }

    public List<ApiCurrencyType> getEnabledCurrencyTypeList() {
        List<CurrencyType> enabledCurrencyTypeList = em.createNamedQuery("CurrencyType.getEnabledCurrencyTypes", CurrencyType.class).getResultList();
        return CurrencyTypeMapper.toApiCurrencyTypeList(enabledCurrencyTypeList);
    }

    public List<ApiCurrencyType> getDisabledCurrencyTypeList() {
        List<CurrencyType> disabledCurrencyTypeList = em.createNamedQuery("CurrencyType.getDisabledCurrencyTypes", CurrencyType.class).getResultList();
        return CurrencyTypeMapper.toApiCurrencyTypeList(disabledCurrencyTypeList);
    }

    public CurrencyType getCurrencyType(Long id) {
        return em.find(CurrencyType.class, id);
    }

    public CurrencyType getCurrencyTypeByCode(String code) {
        return em.createNamedQuery("CurrencyType.getCurrencyTypeByCode", CurrencyType.class).setParameter("code", code).getSingleResult();
    }

    @Transactional
    public void updateStatus(Long id, Boolean enabled) {
        CurrencyType currencyType = em.find(CurrencyType.class, id);
        currencyType.setEnabled(enabled);
        if (enabled) {
            currencyService.updateCurrencies();
        }
    }
}
