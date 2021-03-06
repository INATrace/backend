package com.abelium.INATrace.components.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.api.ApiPaginatedList;
import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.BaseEngine;
import com.abelium.INATrace.components.common.StorageKeyCache;
import com.abelium.INATrace.components.company.api.ApiCompany;
import com.abelium.INATrace.components.company.api.ApiCompanyActionRequest;
import com.abelium.INATrace.components.company.api.ApiCompanyGet;
import com.abelium.INATrace.components.company.api.ApiCompanyListResponse;
import com.abelium.INATrace.components.company.api.ApiCompanyPublic;
import com.abelium.INATrace.components.company.api.ApiCompanyUpdate;
import com.abelium.INATrace.components.company.api.ApiCompanyUser;
import com.abelium.INATrace.components.company.api.ApiListCompaniesRequest;
import com.abelium.INATrace.components.company.types.CompanyAction;
import com.abelium.INATrace.components.user.UserQueries;
import com.abelium.INATrace.db.entities.Company;
import com.abelium.INATrace.db.entities.CompanyUser;
import com.abelium.INATrace.db.entities.Document;
import com.abelium.INATrace.db.entities.User;
import com.abelium.INATrace.security.service.CustomUserDetails;
import com.abelium.INATrace.tools.PaginationTools;
import com.abelium.INATrace.tools.Queries;
import com.abelium.INATrace.tools.QueryTools;
import com.abelium.INATrace.tools.TorpedoProjector;
import com.abelium.INATrace.types.CompanyStatus;
import com.abelium.INATrace.types.CompanyUserRole;
import com.abelium.INATrace.types.Language;
import com.abelium.INATrace.types.UserRole;


@Lazy
@Component
public class CompanyEngine extends BaseEngine {
	
	@Autowired
	private CompanyApiTools companyApiTools;
	
	@Autowired
	private CompanyQueries companyQueries;
	
	@Autowired
	private UserQueries userQueries;
	
    private Company companyListQueryObject(ApiListCompaniesRequest request) {
        Company cProxy = Torpedo.from(Company.class);
        
        OnGoingLogicalCondition condition = Torpedo.condition();        
        if (StringUtils.isNotBlank(request.name)) {
            condition = condition.and(cProxy.getName()).like().startsWith(request.name);
        }
        if (request.status != null) {
        	condition = condition.and(cProxy.getStatus()).eq(request.status);
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "name": QueryTools.orderBy(request.sort, cProxy.getName()); break;
	        case "status": QueryTools.orderBy(request.sort, cProxy.getStatus()); break;
	        default: QueryTools.orderBy(request.sort, cProxy.getId());
        }
        return cProxy;
    }	

    @Transactional
	public ApiPaginatedList<ApiCompanyListResponse> listCompanies(ApiListCompaniesRequest request) {
    	return PaginationTools.createPaginatedResponse(em, request, () -> companyListQueryObject(request), 
    			CompanyApiTools::toApiCompanyListResponse); 
	}

