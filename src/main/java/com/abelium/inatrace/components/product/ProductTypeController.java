package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product-type")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'REGIONAL_ADMIN')")
    @Operation(summary = "Create a new product type")
    public ApiResponse<ApiBaseEntity> createProductType(
            @Valid @RequestBody ApiProductType apiProductType
    ) throws ApiException {
        return productTypeService.createProductType(apiProductType);
    }

    @GetMapping
    @Operation(summary = "Get a list of product types")
    public ApiPaginatedResponse<ApiProductType> getProductTypes(
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) {
        return productTypeService.getProductTypes(language);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product type details")
    public ApiResponse<ApiProductType> getProductType(
            @RequestParam(value = "id") Long id,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) throws ApiException {
        return productTypeService.getProductType(id, language);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @Operation(summary = "Update a product type")
    public ApiResponse<ApiBaseEntity> updateProductType(
            @Valid @RequestBody ApiProductType apiProductType
    ) throws ApiException {
        return productTypeService.updateProductType(apiProductType);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @Operation(summary = "Delete a product type")
    public ApiDefaultResponse deleteProductType(
            @RequestParam(value = "id") Long id
    ) throws ApiException {
        return productTypeService.deleteProductType(id);
    }

}
