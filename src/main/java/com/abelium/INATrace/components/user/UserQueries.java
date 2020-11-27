package com.abelium.INATrace.components.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.torpedoquery.jpa.Torpedo;

import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.BaseEngine;
import com.abelium.INATrace.db.entities.CompanyUser;
import com.abelium.INATrace.db.entities.User;
import com.abelium.INATrace.tools.Queries;
import com.abelium.INATrace.types.UserStatus;

@Lazy
@Component
public class UserQueries extends BaseEngine {
	
	@Transactional
	public User fetchUser(Long userId) throws ApiException {
    	User user = Queries.get(em, User.class, userId);
    	if (user == null) {
    		throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user id");
    	}
    	return user;
	}

	@Transactional
	public void activateUsersForCompany(Long companyId) {
		CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
		Torpedo.where(cuProxy.getCompany().getId()).eq(companyId);
		List<User> users = Torpedo.select(cuProxy.getUser()).list(em);

		// Don't do a bulk update due to timestamps and auditing
		for (User user : users) {
			user.setStatus(UserStatus.ACTIVE);
		}
	}	
	
}
