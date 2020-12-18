package com.abelium.INATrace.tools;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.torpedoquery.jpa.Torpedo;

import com.abelium.INATrace.api.ApiPaginatedList;
import com.abelium.INATrace.api.ApiPaginatedRequest;
import com.abelium.INATrace.types.PaginatedRequestType;


public class PaginationTools 
{
    public static <T> ApiPaginatedList<T> createPaginatedResponse(PaginatedRequestType  type, 
            Supplier<List<T>> itemListSupplier, Supplier<Long> countSupplier) {
        List<T> items = (type == null || type == PaginatedRequestType.FETCH) ? 
            itemListSupplier.get() : Collections.emptyList();
        Long count = (type == null || type == PaginatedRequestType.COUNT) ?
            countSupplier.get() : null;
        return new ApiPaginatedList<>(items, count);
    }
    
    public static <DBT, APIT> ApiPaginatedList<APIT> createPaginatedResponse(EntityManager em, ApiPaginatedRequest request, 
            Supplier<DBT> proxyObjectSupplier, Function<DBT, APIT> typeConverter) {
        List<APIT> items;
        long count;
        
        if (request.requestType == PaginatedRequestType.COUNT) {
            items = Collections.emptyList();
        } else {
            List<DBT> dbItems = Torpedo.select(proxyObjectSupplier.get()).
                    setFirstResult(request.offset).setMaxResults(request.limit).list(em);
            items = dbItems.stream().map(typeConverter).collect(Collectors.toList());
        }
        if (request.requestType == PaginatedRequestType.FETCH) {
            count = 0;
        } else {
            count = Torpedo.select(Torpedo.count(proxyObjectSupplier.get())).get(em).orElse(0L);
        }
        return new ApiPaginatedList<>(items, count);
    }
    
    public static <DBT, APIT> ApiPaginatedList<APIT> createPaginatedResponse1(EntityManager em, ApiPaginatedRequest request, 
            Supplier<org.torpedoquery.jpa.Function<DBT>> proxyObjectSupplier, Function<DBT, APIT> typeConverter) {
        List<APIT> items;
        long count;
        
        if (request.requestType == PaginatedRequestType.COUNT) {
            items = Collections.emptyList();
        } else {
            List<DBT> dbItems = Torpedo.select(proxyObjectSupplier.get()).
                    setFirstResult(request.offset).setMaxResults(request.limit).list(em);
            items = dbItems.stream().map(typeConverter).collect(Collectors.toList());
        }
        if (request.requestType == PaginatedRequestType.FETCH) {
            count = 0;
        } else {
            count = Torpedo.select(Torpedo.count(proxyObjectSupplier.get())).get(em).orElse(0L);
        }
        return new ApiPaginatedList<>(items, count);
    }
    

    public static <DBT, APIT> ApiPaginatedList<APIT> createPaginatedResponse(EntityManager em, ApiPaginatedRequest request, 
    		Supplier<TorpedoProjector<DBT, APIT>> projectorSupplier) {
        List<APIT> items;
        long count;
        
        if (request.requestType == PaginatedRequestType.COUNT) items = Collections.emptyList();
        else items = projectorSupplier.get().list(em, request.offset, request.limit);

        if (request.requestType == PaginatedRequestType.FETCH) count = 0;
        else count = projectorSupplier.get().count(em);

        return new ApiPaginatedList<>(items, count);
    }
    
    
	//    public static <T> ApiPaginatedList<T> createPaginatedResponse(List<T> values, ApiPaginatableQueryStringRequest request) {
	//        List<T> items;
	//        long count;
	//        
	//        if (request.requestType == PaginatedRequestType .COUNT) {
	//            items = Collections.emptyList();
	//        } else {
	//            items = values;
	//        }
	//        if (request.requestType == PaginatedRequestType .FETCH) {
	//            count = 0;
	//        } else {
	//            count = items.size();
	//        }
	//        return new ApiPaginatedList<>(values, count);
	//    }
    
    public static <DBT, APIT> ApiPaginatedList<APIT> createPaginatedResponse(EntityManager em, ApiPaginatedRequest request,
            CriteriaQuery<DBT> query, CriteriaQuery<Long> queryCount, Function<DBT, APIT> typeConverter) {
        List<APIT> items;
        long count;
        
        if (request.requestType == PaginatedRequestType.COUNT) {
            items = Collections.emptyList();
        } else {
            List<DBT> dbItems = em.createQuery(query).
                    setFirstResult(request.offset).setMaxResults(request.limit).getResultList();
            items = dbItems.stream().map(typeConverter).collect(Collectors.toList());
        }
        if (request.requestType == PaginatedRequestType.FETCH) {
            count = 0;
        } else {
            count = em.createQuery(queryCount).getSingleResult();
        }
        return new ApiPaginatedList<>(items, count);
    }
    
	//    public static <T extends Enum<T> & HasLabel> ApiPaginatedList<ApiEnumWithLabel<T>> createPaginatedEnumResponse(Class<T> clazz, ApiPaginatableQueryStringRequest request) {
	//        List<T> values = Arrays.asList(clazz.getEnumConstants());
	//        return createPaginatedEnumResponse(values, request);
	//    }
	//    
	//    public static <T extends Enum<T> & HasLabel> ApiPaginatedList<ApiEnumWithLabel<T>> createPaginatedEnumResponse(List<T> values, ApiPaginatableQueryStringRequest request) {        
	//        if (StringUtils.isNotBlank(request.queryString)) {
	//            values = values.stream().filter(v -> StringUtils.containsIgnoreCase(v.label(), request.queryString)).
	//                collect(Collectors.toList());
	//        }
	//        if (request.sortBy == GeneralSortBy.NAME) {
	//            if (request.sort == SortDirection.ASC) { 
	//                values.sort((s1, s2) -> s1.compareTo(s2));
	//            } else {
	//                values.sort((s1, s2) -> s2.compareTo(s1));
	//            }
	//        }
	//        List<ApiEnumWithLabel<T>> items = values.stream().
	//                skip(request.offset).
	//                limit(request.limit).
	//                map(ApiEnumWithLabel::new).
	//                collect(Collectors.toList());
	//        return new ApiPaginatedList<>(items, items.size());
	//    }
    
}
