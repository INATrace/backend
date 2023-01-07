package com.abelium.inatrace.security.utils;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.db.entities.product.Product;
import com.abelium.inatrace.db.entities.product.ProductCompany;
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
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Unknown user company!");
		}
	}

	public static void checkUserIfCompanyEnrolledOrSystemAdmin(List<CompanyUser> companyUsers, CustomUserDetails userToCheck) throws ApiException {
		if (!UserRole.ADMIN.equals(userToCheck.getUserRole()) && companyUsers.stream().noneMatch(cu -> cu.getUser().getId().equals(userToCheck.getUserId()))) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Unknown user company or not system admin!");
		}
	}

	public static void checkUserIfCompanyAdminOrSystemAdmin(List<CompanyUser> companyUsers, CustomUserDetails userToCheck) throws ApiException {

		if (UserRole.ADMIN.equals(userToCheck.getUserRole())) {
			return;
		}

		CompanyUser companyUser = findCompanyUser(userToCheck.getUserId(), companyUsers);
		if (!CompanyUserRole.ADMIN.equals(companyUser.getRole())) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "User doesn't have required permission!");
		}
	}

	public static void checkUserIfCompanyEnrolledAndAdminOrSystemAdmin(List<CompanyUser> companyUsers, CustomUserDetails userToCheck) throws ApiException {

		CompanyUser companyUser = findCompanyUser(userToCheck.getUserId(), companyUsers);

		if (!UserRole.ADMIN.equals(userToCheck.getUserRole()) && !CompanyUserRole.ADMIN.equals(companyUser.getRole())) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "User doesn't have required permission!");
		}
	}

	public static void checkUserIfConnectedWithProducts(List<Product> products, CustomUserDetails userToCheck) throws ApiException {

		boolean userIsAssociatedWithOneOfTheProducts = false;
		for (Product companyProduct : products) {

			userIsAssociatedWithOneOfTheProducts = companyProduct.getAssociatedCompanies()
					.stream()
					.map(ProductCompany::getCompany)
					.distinct()
					.map(Company::getUsers)
					.anyMatch(companyUsers -> companyUsers
							.stream()
							.map(CompanyUser::getUser)
							.anyMatch(u -> u.getId().equals(userToCheck.getUserId())));

			if (userIsAssociatedWithOneOfTheProducts) {
				break;
			}
		}

		if (!userIsAssociatedWithOneOfTheProducts) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "User doesn't have required permission!");
		}
	}

	private static CompanyUser findCompanyUser(Long userId, List<CompanyUser> companyUsers) throws ApiException {
		return companyUsers.stream()
				.filter(cu -> cu.getUser().getId().equals(userId)).findAny()
				.orElseThrow(() -> new ApiException(ApiStatus.UNAUTHORIZED, "Unknown user company!"));
	}

}
