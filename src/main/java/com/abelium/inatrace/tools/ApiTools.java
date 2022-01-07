package com.abelium.inatrace.tools;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.db.base.BaseEntity;

public class ApiTools {
	
    public static <DT extends BaseEntity, AT extends ApiBaseEntity> void updateOneToManyList(List<DT> dbList, List<AT> apiList,
            Supplier<DT> dbEntityCreator, BiConsumer<DT, AT> itemUpdate) {
        if (apiList == null || dbList == null) {
            return;
        }
        
        Map<Long, AT> idToApiItem = apiList.stream().filter(apiItem -> apiItem.getId() != null).
                collect(Collectors.toMap(AT::getId, Function.identity()));
        
        dbList.removeIf(dbItem -> !idToApiItem.containsKey(dbItem.getId()));
        for (DT dbItem : dbList) {
            itemUpdate.accept(dbItem, idToApiItem.get(dbItem.getId()));
        }
        for (AT apiItem : apiList) {
            if (apiItem.getId() == null) {
                DT lc = dbEntityCreator.get();
                itemUpdate.accept(lc, apiItem);
                dbList.add(lc);
            }
        }        
    }	
}
