package com.abelium.inatrace.components.company;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.currencies.CurrencyTypeMapper;
import com.abelium.inatrace.components.codebook.currencies.CurrencyTypeService;
import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.common.CommonService;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.common.api.ApiCountry;
import com.abelium.inatrace.components.company.api.*;
import com.abelium.inatrace.components.company.types.CompanyAction;
import com.abelium.inatrace.components.company.types.CompanyTranslatables;
import com.abelium.inatrace.components.product.api.*;
import com.abelium.inatrace.components.user.UserApiTools;
import com.abelium.inatrace.components.user.UserQueries;
import com.abelium.inatrace.db.entities.common.*;
import com.abelium.inatrace.db.entities.company.*;
import com.abelium.inatrace.tools.ListTools;
import com.abelium.inatrace.types.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Lazy
@Component
public class CompanyApiTools {
	
	@Autowired
	private CommonService commonEngine;

	@Autowired
	private CommonApiTools commonApiTools;
	
	@Autowired
	private UserQueries userQueries;

	@Autowired
	private CompanyQueries companyQueries;

	@Autowired
	private CurrencyTypeService currencyTypeService;
	
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
		ac.currency = CommonApiTools.toApiCurrencyType(c.getCurrency());
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

	public ApiCompany toApiCompany(Company company) {
		if (company == null) return null;

		ApiCompany apiCompany = new ApiCompany();
		apiCompany.setId(company.getId());
		apiCompany.setName(company.getName());

		return apiCompany;
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
		c.setCurrency(ac.getCurrency() == null ? null : currencyTypeService.getCurrencyType(ac.getCurrency().getId()));
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

	public ApiUserCustomer toApiUserCustomer(UserCustomer userCustomer) {
		if (userCustomer == null) return null;

		ApiUserCustomer apiUserCustomer = new ApiUserCustomer();
		apiUserCustomer.setId(userCustomer.getId());
		apiUserCustomer.setFarmerCompanyInternalId(userCustomer.getFarmerCompanyInternalId());
		apiUserCustomer.setName(userCustomer.getName());
		apiUserCustomer.setSurname(userCustomer.getSurname());
		apiUserCustomer.setType(userCustomer.getType());
		apiUserCustomer.setPhone(userCustomer.getPhone());
		apiUserCustomer.setEmail(userCustomer.getEmail());
		apiUserCustomer.setGender(userCustomer.getGender());
		apiUserCustomer.setHasSmartphone(userCustomer.getHasSmartphone());
		// Company
		apiUserCustomer.setCompanyId(userCustomer.getCompany().getId());
		// Location
		apiUserCustomer.setLocation(toApiUserCustomerLocation(userCustomer.getUserCustomerLocation()));
		// Bank
		apiUserCustomer.setBank(toApiBankInformation(userCustomer.getBank()));
		// Farm
		apiUserCustomer.setFarm(toApiFarmInformation(userCustomer.getFarm()));
		// Associations
		apiUserCustomer.setAssociations(toApiUserCustomerAssociationList(userCustomer.getAssociations()));
		// Cooperatives
		apiUserCustomer.setCooperatives(toApiUserCustomerCooperativesList(userCustomer.getCooperatives()));

		return apiUserCustomer;
	}

	public ApiBankInformation toApiBankInformation(BankInformation bankInformation) {
		if (bankInformation == null) return null;

		ApiBankInformation apiBankInformation = new ApiBankInformation();
		apiBankInformation.setAccountHolderName(bankInformation.getAccountHolderName());
		apiBankInformation.setAccountNumber(bankInformation.getAccountNumber());
		apiBankInformation.setAdditionalInformation(bankInformation.getAdditionalInformation());
		apiBankInformation.setBankName(bankInformation.getBankName());

		return apiBankInformation;
	}

	public ApiFarmInformation toApiFarmInformation(FarmInformation farmInformation) {
		if (farmInformation == null) return null;

		ApiFarmInformation apiFarmInformation = new ApiFarmInformation();
		apiFarmInformation.setAreaOrganicCertified(farmInformation.getAreaOrganicCertified());
		apiFarmInformation.setCoffeeCultivatedArea(farmInformation.getCoffeeCultivatedArea());
		apiFarmInformation.setNumberOfTrees(farmInformation.getNumberOfTrees());
		apiFarmInformation.setOrganic(farmInformation.getOrganic());
		apiFarmInformation.setStartTransitionToOrganic(farmInformation.getStartTransitionToOrganic());
		apiFarmInformation.setTotalCultivatedArea(farmInformation.getTotalCultivatedArea());

		return apiFarmInformation;
	}

	public ApiUserCustomerAssociation toApiUserCustomerAssociation(UserCustomerAssociation userCustomerAssociation) {
		if (userCustomerAssociation == null) return null;

		ApiUserCustomerAssociation apiUserCustomerAssociation = new ApiUserCustomerAssociation();
		apiUserCustomerAssociation.setId(userCustomerAssociation.getId());
		apiUserCustomerAssociation.setCompany(toApiCompany(userCustomerAssociation.getCompany()));
		apiUserCustomerAssociation.setUserCustomer(new ApiUserCustomer());
		apiUserCustomerAssociation.getUserCustomer().setId(userCustomerAssociation.getUserCustomer().getId());

		return apiUserCustomerAssociation;
	}

	public ApiUserCustomerCooperative toApiUserCustomerCooperative(UserCustomerCooperative userCustomerCooperative) {
		if (userCustomerCooperative == null) return null;

		ApiUserCustomerCooperative apiUserCustomerCooperative = new ApiUserCustomerCooperative();
		apiUserCustomerCooperative.setId(userCustomerCooperative.getId());
		apiUserCustomerCooperative.setCompany(toApiCompany(userCustomerCooperative.getCompany()));
		apiUserCustomerCooperative.setUserCustomer(new ApiUserCustomer());
		apiUserCustomerCooperative.getUserCustomer().setId(userCustomerCooperative.getUserCustomer().getId());
		apiUserCustomerCooperative.setUserCustomerType(userCustomerCooperative.getRole());

		return apiUserCustomerCooperative;
	}

	public ApiUserCustomerLocation toApiUserCustomerLocation(UserCustomerLocation userCustomerLocation) {
		ApiUserCustomerLocation apiUserCustomerLocation = new ApiUserCustomerLocation();
		apiUserCustomerLocation.setAddress(toApiAddress(userCustomerLocation.getAddress()));
		apiUserCustomerLocation.setLatitude(userCustomerLocation.getLatitude());
		apiUserCustomerLocation.setLongitude(userCustomerLocation.getLongitude());
		apiUserCustomerLocation.setPubliclyVisible(userCustomerLocation.getPubliclyVisible());

		return apiUserCustomerLocation;
	}

	public ApiAddress toApiAddress(Address address) {
		ApiAddress apiAddress = new ApiAddress();
		apiAddress.setAddress(address.getAddress());
		apiAddress.setCell(address.getCell());
		apiAddress.setCity(address.getCity());
		apiAddress.setCountry(toApiCountry(address.getCountry()));
		apiAddress.setHondurasDepartment(address.getHondurasDepartment());
		apiAddress.setHondurasFarm(address.getHondurasFarm());
		apiAddress.setHondurasMunicipality(address.getHondurasMunicipality());
		apiAddress.setHondurasVillage(address.getHondurasVillage());
		apiAddress.setSector(address.getSector());
		apiAddress.setState(address.getState());
		apiAddress.setVillage(address.getVillage());
		apiAddress.setZip(address.getZip());

		return apiAddress;
	}

	public ApiCountry toApiCountry(Country country) {
		ApiCountry apiCountry = new ApiCountry();
		apiCountry.setId(country.getId());
		apiCountry.setCode(country.getCode());
		apiCountry.setName(country.getName());

		return apiCountry;
	}

	public List<ApiUserCustomerAssociation> toApiUserCustomerAssociationList(List<UserCustomerAssociation> userCustomerAssociationList) {
		if (userCustomerAssociationList == null) {
			return null;
		}
		return userCustomerAssociationList.stream().map(this::toApiUserCustomerAssociation).collect(Collectors.toList());
	}

	public List<ApiUserCustomerCooperative> toApiUserCustomerCooperativesList(List<UserCustomerCooperative> userCustomerCooperativeList) {
		if (userCustomerCooperativeList == null) {
			return null;
		}
		return userCustomerCooperativeList.stream().map(this::toApiUserCustomerCooperative).collect(Collectors.toList());
	}
				
}
