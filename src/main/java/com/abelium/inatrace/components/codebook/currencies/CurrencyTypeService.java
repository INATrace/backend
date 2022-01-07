package com.abelium.inatrace.components.codebook.currencies;

import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.currencies.CurrencyService;
import com.abelium.inatrace.components.currencies.api.ApiCurrencyTypeRequest;
import com.abelium.inatrace.db.entities.codebook.CurrencyType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

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

    public ApiPaginatedResponse<ApiCurrencyType> getCurrencyTypeList(Boolean enabled, ApiCurrencyTypeRequest request) {
        return new ApiPaginatedResponse<>(PaginationTools.createPaginatedResponse(em, request, () -> currencyTypeProxy(enabled, request), CurrencyTypeMapper::toApiCurrencyType));
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

    private CurrencyType currencyTypeProxy(Boolean enabled, ApiCurrencyTypeRequest request) {
        CurrencyType currencyTypeProxy = Torpedo.from(CurrencyType.class);

        OnGoingLogicalCondition condition = Torpedo.condition();

        if (enabled != null) {
            condition = condition.and(currencyTypeProxy.getEnabled()).eq(enabled);
        }

        if (request.getQuery() != null) {
            OnGoingLogicalCondition codeLikeQuery = Torpedo.condition(currencyTypeProxy.getCode()).like().any(request.getQuery());
            OnGoingLogicalCondition labelLikeQuery = Torpedo.condition(currencyTypeProxy.getLabel()).like().any(request.getQuery());
            condition = condition.and(codeLikeQuery.or(labelLikeQuery));
        }

        Torpedo.where(condition);

        switch (request.sortBy) {
            case "code":
                QueryTools.orderBy(request.sort, currencyTypeProxy.getCode());
                break;
            case "label":
                QueryTools.orderBy(request.sort, currencyTypeProxy.getLabel());
                break;
        }

        return currencyTypeProxy;
    }
}
