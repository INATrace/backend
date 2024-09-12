package com.abelium.inatrace.components.user;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.UserStatus;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.torpedoquery.jakarta.jpa.Torpedo;
import java.util.List;

@Lazy
@Component
public class UserQueries extends BaseService {
	
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
