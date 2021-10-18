package com.abelium.inatrace.components.company;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.company.api.*;
import com.abelium.inatrace.components.company.types.CompanyAction;
import com.abelium.inatrace.components.product.api.ApiListCustomersRequest;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.UserCustomerType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

	private final CompanyService companyService;

	@Autowired
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@PostMapping(value = "/create")
    @ApiOperation(value = "Create a new company (with the logged-in user as company admin)")
    public ApiResponse<ApiBaseEntity> createCompany(@AuthenticationPrincipal CustomUserDetails authUser, @Valid @RequestBody ApiCompany request) throws ApiException {
		return new ApiResponse<>(companyService.createCompany(authUser.getUserId(), request));
    }
    
    @GetMapping(value = "/list")
    @ApiOperation(value = "Lists all companies for the logged-in user. Sorting: name or default")
    public ApiPaginatedResponse<ApiCompanyListResponse> listCompanies(@AuthenticationPrincipal CustomUserDetails authUser, 
    		@Valid ApiListCompaniesRequest request) {
    	return new ApiPaginatedResponse<>(companyService.listUserCompanies(authUser.getUserId(), request));
    }
    
    @GetMapping(value = "/admin/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Lists all companies. Must be admin. Sorting: name or default")
    public ApiPaginatedResponse<ApiCompanyListResponse> listCompaniesAdmin(@Valid ApiListCompaniesRequest request) {
    	return new ApiPaginatedResponse<>(companyService.listCompanies(request));
    }    
    
    @GetMapping(value = "/profile/{id}")
    @ApiOperation(value = "Get all info about a company")
    public ApiResponse<ApiCompanyGet> getCompany(@AuthenticationPrincipal CustomUserDetails authUser, 
    		@Valid @ApiParam(value = "Record id", required = true) @PathVariable("id") Long id,
    		@Valid @ApiParam(value = "language", required = false) @RequestParam(value = "language", defaultValue = "EN") String language) throws ApiException {
    	return new ApiResponse<>(companyService.getCompany(authUser, id, Language.valueOf(language)));
    }

	@GetMapping("/profile/{id}/users")
	@ApiOperation("Get all user for the company with the provided ID")
	public ApiResponse<List<ApiCompanyUser>> getCompanyUsers(
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(companyService.getCompanyUsers(id));
	}

    @PutMapping(value = "/profile")
    @ApiOperation(value = "Update company data")
    public ApiDefaultResponse updateCompany(@AuthenticationPrincipal CustomUserDetails authUser, @Valid @RequestBody ApiCompanyUpdate company) throws ApiException {
    	companyService.updateCompany(authUser, company);
    	return new ApiDefaultResponse();
    }
    
    @PostMapping(value = "/execute/{action}")
    @ApiOperation(value = "Execute company action. Must be an administrator")
    public ApiDefaultResponse executeAction(@AuthenticationPrincipal CustomUserDetails authUser,
            @Valid @RequestBody ApiCompanyActionRequest request,
    		@Valid @PathVariable(value = "action", required = true) CompanyAction action) throws ApiException {
        if (companyService.isSystemAdmin(authUser) || companyService.isCompanyAdmin(authUser, request.getCompanyId())) {
            companyService.executeAction(request, action);
            return new ApiDefaultResponse();
        }
    	throw new ApiException(ApiStatus.UNAUTHORIZED, "User must be system or company admin");
    }

    @GetMapping(value = "/userCustomers/{id}")
    @ApiOperation(value = "Get user customer by id")
    public ApiResponse<ApiUserCustomer> getUserCustomer(
            @Valid @ApiParam(value = "User customer ID", required = true) @PathVariable("id") Long id
    ) throws ApiException {
        return new ApiResponse<>(companyService.getUserCustomer(id));
    }

    @GetMapping(value = "/userCustomers/{companyId}/{type}")
    @ApiOperation(value = "Get list of user customers for given company ID and type")
    public ApiPaginatedResponse<ApiUserCustomer> getUserCustomersForCompanyAndType(
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @Valid @ApiParam(value = "Type of user customer (collector, farmer)") @PathVariable("type") UserCustomerType type,
            @Valid ApiListFarmersRequest request
    ) throws ApiException {
        return new ApiPaginatedResponse<>(companyService.getUserCustomersForCompanyAndType(companyId, type, request));
    }

    @PostMapping(value = "/userCustomers/add/{companyId}")
    @ApiOperation(value = "Add new user customer for given company ID")
    public ApiResponse<ApiUserCustomer> addUserCustomer(
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("companyId") Long companyId,
            @Valid @RequestBody ApiUserCustomer request
    ) throws ApiException {
        return new ApiResponse<>(companyService.addUserCustomer(companyId, request));
    }

    @PutMapping(value = "/userCustomers/edit")
    @ApiOperation(value = "Update user customer with given ID")
    public ApiResponse<ApiUserCustomer> updateUserCustomer(
            @Valid @RequestBody ApiUserCustomer request
    ) throws ApiException {
        return new ApiResponse<>(companyService.updateUserCustomer(request));
    }

    @DeleteMapping(value = "/userCustomers/{id}")
    @ApiOperation(value = "Delete user customer with given id")
    public ApiDefaultResponse deleteUserCustomer(
            @Valid @ApiParam(value = "User customer ID", required = true) @PathVariable("id") Long id
    ) throws ApiException {
        companyService.deleteUserCustomer(id);
        return new ApiDefaultResponse();
    }

    @GetMapping(value = "/companyCustomers/list/{companyId}")
    @ApiOperation(value = "List company customers for company")
    public ApiPaginatedResponse<ApiCompanyCustomer> getCompanyCustomersList(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @Valid @ApiParam(value = "Company id", required = true) @PathVariable("companyId") Long companyId,
            @Valid ApiListCustomersRequest request) throws ApiException {
        return new ApiPaginatedResponse<>(companyService.listCompanyCustomers(authUser, companyId, request));
    }

    @GetMapping(value = "/companyCustomers/{id}")
    @ApiOperation(value = "Get company customer by ID")
    public ApiResponse<ApiCompanyCustomer> getCompanyCustomer(
            @Valid @ApiParam(value = "Company customer ID", required = true) @PathVariable("id") Long companyCustomerId
    ) throws ApiException {
        return new ApiResponse<>(companyService.getCompanyCustomer(companyCustomerId));
    }

    @PostMapping(value = "/companyCustomers")
    @ApiOperation(value = "Create company customer")
    public ApiResponse<ApiCompanyCustomer> createCompanyCustomer(
            @Valid @RequestBody ApiCompanyCustomer apiCompanyCustomer
    ) throws ApiException {
        return new ApiResponse<>(companyService.createCompanyCustomer(apiCompanyCustomer));
    }

    @PutMapping(value = "/companyCustomers")
    @ApiOperation(value = "Update company customer")
    public ApiResponse<ApiCompanyCustomer> updateCompanyCustomer(
            @Valid @RequestBody ApiCompanyCustomer apiCompanyCustomer
    ) throws ApiException {
        return new ApiResponse<>(companyService.updateCompanyCustomer(apiCompanyCustomer));
    }

    @DeleteMapping(value = "/companyCustomers/{id}")
    @ApiOperation(value = "Delete company customer with ID")
    public ApiDefaultResponse deleteCompanyCustomer(
            @Valid @ApiParam(value = "Company customer ID", required = true) @PathVariable("id") Long id
    ) throws ApiException {
        companyService.deleteCompanyCustomer(id);
        return new ApiDefaultResponse();
    }

    @GetMapping(value = "/associations/{id}")
    @ApiOperation(value = "Get list of associations for the selected company with given ID")
    public ApiPaginatedResponse<ApiCompanyListResponse> getAssociations(
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long id,
            @Valid ApiPaginatedRequest request
    ) {
        return new ApiPaginatedResponse<>(companyService.getAssociations(id, request));
    }

}
