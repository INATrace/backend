package com.abelium.INATrace.components.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.torpedoquery.jpa.Function;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import com.abelium.INATrace.api.ApiDefaultResponse;
import com.abelium.INATrace.api.ApiPaginatedList;
import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.ApiUserRole;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.BaseEngine;
import com.abelium.INATrace.components.common.TokenEngine;
import com.abelium.INATrace.components.company.CompanyQueries;
import com.abelium.INATrace.components.mail.MailEngine;
import com.abelium.INATrace.components.user.api.ApiCreateUserRequest;
import com.abelium.INATrace.components.user.api.ApiListUsersRequest;
import com.abelium.INATrace.components.user.api.ApiLoginRequest;
import com.abelium.INATrace.components.user.api.ApiResetPasswordRequest;
import com.abelium.INATrace.components.user.api.ApiUser;
import com.abelium.INATrace.components.user.api.ApiUserBase;
import com.abelium.INATrace.components.user.api.ApiUserGet;
import com.abelium.INATrace.components.user.api.ApiUserUpdate;
import com.abelium.INATrace.components.user.types.UserAction;
import com.abelium.INATrace.db.entities.AuthenticationToken;
import com.abelium.INATrace.db.entities.CompanyUser;
import com.abelium.INATrace.db.entities.ConfirmationToken;
import com.abelium.INATrace.db.entities.User;
import com.abelium.INATrace.security.service.CustomUserDetails;
import com.abelium.INATrace.tools.PaginationTools;
import com.abelium.INATrace.tools.PasswordTools;
import com.abelium.INATrace.tools.Queries;
import com.abelium.INATrace.tools.QueryTools;
import com.abelium.INATrace.types.CompanyStatus;
import com.abelium.INATrace.types.ConfirmationTokenType;
import com.abelium.INATrace.types.Status;
import com.abelium.INATrace.types.UserRole;
import com.abelium.INATrace.types.UserStatus;

@Lazy
@Component
public class UserEngine extends BaseEngine 
{	
    @Value("${INATrace.loginManager.mail}")
    private String loginManagerMail;
    
    @Value("${INATrace.emailConfirmation.url}")
    private String emailConfirmationUrl;
    
    @Value("${INATrace.passwordReset.url}")
    private String passwordResetUrl;

    
    @Autowired
    private MailEngine mailEngine;
    
    @Autowired
    private NotificationEngine notificationEngine;
    
    @Autowired
    private UserQueries userQueries;

    @Autowired
    private CompanyQueries companyQueries;
    
    @Autowired
    private TokenEngine tokenEngine;

    
	@Transactional
	public User fetchUserByEmail(String email) {
		return Queries.getUniqueBy(em, User.class, User::getEmail, email);
	}

