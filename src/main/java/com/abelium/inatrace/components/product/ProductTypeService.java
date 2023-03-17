package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Service
public class ProductTypeService extends BaseService {

    private static final String PRODUCT_TYPE_DOES_NOT_EXIST = "Product type does not exist!";

    @Transactional
    public ApiResponse<ApiProductType> createProductType(ApiProductType apiProductType) {

        ProductType productType = ProductTypeMapper.toProductType(apiProductType);

        em.persist(productType);

        return new ApiResponse<>(ProductTypeMapper.toApiProductType(productType));
    }

    public ApiPaginatedResponse<ApiProductType> getProductTypes() {

        List<ApiProductType> productTypeList = em.createQuery("SELECT pt FROM ProductType pt", ProductType.class)
                .getResultList()
                .stream()
                .map(ProductTypeMapper::toApiProductType)
                .collect(Collectors.toList());

        return new ApiPaginatedResponse<>(new ApiPaginatedList<>(productTypeList, productTypeList.size()));
    }

    public ApiResponse<ApiProductType> getProductType(Long id) throws ApiException {

        ProductType productType = em.find(ProductType.class, id);

        if (productType == null) {
            throw new ApiException(ApiStatus.NOT_FOUND, PRODUCT_TYPE_DOES_NOT_EXIST);
        }

        return new ApiResponse<>(ProductTypeMapper.toApiProductType(productType));
    }

    @Transactional
    public ApiResponse<ApiProductType> updateProductType(ApiProductType apiProductType) throws ApiException {

        if (apiProductType.getId() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Id must be provided!");
        }

        ProductType productType = em.find(ProductType.class, apiProductType.getId());

        if (productType == null) {
            throw new ApiException(ApiStatus.NOT_FOUND, PRODUCT_TYPE_DOES_NOT_EXIST);
        }

        productType.setName(apiProductType.getName());
        productType.setDescription(apiProductType.getDescription());

        return new ApiResponse<>(ProductTypeMapper.toApiProductType(productType));
    }

    @Transactional
    public ApiDefaultResponse deleteProductType(Long id) throws ApiException {

        if (id == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Id must be provided!");
        }

        ProductType productType = em.find(ProductType.class, id);

        if (productType == null) {
            throw new ApiException(ApiStatus.NOT_FOUND, PRODUCT_TYPE_DOES_NOT_EXIST);
        }

        em.remove(productType);

        return new ApiDefaultResponse();
    }

}
