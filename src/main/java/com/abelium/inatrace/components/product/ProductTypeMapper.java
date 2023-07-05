package com.abelium.inatrace.components.product;

import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.components.product.api.ApiProductTypeTranslation;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import com.abelium.inatrace.db.entities.codebook.ProductTypeTranslation;
import com.abelium.inatrace.types.Language;

import java.util.ArrayList;

public class ProductTypeMapper {

    private ProductTypeMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ProductType toProductType(ApiProductType apiProductType) {
        ProductType productType = new ProductType();

        productType.setName(apiProductType.getName());
        productType.setDescription(apiProductType.getDescription());

        return productType;
    }

    public static ApiProductType toApiProductType(ProductType entity, Language language) {

        if (entity == null) {
            return null;
        }

        ProductTypeTranslation translation = entity.getProductTypeTranslations().stream()
                .filter(productTypeTranslation -> productTypeTranslation.getLanguage().equals(language))
                .findFirst().orElse(new ProductTypeTranslation());

        ApiProductType apiProductType = new ApiProductType();
        apiProductType.setId(entity.getId());
        apiProductType.setName(translation.getName());
        apiProductType.setCode(entity.getCode());
        apiProductType.setDescription(translation.getDescription());

        return apiProductType;
    }

    public static ApiProductType toApiProductTypeDetailed(ProductType entity, Language language) {
        if (entity == null) {
            return null;
        }

        ApiProductType apiProductType = toApiProductType(entity, language);
        apiProductType.setTranslations(new ArrayList<>());

        entity.getProductTypeTranslations().forEach(productTypeTranslation -> {
            ApiProductTypeTranslation apiProductTypeTranslation = new ApiProductTypeTranslation();
            apiProductTypeTranslation.setName(productTypeTranslation.getName());
            apiProductTypeTranslation.setDescription(productTypeTranslation.getDescription());
            apiProductTypeTranslation.setLanguage(productTypeTranslation.getLanguage());
            apiProductType.getTranslations().add(apiProductTypeTranslation);
        });

        return apiProductType;
    }
}