	@Transactional
	public ResponseEntity<ApiDefaultResponse> login(ApiLoginRequest loginRequest) throws ApiException {
		User user = Queries.getUniqueBy(em, User.class, User::getEmail, loginRequest.getUsername());
		
		if (user == null) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "Invalid credentials");
		}
		if (user.getStatus() == UserStatus.DEACTIVATED || user.getStatus() == UserStatus.UNCONFIRMED) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Not confirmed or disabled");
		}
		if (!new BCryptPasswordEncoder().matches(loginRequest.password, user.getPassword())) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "Invalid credentials");
		}
		return loginUser(user);
	}

	// Call from @Transactional method with fetched user
	private ResponseEntity<ApiDefaultResponse> loginUser(User user) throws ApiException {
		AuthenticationToken authenticationToken = Queries.getUniqueBy(em, AuthenticationToken.class, 
				AuthenticationToken::getUser, user);
		String accessToken = tokenEngine.createAccessToken(user);
		String refreshToken = tokenEngine.createRefreshToken(user);
		
		if (authenticationToken != null) {
			authenticationToken.updateToken(refreshToken, tokenEngine.getRefreshTokenExpirationSec());
		} else {
			authenticationToken = new AuthenticationToken(user);
			authenticationToken.updateToken(refreshToken, tokenEngine.getRefreshTokenExpirationSec());
			em.persist(authenticationToken);
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.SET_COOKIE, tokenEngine.createAccessCookie(accessToken)); 
		responseHeaders.add(HttpHeaders.SET_COOKIE, tokenEngine.createRefreshCookie(refreshToken));
		return ResponseEntity.ok().headers(responseHeaders).body(new ApiDefaultResponse());
	}
	
	@Transactional
	public ResponseEntity<ApiDefaultResponse> logout(HttpServletRequest request) throws ApiException {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return ResponseEntity.ok().body(new ApiDefaultResponse());
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		
		Optional<Cookie> refreshCookie = tokenEngine.getRefreshCookie(cookies);
		if (refreshCookie.isPresent()) {
			String dbToken = AuthenticationToken.encrypt(refreshCookie.get().getValue());
			AuthenticationToken authToken = Queries.getUniqueBy(em, AuthenticationToken.class, AuthenticationToken::getToken, dbToken);
		
			if (authToken != null) {
				authToken.setStatus(Status.DISABLED);
			}
			responseHeaders.add(HttpHeaders.SET_COOKIE, tokenEngine.createRemoveRefreshCookie());
		}
		
		Optional<Cookie> accessCookie = tokenEngine.getAccessCookie(cookies);
		if (accessCookie.isPresent()) {
			responseHeaders.add(HttpHeaders.SET_COOKIE, tokenEngine.createRemoveAccessCookie());
		}

		return ResponseEntity.ok().headers(responseHeaders).body(new ApiDefaultResponse());
	}

	@Transactional
	public AuthenticationToken fetchAndValidateToken(String token) throws ApiException {
		AuthenticationToken authenticationToken = Queries.getUniqueBy(em, AuthenticationToken.class, 
			AuthenticationToken::getToken, AuthenticationToken.encrypt(token));
		if (authenticationToken == null || !authenticationToken.isValid()) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "Invalid authentication token");
		}
		return authenticationToken;
	}

	@Transactional
	public User fetchAndValidateUser(String token) throws ApiException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<AuthenticationToken> root = query.from(AuthenticationToken.class);
        Predicate pred = cb.equal(root.get("token"), AuthenticationToken.encrypt(token));
        query.select(root.get("user")).where(pred);
        return em.createQuery(query).getSingleResult();
	}
	
	@Transactional
	public void requestResetPassword(String email) throws ApiException {
		User user = Queries.getUniqueBy(em, User.class, User::getEmail, email);
		if (user == null) {
			return;
		}
		if (user.getStatus() == UserStatus.DEACTIVATED || user.getStatus() == UserStatus.UNCONFIRMED) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user status");
		}
		
        Pair<ConfirmationToken, String> ctPair = 
				ConfirmationToken.create(user, ConfirmationTokenType.PASSWORD_RESET);
        em.persist(ctPair.getLeft());
        
        String htmlMail = notificationEngine.createPasswordResetEmail(passwordResetUrl + ctPair.getRight());
		mailEngine.sendSimpleMailAsync(user.getEmail(), "",
			"INATrace password reset",
			"",
			htmlMail);        
	}

	@Transactional
	public void createUser(ApiCreateUserRequest createUserRequest) throws ApiException {
        
        if (!PasswordTools.isPasswordComplex(createUserRequest.password)) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Invalid password");
        }
        
        if (Queries.getUniqueBy(em, User.class, User::getEmail, createUserRequest.email) != null) {
        	throw new ApiException(ApiStatus.INVALID_REQUEST, "User already exists");
        }
        
        User user = new User();
        user.setEmail(createUserRequest.email);
        user.setPassword(new BCryptPasswordEncoder().encode(createUserRequest.password));
        user.setName(createUserRequest.name);
        user.setSurname(createUserRequest.surname);
        user.setLanguage(createUserRequest.language);
        em.persist(user);
        
        Pair<ConfirmationToken, String> ctPair = 
        		ConfirmationToken.create(user, ConfirmationTokenType.CONFIRM_EMAIL);
        em.persist(ctPair.getLeft());
        
        mailEngine.sendSimpleMailAsync(loginManagerMail, "", "New INATrace registration",
        		"",
                "<p>New registration in INATrace system</p><br/>" +
        	    "<p>  User's email: " + createUserRequest.email + "</p><br/>" +
        	    "<p>Please approve the registrant as soon as possible</p>"
        );
        	
        String htmlMail = notificationEngine.createEmailConfirmationEmail(createUserRequest.name, 
        		createUserRequest.surname, emailConfirmationUrl + ctPair.getRight());
		mailEngine.sendSimpleMailAsync(createUserRequest.email, "",
			"INATrace registration",
			"",
			htmlMail);
	}
	
    private User adminUserListQueryObject(ApiListUsersRequest request) {
        User uProxy = Torpedo.from(User.class);
        
        OnGoingLogicalCondition condition = Torpedo.condition(); // .Torpedo conditions = new ArrayList<>();
        if (StringUtils.isNotBlank(request.query)) {
        	OnGoingLogicalCondition queryCondition = 
    				Torpedo.condition(uProxy.getName()).like().any(request.query).
        				   or(uProxy.getSurname()).like().any(request.query).
        				   or(uProxy.getEmail()).like().any(request.query);
        	condition = condition.and(queryCondition);
        	
        } else {
        	if (StringUtils.isNotBlank(request.email)) {
        		condition = condition.and(uProxy.getEmail()).like().any(request.email);
        	}
        	if (StringUtils.isNotBlank(request.surname)) {
        		condition = condition.and(uProxy.getSurname()).like().any(request.surname);
        	}
        }
        if (request.role != null) {
            condition = condition.and(Torpedo.condition(uProxy.getRole()).eq(request.role));
        }
        if (request.status != null) {
            condition = condition.and(Torpedo.condition(uProxy.getStatus()).eq(request.status));
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "email": QueryTools.orderBy(request.sort, uProxy.getEmail()); break;
	        case "surname": QueryTools.orderBy(request.sort, uProxy.getSurname()); break;
	        default: QueryTools.orderBy(request.sort, uProxy.getId());
        }
        return uProxy;
    }	

    @Transactional
	public ApiPaginatedList<ApiUserBase> adminListUsers(ApiListUsersRequest request) {
    	return PaginationTools.createPaginatedResponse(em, request, () -> adminUserListQueryObject(request), 
    			UserApiTools::toApiUserBase); 
	}

    private Function<User> userListQueryObject(Long userId, ApiListUsersRequest request) {
    	List<Long> companyIds = companyQueries.fetchCompanyIdsForUser(userId, List.of(CompanyStatus.ACTIVE));
    	
    	CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
        
        OnGoingLogicalCondition condition = Torpedo.condition(); // .Torpedo conditions = new ArrayList<>();
        condition = condition.and(cuProxy.getCompany().getId()).in(companyIds); 
        if (StringUtils.isNotBlank(request.query)) {
        	OnGoingLogicalCondition queryCondition = 
    				Torpedo.condition(cuProxy.getUser().getName()).like().any(request.query).
        				   or(cuProxy.getUser().getSurname()).like().any(request.query).
        				   or(cuProxy.getUser().getEmail()).like().any(request.query);
        	condition = condition.and(queryCondition);
        } else {
        	if (StringUtils.isNotBlank(request.email)) {
        		condition = condition.and(cuProxy.getUser().getEmail()).like().any(request.email);
        	}
        	if (StringUtils.isNotBlank(request.surname)) {
        		condition = condition.and(cuProxy.getUser().getSurname()).like().any(request.surname);
        	}
        }
        if (request.role != null) {
            condition = condition.and(Torpedo.condition(cuProxy.getUser().getRole()).eq(request.role));
        }
        if (request.status != null) {
            condition = condition.and(Torpedo.condition(cuProxy.getUser().getStatus()).eq(request.status));
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "email": QueryTools.orderBy(request.sort, cuProxy.getUser().getEmail()); break;
	        case "surname": QueryTools.orderBy(request.sort, cuProxy.getUser().getSurname()); break;
	        default: QueryTools.orderBy(request.sort, cuProxy.getUser().getSurname());
        }
        return Torpedo.distinct(cuProxy.getUser());
    }	
    
    @Transactional
	public ApiPaginatedList<ApiUserBase> listUsers(CustomUserDetails authUser, ApiListUsersRequest request) {
    	return PaginationTools.createPaginatedResponse1(em, request, () -> userListQueryObject(authUser.getUserId(), request), 
    			UserApiTools::toApiUserBase); 
	}

    @Transactional
	public ResponseEntity<ApiDefaultResponse> confirmEmail(String token) throws ApiException {
		ConfirmationToken confirmationToken = Queries.getUniqueBy(em, ConfirmationToken.class, 
				ConfirmationToken::getToken, ConfirmationToken.encrypt(token));
		if (confirmationToken == null || !confirmationToken.isValidEmailConfirmationToken()) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "Invalid confirmation token");
		}
		confirmationToken.getUser().setStatus(UserStatus.CONFIRMED_EMAIL);
		confirmationToken.setStatus(Status.DISABLED);
		return loginUser(confirmationToken.getUser());
	}
    
    @Transactional
	public ResponseEntity<ApiDefaultResponse> resetPassword(ApiResetPasswordRequest request) throws ApiException {
		ConfirmationToken confirmationToken = Queries.getUniqueBy(em, ConfirmationToken.class, 
				ConfirmationToken::getToken, ConfirmationToken.encrypt(request.token));
		if (confirmationToken == null || !confirmationToken.isValidPasswordResetToken()) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "Invalid password reset token");
		}
        if (!PasswordTools.isPasswordComplex(request.password)) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Invalid password");
        }
        confirmationToken.setStatus(Status.DISABLED);
        confirmationToken.getUser().setPassword(new BCryptPasswordEncoder().encode(request.password));
		return loginUser(confirmationToken.getUser());
	}
    

	public void activateUser(User user) throws ApiException {
		if (user.getStatus() != UserStatus.DEACTIVATED && user.getStatus() != UserStatus.CONFIRMED_EMAIL) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user status");
		}
		user.setStatus(UserStatus.ACTIVE);
		
        String htmlMail = notificationEngine.createConfirmationEmail(user.getName(), user.getSurname());
		mailEngine.sendSimpleMailAsync(user.getEmail(), "",
			"INATrace sign-up",
			"",
			htmlMail);
	}
	
	public void confirmUserEmail(User user) throws ApiException {
		if (user.getStatus() != UserStatus.UNCONFIRMED) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user status");
		}
		user.setStatus(UserStatus.CONFIRMED_EMAIL);
	}	
	

	public void deactivateUser(User user) throws ApiException {
		if (user.getStatus() != UserStatus.ACTIVE && user.getStatus() != UserStatus.CONFIRMED_EMAIL) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user status");
		}
		if (user.getRole() == UserRole.ADMIN) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Cannot change status");
		}
		user.setStatus(UserStatus.DEACTIVATED);
	}
	
	public void setUserAdmin(User user) throws ApiException {
		if (user.getStatus() != UserStatus.ACTIVE) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user status");
		}
		if (user.getRole() == UserRole.ADMIN) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user role");
		}
		user.setRole(UserRole.ADMIN);
	}
	
	public void setUserRole(User user, UserRole role) throws ApiException {
		if (user.getStatus() != UserStatus.ACTIVE) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user status");
		}
		if (user.getRole() == UserRole.ADMIN) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user role");
		}
		if (role == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid role");
		}
		user.setRole(role);
	}

	public void unsetUserAdmin(User user) throws ApiException {
		if (user.getRole() != UserRole.ADMIN) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user role");
		}
		user.setRole(UserRole.USER);
	}
	
    @Transactional
	public ApiUserGet getProfileForUser(Long userId) throws ApiException {
    	User user = userQueries.fetchUser(userId);
    	List<UserAction> actions = new ArrayList<>();

    	actions.add(UserAction.VIEW_USER_PROFILE);
		actions.add(UserAction.UPDATE_USER_PROFILE);
		
		List<Long> companyIds = companyQueries.fetchCompanyIdsForUser(userId, 
				Arrays.asList(CompanyStatus.ACTIVE, CompanyStatus.REGISTERED));
		
		if (companyIds.size() == 0) actions.add(UserAction.CREATE_COMPANY);
		
		return UserApiTools.toApiUserGet(user, actions, companyIds);
	}
    
    @Transactional
	public ApiUser getProfileForAdmin(CustomUserDetails authUser, Long userId) throws ApiException {
    	User user = userQueries.fetchUser(userId);
    	List<UserAction> actions = new ArrayList<>();

    	actions.add(UserAction.VIEW_USER_PROFILE);
		actions.add(UserAction.UPDATE_USER_PROFILE);
		
		List<Long> companyIds = companyQueries.fetchCompanyIdsForUser(userId, 
				Arrays.asList(CompanyStatus.ACTIVE, CompanyStatus.REGISTERED));

		if (!authUser.getUserId().equals(userId)) {
			switch (user.getStatus()) {
				case ACTIVE: 
					actions.add(UserAction.DEACTIVATE_USER);
					actions.add(UserAction.SET_USER_ROLE);
					if (user.getRole() == UserRole.ADMIN) actions.add(UserAction.UNSET_USER_ADMIN);
					else actions.add(UserAction.SET_USER_ADMIN);
					break;
				case CONFIRMED_EMAIL: actions.addAll(Arrays.asList(UserAction.DEACTIVATE_USER, UserAction.ACTIVATE_USER)); break;
				case DEACTIVATED: actions.add(UserAction.ACTIVATE_USER); break;
				default:
			}
		}
		
		return UserApiTools.toApiUserGet(user, actions, companyIds);
	}

    @Transactional
	public void updateProfile(Long userId, ApiUserUpdate request) throws ApiException {
    	User user = userQueries.fetchUser(userId);
		user.setName(request.name);
		user.setSurname(request.surname);
		user.setLanguage(request.language);
	}

    @Transactional
	public void changeUserStatus(CustomUserDetails authUser, ApiUserRole request, UserAction action) throws ApiException {
    	if (authUser.getUserId().equals(request.id)) {
    		throw new ApiException(ApiStatus.INVALID_REQUEST, "Cannot change status/role for this user");
    	}
    	
		User user = userQueries.fetchUser(request.id);
		
		switch (action) {
			case ACTIVATE_USER: activateUser(user); break;
			case DEACTIVATE_USER: deactivateUser(user); break;
			case SET_USER_ADMIN: setUserAdmin(user); break;
			case UNSET_USER_ADMIN: unsetUserAdmin(user); break;
			case CONFIRM_USER_EMAIL: confirmUserEmail(user); break;
			case SET_USER_ROLE: setUserRole(user, request.role); break;
			default: throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid user status");
		}
	}

    @Transactional
	public ResponseEntity<ApiDefaultResponse> refreshAuthentication(HttpServletRequest request) throws ApiException {
		String token = tokenEngine.getRefreshToken(request.getCookies());
		
		try {
			AuthenticationToken dbToken = fetchAndValidateToken(token);
			String accessToken = tokenEngine.createAccessToken(dbToken.getUser());
			HttpHeaders responseHeaders = new HttpHeaders();
	
			responseHeaders.add(HttpHeaders.SET_COOKIE, tokenEngine.createAccessCookie(accessToken));
			return ResponseEntity.ok().headers(responseHeaders).body(new ApiDefaultResponse());
		} catch (Exception e) {
			throw new ApiException(ApiStatus.AUTH_ERROR, "Invalid or expired refresh token");
		}
	}


}
