package com.abelium.inatrace.security.utils;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.CompanyUserRole;
import com.abelium.inatrace.types.UserRole;

import java.util.List;

public final class PermissionsUtil {

	private PermissionsUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void checkUserIfCompanyEnrolled(List<CompanyUser> companyUsers, CustomUserDetails userToCheck) throws ApiException {
		if (companyUsers.stream().noneMatch(cu -> cu.getUser().getId().equals(userToCheck.getUserId()))) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "Unknown user company!");
		}
	}

	public static void checkUserIfCompanyOrSystemAdmin(List<CompanyUser> companyUsers, CustomUserDetails userToCheck) throws ApiException {
		CompanyUser companyUser = companyUsers.stream()
				.filter(cu -> cu.getUser().getId().equals(userToCheck.getUserId())).findAny()
				.orElseThrow(() -> new ApiException(ApiStatus.AUTH_ERROR, "Unknown user company!"));

		if (!UserRole.ADMIN.equals(userToCheck.getUserRole()) && !CompanyUserRole.ADMIN.equals(companyUser.getRole())) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "User doesn't have required permission!");
		}
	}

}