    private TorpedoProjector<CompanyUser, ApiCompanyListResponse> userCompanyListQueryObject(Long userId, ApiListCompaniesRequest request) {
        CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
        
        Company cProxy = Torpedo.innerJoin(cuProxy.getCompany());
        OnGoingLogicalCondition condition = Torpedo.condition();
        condition = condition.and(cuProxy.getUser().getId()).eq(userId);
        if (StringUtils.isNotBlank(request.name)) {
            condition = condition.and(cProxy.getName()).like().startsWith(request.name);
        }
        if (request.status != null) {
        	condition = condition.and(cProxy.getStatus()).eq(request.status);
        }
        Document dProxy = Torpedo.leftJoin(cProxy.getLogo());
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "name": QueryTools.orderBy(request.sort, cProxy.getName()); break;
	        case "status": QueryTools.orderBy(request.sort, cProxy.getStatus()); break;
	        default: QueryTools.orderBy(request.sort, cProxy.getId());
        }
        return new TorpedoProjector<>(cuProxy, ApiCompanyListResponse.class).
        		add(cProxy.getId(), ApiCompanyListResponse::setId).
        		add(cProxy.getStatus(), ApiCompanyListResponse::setStatus).
        		add(cProxy.getName(), ApiCompanyListResponse::setName).
        		add(dProxy.getStorageKey(), (r, s) -> r.setLogoStorageKey(StorageKeyCache.put(s, userId)));
    }	    
    
	public ApiPaginatedList<ApiCompanyListResponse> listUserCompanies(Long userId, ApiListCompaniesRequest request) {
    	return PaginationTools.createPaginatedResponse(em, request, () -> userCompanyListQueryObject(userId, request)); 
	}
    

    @Transactional
	public ApiBaseEntity createCompany(Long userId, ApiCompany request) throws ApiException {
		User user = Queries.get(em, User.class, userId);
		Company company = new Company();
		CompanyUser companyUser = new CompanyUser();
		
		companyApiTools.updateCompany(userId, company, request, null);
		em.persist(company);
		
		companyUser.setUser(user);
		companyUser.setCompany(company);
		em.persist(companyUser);
		
		return new ApiBaseEntity(company);
	}

    @Transactional
	public ApiCompanyGet getCompany(CustomUserDetails authUser, long id, Language language) throws ApiException {
		Company c = companyQueries.fetchCompany(id);
		List<ApiCompanyUser> users = companyQueries.fetchUsersForCompany(id);
		
		if (authUser.getUserRole() != UserRole.ADMIN && !users.stream().anyMatch(u -> u.getId().equals(authUser.getUserId()))) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Not authorized");
		}
		
		List<CompanyAction> actions = new ArrayList<>();
		actions.add(CompanyAction.VIEW_COMPANY_PROFILE);
		actions.add(CompanyAction.UPDATE_COMPANY_PROFILE);
		
		if (authUser.getUserRole() == UserRole.ADMIN) {
			switch (c.getStatus()) {
				case REGISTERED: actions.addAll(Arrays.asList(CompanyAction.ACTIVATE_COMPANY, CompanyAction.DEACTIVATE_COMPANY)); break;
				case ACTIVE: actions.add(CompanyAction.DEACTIVATE_COMPANY); break;
				case DEACTIVATED: actions.add(CompanyAction.ACTIVATE_COMPANY); break;
			}
			actions.add(CompanyAction.ADD_USER_TO_COMPANY);
			if (!users.isEmpty()) {
				actions.add(CompanyAction.REMOVE_USER_FROM_COMPANY);
				actions.add(CompanyAction.SET_USER_COMPANY_ROLE);
			}
			actions.add(CompanyAction.MERGE_TO_COMPANY);
		}
		
		return companyApiTools.toApiCompanyGet(authUser.getUserId(), c, language, actions, users);
	}

    @Transactional
	public ApiCompanyPublic getCompanyPublic(long id, Language language) throws ApiException {
		Company c = companyQueries.fetchCompany(id);
		return companyApiTools.toApiCompanyPublic(c, language);
	}

    @Transactional
	public void updateCompany(CustomUserDetails authUser, ApiCompanyUpdate ac) throws ApiException {
		Company c = companyQueries.fetchCompany(authUser, ac.id);
		companyApiTools.updateCompanyWithUsers(authUser.getUserId(), c, ac);
	}
    
    @Transactional
	public void executeAction(ApiCompanyActionRequest request, CompanyAction action) throws ApiException {
    	Company c = companyQueries.fetchCompany(request.companyId);
    	switch (action) {
    		case ACTIVATE_COMPANY: activateCompany(c); break;
    		case DEACTIVATE_COMPANY: deactivateCompany(c); break;
    		case ADD_USER_TO_COMPANY: addUserToCompany(request.userId, c, request.companyUserRole); break;
    		case SET_USER_COMPANY_ROLE: setUserCompanyRole(request.userId, c, request.companyUserRole); break;
    		case REMOVE_USER_FROM_COMPANY: removeUserFromCompany(request.userId, c); break;
    		case MERGE_TO_COMPANY: mergeToCompany(c, request.otherCompanyId); break;
    		default: throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid action");
    	}
	}

	private void mergeToCompany(Company c, Long otherCompanyId) throws ApiException {
		Company other = companyQueries.fetchCompany(otherCompanyId);
		if (other.getStatus() != CompanyStatus.ACTIVE) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Merging to non-active company is impossible");
		}
		
		Set<Long> otherUsers = other.getUsers().stream().map(cu -> cu.getUser().getId()).collect(Collectors.toSet());
		for (CompanyUser cu : c.getUsers()) {
			if (!otherUsers.contains(cu.getUser().getId())) {
				CompanyUser otherCu = new CompanyUser();
				otherCu.setUser(cu.getUser());
				otherCu.setCompany(other);
				other.getUsers().add(otherCu);
			}
		}
		c.setStatus(CompanyStatus.DEACTIVATED);
	}

	private void removeUserFromCompany(Long userId, Company c) {
		c.getUsers().removeIf(cu -> cu.getUser().getId().equals(userId));
	}

	private void addUserToCompany(Long userId, Company c, CompanyUserRole cur) throws ApiException {
		if (c.getUsers().stream().anyMatch(cu -> cu.getUser().getId().equals(userId))) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "User already exists");
		}
		User user = userQueries.fetchUser(userId);
		CompanyUser cu = new CompanyUser();
		cu.setUser(user);
		cu.setCompany(c);
		cu.setRole(cur);
		c.getUsers().add(cu);
	}
	
	private void setUserCompanyRole(Long userId, Company c, CompanyUserRole cur) throws ApiException {
		Optional<CompanyUser> optCu = c.getUsers().stream().filter(cu -> cu.getUser().getId().equals(userId)).findAny();
		if (optCu.isEmpty()) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "User does not exist or does not exist on the company");
		}
		optCu.get().setRole(cur);
	}

    private void activateCompany(Company company) throws ApiException {
    	if (company.getStatus() == CompanyStatus.ACTIVE) {
    		throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid status");
    	}
    	company.setStatus(CompanyStatus.ACTIVE);
    	userQueries.activateUsersForCompany(company.getId());
    }
    
    private void deactivateCompany(Company company) throws ApiException {
    	if (company.getStatus() == CompanyStatus.DEACTIVATED) {
    		throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid status");
    	}
    	company.setStatus(CompanyStatus.DEACTIVATED);
    }


}
