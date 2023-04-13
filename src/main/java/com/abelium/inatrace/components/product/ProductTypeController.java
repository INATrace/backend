package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product-type")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Create a new product type")
    public ApiResponse<ApiBaseEntity> createProductType(
            @Valid @RequestBody ApiProductType apiProductType
    ) throws ApiException {
        return productTypeService.createProductType(apiProductType);
    }

    @GetMapping
    @ApiOperation(value = "Get a list of product types")
    public ApiPaginatedResponse<ApiProductType> getProductTypes(
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) {
        return productTypeService.getProductTypes(language);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get product type details")
    public ApiResponse<ApiProductType> getProductType(
            @RequestParam(value = "id") Long id,
            @RequestHeader(value = "language", defaultValue = "EN", required = false) Language language
    ) throws ApiException {
        return productTypeService.getProductType(id, language);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Update a product type")
    public ApiResponse<ApiBaseEntity> updateProductType(
            @Valid @RequestBody ApiProductType apiProductType
    ) throws ApiException {
        return productTypeService.updateProductType(apiProductType);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Delete a product type")
    public ApiDefaultResponse deleteProductType(
            @RequestParam(value = "id") Long id
    ) throws ApiException {
        return productTypeService.deleteProductType(id);
    }

}
