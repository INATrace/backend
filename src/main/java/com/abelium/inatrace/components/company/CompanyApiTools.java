package com.abelium.inatrace.components.company;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.common.CommonEngine;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.components.company.api.ApiCompanyDocument;
import com.abelium.inatrace.components.company.api.ApiCompanyGet;
import com.abelium.inatrace.components.company.api.ApiCompanyListResponse;
import com.abelium.inatrace.components.company.api.ApiCompanyPublic;
import com.abelium.inatrace.components.company.api.ApiCompanyUpdate;
import com.abelium.inatrace.components.company.api.ApiCompanyUser;
import com.abelium.inatrace.components.company.types.CompanyAction;
import com.abelium.inatrace.components.company.types.CompanyTranslatables;
import com.abelium.inatrace.components.user.UserApiTools;
import com.abelium.inatrace.components.user.UserQueries;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyCertification;
import com.abelium.inatrace.db.entities.company.CompanyDocument;
import com.abelium.inatrace.db.entities.company.CompanyTranslation;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.tools.ListTools;
import com.abelium.inatrace.types.Language;


@Lazy
@Component
public class CompanyApiTools {
	
	@Autowired
	private CommonEngine commonEngine;

	@Autowired
	private CommonApiTools commonApiTools;
	
	@Autowired
	private UserQueries userQueries;

	@Autowired
	private CompanyQueries companyQueries;
	
	public static ApiCompanyListResponse toApiCompanyListResponse(Company company) {
		if (company == null) return null;
		
		ApiCompanyListResponse apiCompany = new ApiCompanyListResponse();
		CommonApiTools.updateApiBaseEntity(apiCompany, company);
		apiCompany.name = company.getName();
		apiCompany.status = company.getStatus();
		return apiCompany;
	}

	public void updateApiCompany(Long userId, ApiCompany ac, Company c, Language language) {
		CommonApiTools.updateApiBaseEntity(ac, c);
		if (language == null || Language.EN == language) updateApiCompanyTranslatables(userId, ac, c);
		else {
			CompanyTranslation ct = companyQueries.fetchCompanyTranslation(c, language);
			if (ct != null) updateApiCompanyTranslatables(userId, ac, ct);
		}
		ac.logo = CommonApiTools.toApiDocument(c.getLogo(), userId);
		ac.headquarters = CommonApiTools.toApiAddress(c.getHeadquarters());
		ac.manager = c.getManager();
		ac.email = c.getEmail();
		ac.phone = c.getPhone();
	}

	public void updateApiCompanyPublic(ApiCompanyPublic ac, Company c, Language language) {
		if (language == null || Language.EN == language) updateApiCompanyPublicTranslatables(ac, c);
		else {
			CompanyTranslation ct = companyQueries.fetchCompanyTranslation(c, language);
			if (ct != null) updateApiCompanyPublicTranslatables(ac, ct);
		}
	}
	
	public ApiCompanyGet toApiCompanyGet(Long userId, Company c, Language language, 
			List<CompanyAction> actions,
			List<ApiCompanyUser> users) {
		if (c == null) return null;
		
		ApiCompanyGet ac = new ApiCompanyGet();
		updateApiCompany(userId, ac, c, language);
		ac.actions = actions;
		ac.users = users;
		return ac;
	}

	public ApiCompanyPublic toApiCompanyPublic(Company c, Language language) {
		if (c == null) return null;
		
		ApiCompanyPublic ac = new ApiCompanyPublic();
		updateApiCompanyPublic(ac, c, language);
		return ac;
	}
	
	public ApiCompany toApiCompany(Long userId, Company c, Language language) {
		if (c == null) return null;
		
		ApiCompany ac = new ApiCompany();
		updateApiCompany(userId, ac, c, language);
		return ac;
	}
	

	public void updateCompany(Long userId, Company c, ApiCompany ac, Language language) throws ApiException {
		if (Language.EN == language || language == null) {
			updateCompanyTranslatables(userId, c, ac);
		} else {
			CompanyTranslation ct = companyQueries.fetchCompanyTranslation(c, language);
			if (ct == null) ct = companyQueries.createAndPersistCompanyTranslation(c, language);
			updateCompanyTranslatables(userId, ct, ac);
		}
		c.setLogo(commonEngine.fetchDocument(userId, ac.logo));
		c.setHeadquarters(commonApiTools.toAddress(ac.headquarters));
		c.setEmail(ac.email);
		c.setPhone(ac.phone);
	}
	
	
	private void updateCompanyTranslatables(Long userId, CompanyTranslatables c, ApiCompany ac) throws ApiException {
		c.setName(ac.name);
		c.setAbbreviation(ac.abbreviation);
		c.setAbout(ac.about);
		c.setInterview(ac.interview);
		c.setWebPage(ac.webPage);
		if (ac.mediaLinks != null) {
			c.setMediaLinks(ac.mediaLinks);
		}
		
		Company cc = (c instanceof Company) ? (Company) c : null;
		CompanyTranslation cct = (c instanceof CompanyTranslation) ? (CompanyTranslation) c : null;
		
		if (ac.documents != null) {
			c.getDocuments().clear();
			c.getDocuments().addAll(ListTools.mapThrowable(ac.documents, acd -> toCompanyDocument(userId, cc, cct, acd)));
		}
		if (ac.certifications != null) {
			c.getCertifications().clear();
			c.getCertifications().addAll(ListTools.mapThrowable(ac.certifications, acc -> toCompanyCertification(userId, cc, cct, acc)));
		}
	}

