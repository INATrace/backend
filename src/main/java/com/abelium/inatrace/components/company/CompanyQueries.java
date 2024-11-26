package com.abelium.inatrace.components.company;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.company.api.ApiCompanyUser;
import com.abelium.inatrace.components.value_chain.ValueChainMapper;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyTranslation;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.db.entities.product.Product;
import com.abelium.inatrace.db.entities.value_chain.CompanyValueChain;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.CompanyStatus;
import com.abelium.inatrace.types.CompanyUserRole;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.UserRole;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.torpedoquery.jakarta.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jakarta.jpa.Torpedo;
import java.util.List;
import java.util.Optional;

@Lazy
@Component
public class CompanyQueries extends BaseService {

	@Transactional
	public Company fetchCompany(Long companyId) throws ApiException {
    	Company company = Queries.get(em, Company.class, companyId);
    	if (company == null) {
    		throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id");
    	}
    	return company;
	}

	public List<Company> fetchAllActiveCompanies() {

		Company companyProxy = Torpedo.from(Company.class);
		Torpedo.where(companyProxy.getStatus()).eq(CompanyStatus.ACTIVE);
		return Torpedo.select(companyProxy).list(em);
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

	public List<ApiValueChain> fetchCompanyValueChains(Long companyId) {

		CompanyValueChain companyValueChainProxy = Torpedo.from(CompanyValueChain.class);
		OnGoingLogicalCondition companyCondition = Torpedo.condition(companyValueChainProxy.getCompany().getId()).eq(companyId);
		Torpedo.where(companyCondition);
		List<Long> valueChainIds = Torpedo.select(companyValueChainProxy.getValueChain().getId()).list(em);

		ValueChain valueChainProxy = Torpedo.from(ValueChain.class);
		OnGoingLogicalCondition valueChainCondition = Torpedo.condition().and(valueChainProxy.getId()).in(valueChainIds);
		Torpedo.where(valueChainCondition);

		return Torpedo.select(valueChainProxy).map(em, ValueChainMapper::toApiValueChainBase);
	}

	@Transactional
	public Company fetchCompany(CustomUserDetails authUser, Long companyId) throws ApiException {

		if (authUser.getUserRole() == UserRole.SYSTEM_ADMIN) {
			return fetchCompany(companyId);
		}

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

	public List<Long> fetchCompanyIdsForUserAdmin(Long userId) {
		CompanyUser companyUser = Torpedo.from(CompanyUser.class);
		Torpedo.where(companyUser.getUser().getId()).eq(userId).
				and(companyUser.getRole()).eq(CompanyUserRole.COMPANY_ADMIN);
		return Torpedo.select(companyUser.getCompany().getId()).list(em);
	}

	public List<Product> fetchCompanyProducts(Long companyId) throws ApiException {

		Company company = fetchCompany(companyId);

		TypedQuery<Product> companyProductsQuery = em.createNamedQuery("ProductCompany.getCompanyProductsWithAnyRole", Product.class);
		companyProductsQuery.setParameter("companyId", company.getId());

		return companyProductsQuery.getResultList();
	}

}
