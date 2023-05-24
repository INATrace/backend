package com.abelium.inatrace.components.user;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.ApiUserRole;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.user.api.*;
import com.abelium.inatrace.components.user.types.UserAction;
import com.abelium.inatrace.security.service.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userEngine;
	
    @PostMapping(value = "/login")
    @ApiOperation(value = "Logs in a user")
    public ResponseEntity<ApiDefaultResponse> login(@Valid @RequestBody ApiLoginRequest request) throws ApiException {
        return userEngine.login(request);
    }

    @PostMapping(value = "/refresh_authentication")
    @ApiOperation(value = "Refresh access token")
    public ResponseEntity<ApiDefaultResponse> refreshAuthentication(HttpServletRequest request) throws ApiException {
        return userEngine.refreshAuthentication(request);
    }
    
    @PostMapping(value = "/logout")
    @ApiOperation(value = "Logs out a user")
    public ResponseEntity<ApiDefaultResponse> logout(HttpServletRequest request, HttpServletResponse response) throws ApiException {
        return userEngine.logout(request);
    }
    
    @PostMapping(value = "/register")
    @ApiOperation(value = "Create a new user (not activated)")
    public ApiDefaultResponse createUser(@Valid @RequestBody ApiCreateUserRequest request) throws ApiException {
		userEngine.createUser(request);
		return new ApiDefaultResponse();
    }
    
    @GetMapping(value = "/admin/list")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @ApiOperation(value = "Lists all users. Must be an administrator. Sorting: email, surname or default")
    public ApiPaginatedResponse<ApiUserBase> adminListUsers(@Valid ApiListUsersRequest request) {
    	return new ApiPaginatedResponse<>(userEngine.adminListUsers(request));
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "Lists all users in the logged-in user's companies")
    public ApiPaginatedResponse<ApiUserBase> listUsers(@AuthenticationPrincipal CustomUserDetails authUser, @Valid ApiListUsersRequest request) {
    	return new ApiPaginatedResponse<>(userEngine.listUsers(authUser, request));
    }
    
    @PostMapping(value = "/confirm_email")
    @ApiOperation(value = "Confirms email with a token and logs-in the user")
    public ResponseEntity<ApiDefaultResponse> confirmEmail(@Valid @RequestBody ApiToken request) throws ApiException {
    	return userEngine.confirmEmail(request.token);
    }
    
    @PostMapping(value = "/request_reset_password")
    @ApiOperation(value = "Request reset password (sends mail with reset link)")
    public ApiDefaultResponse requestResetPassword(@Valid @RequestBody ApiEmail request) throws ApiException {
    	userEngine.requestResetPassword(request.email);
    	return new ApiDefaultResponse(); 
    }
    
    @PostMapping(value = "/reset_password")
    @ApiOperation(value = "Reset password using token and new password")
    public ResponseEntity<ApiDefaultResponse> resetPassword(@Valid @RequestBody ApiResetPasswordRequest request) throws ApiException {
    	return userEngine.resetPassword(request);
    }

    @PostMapping(value = "/admin/execute/{action}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @ApiOperation(value = "Execute user (status, role) action. Must be an administrator")
    public ApiDefaultResponse activateUser(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @RequestBody ApiUserRole request, 
    		@Valid @PathVariable(value = "action", required = true) UserAction action) throws ApiException {
    	userEngine.changeUserStatus(authUser, request, action);
    	return new ApiDefaultResponse();
    }
    
    @GetMapping(value = "/admin/profile/{id}")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @ApiOperation(value = "Get profile of a user. Must be an administrator")
    public ApiResponse<ApiUser> getProfileForAdmin(
    		@AuthenticationPrincipal CustomUserDetails authUser,
    		@Valid @PathVariable(value = "id", required = true) Long userId) throws ApiException {
    	return new ApiResponse<>(userEngine.getProfileForAdmin(authUser, userId));
    }
    
    @GetMapping(value = "/profile")
    @ApiOperation(value = "Get profile of the currently loged-in user")
    public ApiResponse<ApiUserGet> getProfileForUser(@AuthenticationPrincipal CustomUserDetails authUser) throws ApiException {
    	return new ApiResponse<>(userEngine.getProfileForUser(authUser.getUserId()));
    }
    
    @PutMapping(value = "/profile")
    @ApiOperation(value = "Update profile of the currently logged in user")
    public ApiDefaultResponse updateProfile(@AuthenticationPrincipal CustomUserDetails authUser, @Valid @RequestBody ApiUserUpdate request) throws ApiException {
    	userEngine.updateProfile(authUser.getUserId(), request);
    	return new ApiDefaultResponse();
    }
    
    @PutMapping(value = "/admin/profile")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @ApiOperation(value = "Update profile of a user. Must be admin")
    public ApiDefaultResponse adminUpdateProfile(@Valid @RequestBody ApiAdminUserUpdate request) throws ApiException {
    	userEngine.updateProfile(request.id, request);
    	return new ApiDefaultResponse();
    }     
       
}
