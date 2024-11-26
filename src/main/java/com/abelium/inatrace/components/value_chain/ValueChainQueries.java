package com.abelium.inatrace.components.value_chain;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import com.abelium.inatrace.tools.Queries;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Lazy
@Component
public class ValueChainQueries extends BaseService {

    @Transactional
    public ValueChain fetchValueChain(Long companyId) throws ApiException {
        ValueChain valueChain = Queries.get(em, ValueChain.class, companyId);
        if (valueChain == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id");
        }
        return valueChain;
    }
}
