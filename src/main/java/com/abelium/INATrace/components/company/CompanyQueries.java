package com.abelium.INATrace.components.company;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.torpedoquery.jpa.Torpedo;

import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.BaseEngine;
import com.abelium.INATrace.components.company.api.ApiCompanyUser;
import com.abelium.INATrace.db.entities.Company;
import com.abelium.INATrace.db.entities.CompanyTranslation;
import com.abelium.INATrace.db.entities.CompanyUser;
import com.abelium.INATrace.security.service.CustomUserDetails;
import com.abelium.INATrace.tools.Queries;
import com.abelium.INATrace.types.CompanyStatus;
import com.abelium.INATrace.types.Language;
import com.abelium.INATrace.types.UserRole;

@Lazy
@Component
public class CompanyQueries extends BaseEngine {

	@Transactional
	public Company fetchCompany(Long companyId) throws ApiException {
    	Company company = Queries.get(em, Company.class, companyId);
    	if (company == null) {
    		throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id");
    	}
    	return company;
	}
	
	@Transactional
	public List<Long> fetchCompanyIdsForUser(Long userId, List<CompanyStatus> statuses) {
		CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
		Torpedo.where(cuProxy.getUser().getId()).eq(userId).
			and(cuProxy.getCompany().getStatus()).in(statuses);
		return Torpedo.select(cuProxy.getCompany().getId()).list(em);
	}	

	@Transactional
	public List<Long> fetchUserIdsForCompany(Long companyId) throws ApiException {
		CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
		Torpedo.where(cuProxy.getCompany().getId()).eq(companyId);
		return Torpedo.select(cuProxy.getUser().getId()).list(em);
	}

	@Transactional
	public List<ApiCompanyUser> fetchUsersForCompany(Long companyId) throws ApiException {
		CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
		Torpedo.where(cuProxy.getCompany().getId()).eq(companyId);
		return Torpedo.select(cuProxy).map(em, CompanyApiTools::toApiCompanyUser);
	}

	@Transactional
	public Company fetchCompany(CustomUserDetails authUser, Long companyId) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) 
			return fetchCompany(companyId);
		
		CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
		Torpedo.where(cuProxy.getCompany().getId()).eq(companyId).
			and(cuProxy.getUser().getId()).eq(authUser.getUserId());
		Optional<Company> oc = Torpedo.select(cuProxy.getCompany()).get(em);
		if (oc.isEmpty()) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id or not authorized");
		}
		return oc.get();
	}
	
	@Transactional
	public CompanyTranslation fetchCompanyTranslation(Company company, Language language) {
		CompanyTranslation ctProxy = Torpedo.from(CompanyTranslation.class);
		Torpedo.where(ctProxy.getCompany()).eq(company).and(ctProxy.getLanguage()).eq(language);
		return Torpedo.select(ctProxy).get(em).orElse(null);
	}

	@Transactional
	public CompanyTranslation createAndPersistCompanyTranslation(Company c, Language language) {
		CompanyTranslation ct = new CompanyTranslation(language);
		ct.setCompany(c);
		em.persist(ct);
		return ct;
	}

}
