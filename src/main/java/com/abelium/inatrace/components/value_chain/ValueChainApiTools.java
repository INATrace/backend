package com.abelium.inatrace.components.value_chain;

import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Lazy
@Component
public class ValueChainApiTools {

    public ApiValueChain toApiValueChain(ValueChain vc) {
        if (vc == null) return null;

        ApiValueChain avc = new ApiValueChain();
        updateApiValueChain(avc, vc);
        return avc;
    }

    public void updateApiValueChain(ApiValueChain avc, ValueChain vc) {
        CommonApiTools.updateApiBaseEntity(avc, vc);
        avc.setName(vc.getName());
        avc.setDescription(vc.getDescription());
        avc.setValueChainStatus(vc.getValueChainStatus());
    }
}
