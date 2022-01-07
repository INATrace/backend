package com.abelium.inatrace.components.user;

import java.util.List;

import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.user.api.ApiUser;
import com.abelium.inatrace.components.user.api.ApiUserBase;
import com.abelium.inatrace.components.user.api.ApiUserGet;
import com.abelium.inatrace.components.user.types.UserAction;
import com.abelium.inatrace.db.entities.common.User;

public class UserApiTools {

	public static void updateApiUserBase(ApiUserBase apiUser, User user) {
		CommonApiTools.updateApiBaseEntity(apiUser, user);
		apiUser.email = user.getEmail();
		apiUser.name = user.getName();
		apiUser.surname = user.getSurname();
		apiUser.status = user.getStatus();
		apiUser.role = user.getRole();
		apiUser.language = user.getLanguage();
	}
	
	public static ApiUserBase toApiUserBase(User user) {
		if (user == null) return null;
		
		ApiUserBase apiUser = new ApiUserBase();
		updateApiUserBase(apiUser, user);
		return apiUser;
	}
	
	public static ApiUser toApiUser(User user) {
		if (user == null) return null;
		
		ApiUser apiUser = new ApiUser();
		updateApiUserBase(apiUser, user);
		// Add more
		return apiUser;
	}
	
	public static ApiUserGet toApiUserGet(User user, 
			List<UserAction> actions,
			List<Long> companyIds,
			List<Long> companyIdsAdmin) {
		if (user == null) return null;
		
		ApiUserGet apiUser = new ApiUserGet();
		updateApiUserBase(apiUser, user);
		// Add more
		apiUser.actions = actions;
		apiUser.companyIds = companyIds;
		apiUser.companyIdsAdmin = companyIdsAdmin;
		return apiUser;
	}
	
}