	private static void updateApiCompanyTranslatables(Long userId, ApiCompany ac, CompanyTranslatables c) {
		ac.name = c.getName();
		ac.abbreviation = c.getAbbreviation();
		ac.about = c.getAbout();
		ac.interview = c.getInterview();
		ac.webPage = c.getWebPage();
		ac.mediaLinks = c.getMediaLinks();
		ac.documents = c.getDocuments().stream().map(cd -> toApiCompanyDocument(userId, cd)).collect(Collectors.toList());
		ac.certifications = c.getCertifications().stream().map(cd -> toApiCertification(userId, cd)).collect(Collectors.toList());
	}
	
	private static void updateApiCompanyPublicTranslatables(ApiCompanyPublic ac, CompanyTranslatables c) {
		ac.name = c.getName();
		ac.interview = c.getInterview();
		ac.documents = c.getDocuments().stream().map(cd -> toApiCompanyDocument(null, cd)).collect(Collectors.toList());
		ac.certifications = c.getCertifications().stream().map(cd -> toApiCertification(null, cd)).collect(Collectors.toList());
		ac.about = c.getAbout();
		ac.mediaLinks = c.getMediaLinks();
	}
	
	public void updateCompanyWithUsers(Long userId, Company c, ApiCompanyUpdate ac) throws ApiException {
		updateCompany(userId, c, ac, ac.language);
		if (ac.users != null) {
			Set<Long> apiUserIds = ac.users.stream().map(ApiBaseEntity::getId).collect(Collectors.toSet()); 
			Set<Long> dbUserIds = c.getUsers().stream().map(cu -> cu.getUser().getId()).collect(Collectors.toSet());
			
	        c.getUsers().removeIf(dbItem -> !apiUserIds.contains(dbItem.getUser().getId()));
	        for (ApiBaseEntity be : ac.users) {
	        	if (!dbUserIds.contains(be.getId())) {
	        		CompanyUser cu = new CompanyUser();
	        		cu.setCompany(c);
	        		cu.setUser(userQueries.fetchUser(be.getId()));
	        		c.getUsers().add(cu);
	        	}
	        }
		}
	}
	
	public static ApiCompanyDocument toApiCompanyDocument(Long userId, CompanyDocument cd) {
		if (cd == null) return null;
		
		ApiCompanyDocument acd = new ApiCompanyDocument();
		acd.type = cd.getType();
		acd.category = cd.getCategory();
		acd.name = cd.getName();
		acd.quote = cd.getQuote();
		acd.description = cd.getDescription();
		acd.link = cd.getLink();
		acd.document = CommonApiTools.toApiDocument(cd.getDocument(), userId);
		return acd;
	}

	public static ApiCertification toApiCertification(Long userId, CompanyCertification cc) {
		if (cc == null) return null;
		
		ApiCertification acc = new ApiCertification();
		acc.type = cc.getType();
		acc.description = cc.getDescription();
		acc.validity = cc.getValidity();
		acc.certificate = CommonApiTools.toApiDocument(cc.getCertificate(), userId);
		return acc;
	}
	
	private CompanyDocument toCompanyDocument(Long userId, Company c, CompanyTranslation ct, ApiCompanyDocument ad) throws ApiException {
		CompanyDocument pd = new CompanyDocument();
		pd.setCompany(c);
		pd.setCompanyTranslation(ct);
		pd.setType(ad.type);
		pd.setCategory(ad.category);
		pd.setName(ad.name);
		pd.setQuote(ad.quote);
		pd.setDescription(ad.description);
		pd.setLink(ad.link);
		pd.setDocument(commonEngine.fetchDocument(userId, ad.document));
		return pd;
	}
	
	private CompanyCertification toCompanyCertification(Long userId, Company c, CompanyTranslation ct, ApiCertification ac) throws ApiException {
		CompanyCertification pd = new CompanyCertification();
		pd.setCompany(c);
		pd.setCompanyTranslation(ct);
		pd.setType(ac.type);
		pd.setDescription(ac.description);
		pd.setValidity(ac.validity);
		pd.setCertificate(commonEngine.fetchDocument(userId, ac.certificate));
		return pd;
	}
	
	public static ApiCompanyUser toApiCompanyUser(CompanyUser cu) {
		ApiCompanyUser acu = new ApiCompanyUser();
		UserApiTools.updateApiUserBase(acu, cu.getUser());
		acu.companyRole = cu.getRole();
		return acu;
	}
				
}
