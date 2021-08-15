package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.product.api.*;
import com.abelium.inatrace.components.product.types.ProductLabelAction;
import com.abelium.inatrace.security.service.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productEngine;
	
	@Autowired
	private ProductDocumentService productDocumentEngine;

	
    @PostMapping(value = "/create")
    @ApiOperation(value = "Create a new product")
    public ApiResponse<ApiBaseEntity> createProduct(@AuthenticationPrincipal CustomUserDetails authUser, 
    		@Valid @RequestBody ApiProduct request) throws ApiException {
		return new ApiResponse<>(productEngine.createProduct(authUser.getUserId(), request));
    }
    
    @GetMapping(value = "/list")
    @ApiOperation(value = "Lists all products. Sorting: name or default")
    public ApiPaginatedResponse<ApiProductListResponse> listProducts(@AuthenticationPrincipal CustomUserDetails authUser, 
    		@Valid ApiListProductsRequest request) {
    	return new ApiPaginatedResponse<>(productEngine.listUserProducts1(authUser.getUserId(), request));
    }
    
    @GetMapping(value = "/admin/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Lists all products. Must be admin. Sorting: name or default")
    public ApiPaginatedResponse<ApiProductListResponse> listProductsAdmin(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid ApiListProductsRequest request) {
    	return new ApiPaginatedResponse<>(productEngine.listProducts(authUser.getUserId(), request));
    }
    
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get all info about a product")
    public ApiResponse<ApiProduct> getProduct(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true) @PathVariable("id") Long id,
    		@Valid @RequestParam(value = "includeLabels", defaultValue = "false") boolean includeLabels) throws ApiException {
    	return new ApiResponse<>(productEngine.getProduct(authUser, id, includeLabels));
    }
    
    @PutMapping(value = "/")
    @ApiOperation(value = "Update product data")
    public ApiDefaultResponse updateProduct(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiProduct product) throws ApiException {
    	productEngine.updateProduct(authUser, product);
    	return new ApiDefaultResponse();
    }
    
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deletes a product")
    public ApiDefaultResponse deleteProduct(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true)  @PathVariable("id") Long id) throws ApiException {
    	productEngine.deleteProduct(authUser, id);
    	return new ApiDefaultResponse();
    }

    @PostMapping(value = "/label/create")
    @ApiOperation(value = "Create a new product label")
    public ApiResponse<ApiBaseEntity> createProductLabel(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiProductLabel request) throws ApiException {
		return new ApiResponse<>(productEngine.createProductLabel(authUser, request));
    }
    
    @GetMapping(value = "/label/{id}")
    @ApiOperation(value = "Get label data")
    public ApiResponse<ApiProductLabel> getProductLabel(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Record id", required = true) @PathVariable("id") Long id) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabel(authUser, id));
    }

    @PutMapping(value = "/label")
    @ApiOperation(value = "Update label data")
    public ApiDefaultResponse updateProductLabel(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiProductLabel request) throws ApiException {
    	return productEngine.updateProductLabel(authUser, request);
    }
    
    @DeleteMapping(value = "/label/{id}")
    @ApiOperation(value = "Deletes a product label")
    public ApiDefaultResponse deleteProductLabel(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Label id", required = true)  @PathVariable("id") Long id) throws ApiException {
    	productEngine.deleteProductLabel(authUser, id);
    	return new ApiDefaultResponse();
    }

    @PutMapping(value = "/label/values")
    @ApiOperation(value = "Update field values")
    public ApiDefaultResponse updateProductLabelValues(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiProductLabelUpdateValues request) throws ApiException {
    	return productEngine.updateProductLabelValues(authUser, request);
    }    
    
    @PutMapping(value = "/label/content")
    @ApiOperation(value = "Update label content")
    public ApiDefaultResponse updateProductLabelContent(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiProductLabelContent request) throws ApiException {
    	return productEngine.updateProductLabelContent(authUser, request);
    }
    
    @GetMapping(value = "/label/values/{id}")
    @ApiOperation(value = "Get label with field values")
    public ApiResponse<ApiProductLabelValues> getProductLabelValues(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Record id", required = true)  @PathVariable("id") Long id) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabelValues(authUser, id));
    }
    
    @GetMapping(value = "/label/content/{id}")
    @ApiOperation(value = "Get label content")
    public ApiResponse<ApiProductLabelContent> getProductLabelContent(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Label id", required = true)  @PathVariable("id") Long id) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabelContent(authUser, id));
    }    
    
    @GetMapping(value = "/labels/{id}")
    @ApiOperation(value = "Get labels for product")
    public ApiResponse<List<ApiProductLabelBase>> getProductLabels(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true)  @PathVariable("id") Long id) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabels(authUser, id));
    }

    @PostMapping(value = "/label/execute/{action}")
    @ApiOperation(value = "Execute action")
    public ApiDefaultResponse executeAction(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiBaseEntity request, 
    		@Valid @PathVariable(value = "action", required = true) ProductLabelAction action) throws ApiException {
    	productEngine.executeAction(authUser, request, action);
    	return new ApiDefaultResponse();
    }
    
    @GetMapping(value = "/label/analytics/{uid}")
    @ApiOperation(value = "Get label data")
    public ApiResponse<ApiProductLabelAnalytics> getProductLabelAnalytics(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Label uid", required = true) @PathVariable("uid") String uid) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabelAnalytics(authUser, uid));
    }
    
    @PostMapping(value = "/label_batch/create")
    @ApiOperation(value = "Create a new product label batch")
    public ApiResponse<ApiBaseEntity> createProductLabelBatch(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiProductLabelBatch request) throws ApiException {
		return new ApiResponse<>(productEngine.createProductLabelBatch(authUser, request));
    }
    
    @GetMapping(value = "/label_batch/{id}")
    @ApiOperation(value = "Get label batch data")
    public ApiResponse<ApiProductLabelBatch> getProductLabelBatch(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Batch id", required = true) @PathVariable("id") Long id) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabelBatch(authUser, id));
    }

    @PutMapping(value = "/label_batch")
    @ApiOperation(value = "Update label batch data")
    public ApiDefaultResponse updateProductLabelBatch(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiProductLabelBatch request) throws ApiException {
    	return productEngine.updateProductLabelBatch(authUser, request);
    }
    
    @DeleteMapping(value = "/label_batch/{id}")
    @ApiOperation(value = "Deletes a product label batch")
    public ApiDefaultResponse deleteProductLabelBatch(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Batch id", required = true)  @PathVariable("id") Long id) throws ApiException {
    	productEngine.deleteProductLabelBatch(authUser, id);
    	return new ApiDefaultResponse();
    }
    
    @GetMapping(value = "/label/{id}/batches")
    @ApiOperation(value = "Get label batches for specified label. Sorting: number or productionDate, expiryDate")
    public ApiPaginatedResponse<ApiProductLabelBatch> getProductLabelBatches(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Label id", required = true) @PathVariable("id") Long id,
    		@Valid ApiListProductLabelBatchesRequest request) throws ApiException {
    	return new ApiPaginatedResponse<>(productEngine.listProductLabelBatches(authUser, id, request));
    }

    @GetMapping(value = "/label/{id}/instructions")
    public ResponseEntity<byte[]> getProductLabelInstructions(@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Label id", required = true) @PathVariable("id") Long id) throws ApiException {
        return productDocumentEngine.createLabelsAndHowToUseInstructions(authUser, id).toResponseEntity();
    }
    
    @GetMapping(value = "/userCustomers/list/{productId}")
    @ApiOperation(value = "List user customers for a product")
    public ApiPaginatedResponse<ApiUserCustomer> getUserCustomersList(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true) @PathVariable("productId") Long productId,
    		@Valid ApiListCollectorsRequest request) throws ApiException {
    	return new ApiPaginatedResponse<>(productEngine.listUserCustomers(authUser, productId, request));
    }
    
    @PutMapping(value = "/userCustomers")
    @ApiOperation(value = "Update user customer (collector, farmer)")
    public ApiDefaultResponse updateUserCustomer(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiUserCustomer request) throws ApiException {
    	productEngine.updateUserCustomer(authUser, request);
    	return new ApiDefaultResponse();
    }
    
    @PostMapping(value = "/userCustomers/add/{productId}/{companyId}")
    @ApiOperation(value = "Add a user customer")
    public ApiResponse<ApiBaseEntity> addUserCustomer(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true) @PathVariable("productId") Long productId,
    		@Valid @ApiParam(value = "Company id", required = true) @PathVariable("companyId") Long companyId,
    		@Valid @RequestBody ApiUserCustomer request) throws ApiException {
    	return new ApiResponse<>(productEngine.addUserCustomer(authUser, productId, companyId, request));
    }
    
    @DeleteMapping(value = "/userCustomers/{id}")
    @ApiOperation(value = "Deletes a user customer")
    public ApiDefaultResponse deleteUserCustomer(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Collector id", required = true) @PathVariable("id") Long id) throws ApiException {
    	productEngine.deleteUserCustomer(authUser, id);
    	return new ApiDefaultResponse();
    }

    @GetMapping(value = "/companyCustomers/list/{productId}")
    @ApiOperation(value = "List company customers for a product")
    public ApiPaginatedResponse<ApiCompanyCustomer> getCompanyCustomersList(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true) @PathVariable("productId") Long productId,
    		@Valid ApiListCustomersRequest request) throws ApiException {
    	return new ApiPaginatedResponse<>(productEngine.listCompanyCustomers(authUser, productId, request));
    }
    
    @PutMapping(value = "/companyCustomers")
    @ApiOperation(value = "Update company customer")
    public ApiDefaultResponse updateCompanyCustomer(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiCompanyCustomer request) throws ApiException {
    	productEngine.updateCompanyCustomer(authUser, request);
    	return new ApiDefaultResponse();
    }
    
    @PostMapping(value = "/companyCustomers/add/{productId}/{companyId}")
    @ApiOperation(value = "Add company customer customer")
    public ApiResponse<ApiBaseEntity> addCompanyCustomer(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true) @PathVariable("productId") Long productId,
    		@Valid @ApiParam(value = "Company id", required = true) @PathVariable("companyId") Long companyId,
    		@Valid @RequestBody ApiCompanyCustomer request) throws ApiException {
    	return new ApiResponse<>(productEngine.addCompanyCustomer(authUser, productId, companyId, request));
    }
    
    @DeleteMapping(value = "/companyCustomers/{id}")
    @ApiOperation(value = "Deletes a company customer")
    public ApiDefaultResponse deleteCompanyCustomer(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Customer id", required = true) @PathVariable("id") Long id) throws ApiException {
    	productEngine.deleteCompanyCustomer(authUser, id);
    	return new ApiDefaultResponse();
    }    
    
    @GetMapping(value = "/knowledgeBlog/list/{productId}")
    @ApiOperation(value = "Get knowledge blogs for a product")
    public ApiPaginatedResponse<ApiKnowledgeBlogBase> getProductKnowledgeBlog(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Product id", required = true) @PathVariable("productId") Long productId,
    		@Valid ApiListKnowledgeBlogRequest request) throws ApiException {
    	return new ApiPaginatedResponse<>(productEngine.listKnowledgeBlogs(authUser, productId, request));
    }
    
    @GetMapping(value = "/knowledgeBlog/{id}")
    @ApiOperation(value = "Get knowledge blog by id")
    public ApiResponse<ApiKnowledgeBlog> getProductKnowledgeBlog(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "id", required = true) @PathVariable("id") Long id) throws ApiException {
    	return new ApiResponse<>(productEngine.getKnowledgeBlog(authUser, id));
    }

    @PutMapping(value = "/knowledgeBlog")
    @ApiOperation(value = "Update knowledge blog")
    public ApiDefaultResponse getProductKnowledgeBlog(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiKnowledgeBlog request) throws ApiException {
    	productEngine.updateKnowledgeBlog(authUser, request);
    	return new ApiDefaultResponse();
    }
    
    @PostMapping(value = "/knowledgeBlog/{productId}")
    @ApiOperation(value = "Add knowledge blog to a product")
    public ApiDefaultResponse getProductKnowledgeBlog(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "type", required = true) @PathVariable("productId") Long productId,
    		@Valid @RequestBody ApiKnowledgeBlog request) throws ApiException {
    	productEngine.addKnowledgeBlog(authUser, productId, request);
    	return new ApiDefaultResponse();
    }
    
    
    @DeleteMapping(value = "/label/feedback/{id}")
    @ApiOperation(value = "Deletes a product label feedback")
    public ApiDefaultResponse deleteProductLabelFeedback(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @ApiParam(value = "Feedback id", required = true) @PathVariable("id") Long id) throws ApiException {
    	productEngine.deleteProductLabelFeedback(authUser, id);
    	return new ApiDefaultResponse();
    }
    
}
