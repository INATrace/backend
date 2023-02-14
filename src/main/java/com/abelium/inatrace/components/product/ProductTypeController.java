package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.product.api.ApiProductType;
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
    public ApiResponse<ApiProductType> createProductType(
            @Valid @RequestBody ApiProductType apiProductType
    ) {
        return productTypeService.createProductType(apiProductType);
    }

    @GetMapping
    public ApiPaginatedResponse<ApiProductType> getProductTypes() {
        return productTypeService.getProductTypes();
    }

    @GetMapping("/{id}")
    public ApiResponse<ApiProductType> getProductType(
            @RequestParam(value = "id") Long id
    ) throws ApiException {
        return productTypeService.getProductType(id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<ApiProductType> updateProductType(
            @Valid @RequestBody ApiProductType apiProductType
    ) throws ApiException {
        return productTypeService.updateProductType(apiProductType);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiDefaultResponse deleteProductType(
            @RequestParam(value = "id") Long id
    ) throws ApiException {
        return productTypeService.deleteProductType(id);
    }

}
