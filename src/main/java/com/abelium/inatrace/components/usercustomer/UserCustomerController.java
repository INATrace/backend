package com.abelium.inatrace.components.usercustomer;


import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.usercustomer.api.ApiUserCustomer;
import com.abelium.inatrace.types.UserCustomerType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/chain/user-customer")
public class UserCustomerController {

    private final UserCustomerService userCustomerService;

    @Autowired
    public UserCustomerController(UserCustomerService userCustomerService) {
        this.userCustomerService = userCustomerService;
    }

    @GetMapping("{id}")
    @ApiOperation("Get a single user customer with the provided ID.")
    public ApiResponse<ApiUserCustomer> getUserCustomer(
            @Valid @ApiParam(value = "UserCustomer ID", required = true) @PathVariable("id") Long id) throws ApiException {

        return new ApiResponse<>(userCustomerService.getUserCustomer(id));
    }

    @GetMapping("/list")
    @ApiOperation("Get a paginated list of user customers.")
    public ApiPaginatedResponse<ApiUserCustomer> getUserCustomerList(@Valid ApiPaginatedRequest request) {
        return new ApiPaginatedResponse<>(userCustomerService.getUserCustomerList(request, new UserCustomerQueryRequest()));
    }

    @GetMapping("/list/company/{companyId}")
    @ApiOperation("Get a paginated list of user customers for provided company.")
    public ApiPaginatedResponse<ApiUserCustomer> getUserCustomerListForCompany(
            @Valid ApiPaginatedRequest request,
            @Valid @ApiParam(value = "Company ID", required = true) @PathVariable(value = "companyId") Long companyId,
            @Valid @ApiParam(value = "User customer type") @RequestParam(value = "userCustomerType", required = false) UserCustomerType userCustomerType) {

        return new ApiPaginatedResponse<>(userCustomerService.getUserCustomerList(
                request,
                new UserCustomerQueryRequest(
                        companyId,
                        userCustomerType
                )
        ));
    }



}
