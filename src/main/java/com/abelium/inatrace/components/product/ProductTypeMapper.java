package com.abelium.inatrace.components.product;

import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.db.entities.product.ProductType;

public class ProductTypeMapper {

    private ProductTypeMapper() {
        throw new IllegalStateException("Utility class");
    }

    static ProductType toProductType(ApiProductType apiProductType) {
        ProductType productType = new ProductType();

        productType.setName(apiProductType.getName());
        productType.setDescription(apiProductType.getDescription());

        return productType;
    }

    static ApiProductType toApiProductType(ProductType productType) {
        ApiProductType apiProductType = new ApiProductType();

        apiProductType.setId(productType.getId());
        apiProductType.setName(productType.getName());
        apiProductType.setDescription(productType.getDescription());

        return apiProductType;
    }
}
