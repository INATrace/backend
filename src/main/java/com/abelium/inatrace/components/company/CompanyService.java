package com.abelium.inatrace.components.company;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.StorageKeyCache;
import com.abelium.inatrace.components.company.api.*;
import com.abelium.inatrace.components.company.types.CompanyAction;
import com.abelium.inatrace.components.user.UserQueries;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import com.abelium.inatrace.tools.TorpedoProjector;
import com.abelium.inatrace.types.CompanyStatus;
import com.abelium.inatrace.types.CompanyUserRole;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Lazy
@Service
public class CompanyService extends BaseService {
	
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
