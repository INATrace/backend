package com.abelium.inatrace.components.company;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.company.api.ApiCompanyActionRequest;
import com.abelium.inatrace.components.company.api.ApiCompanyGet;
import com.abelium.inatrace.components.company.api.ApiCompanyListResponse;
import com.abelium.inatrace.components.company.api.ApiCompanyUpdate;
import com.abelium.inatrace.components.company.api.ApiListCompaniesRequest;
import com.abelium.inatrace.components.company.types.CompanyAction;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyEngine companyEngine;
	
    @PostMapping(value = "/create")
    @ApiOperation(value = "Create a new company (with the logged-in user as company admin)")
    public ApiResponse<ApiBaseEntity> createCompany(@AuthenticationPrincipal CustomUserDetails authUser, @Valid @RequestBody ApiCompany request) throws ApiException {
		return new ApiResponse<>(companyEngine.createCompany(authUser.getUserId(), request));
    }
    
    @GetMapping(value = "/list")
    @ApiOperation(value = "Lists all companies for the logged-in user. Sorting: name or default")
    public ApiPaginatedResponse<ApiCompanyListResponse> listCompanies(@AuthenticationPrincipal CustomUserDetails authUser, 
    		@Valid ApiListCompaniesRequest request) {
    	return new ApiPaginatedResponse<>(companyEngine.listUserCompanies(authUser.getUserId(), request));
    }
    
    @GetMapping(value = "/admin/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Lists all companies. Must be admin. Sorting: name or default")
    public ApiPaginatedResponse<ApiCompanyListResponse> listCompaniesAdmin(@Valid ApiListCompaniesRequest request) {
    	return new ApiPaginatedResponse<>(companyEngine.listCompanies(request));
    }    
    
    @GetMapping(value = "/profile/{id}")
    @ApiOperation(value = "Get all info about a company")
    public ApiResponse<ApiCompanyGet> getCompany(@AuthenticationPrincipal CustomUserDetails authUser, 
    		@Valid @ApiParam(value = "Record id", required = true) @PathVariable("id") Long id,
    		@Valid @ApiParam(value = "language", required = false) @RequestParam(value = "language", defaultValue = "EN") String language) throws ApiException {
    	return new ApiResponse<>(companyEngine.getCompany(authUser, id, Language.valueOf(language)));
    }

    @PutMapping(value = "/profile")
    @ApiOperation(value = "Update company data")
    public ApiDefaultResponse updateCompany(@AuthenticationPrincipal CustomUserDetails authUser, @Valid @RequestBody ApiCompanyUpdate company) throws ApiException {
    	companyEngine.updateCompany(authUser, company);
    	return new ApiDefaultResponse();
    }
    
    @PostMapping(value = "/execute/{action}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Execute company action. Must be an administrator")
    public ApiDefaultResponse executeAction(@Valid @RequestBody ApiCompanyActionRequest request, 
    		@Valid @PathVariable(value = "action", required = true) CompanyAction action) throws ApiException {
    	companyEngine.executeAction(request, action);
    	return new ApiDefaultResponse();
    }
    
}
