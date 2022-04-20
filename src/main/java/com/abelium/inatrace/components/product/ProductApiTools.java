package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.common.CommonService;
import com.abelium.inatrace.components.common.StorageKeyCache;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.company.CompanyApiTools;
import com.abelium.inatrace.components.company.CompanyQueries;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.product.api.*;
import com.abelium.inatrace.components.value_chain.ValueChainApiTools;
import com.abelium.inatrace.components.value_chain.ValueChainQueries;
import com.abelium.inatrace.db.entities.common.BankInformation;
import com.abelium.inatrace.db.entities.common.Location;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.process.Process;
import com.abelium.inatrace.db.entities.process.ProcessDocument;
import com.abelium.inatrace.db.entities.process.ProcessStandard;
import com.abelium.inatrace.db.entities.product.*;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.tools.FieldTools;
import com.abelium.inatrace.tools.ListTools;
import com.abelium.inatrace.types.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Lazy
@Component
public class ProductApiTools {
	
	@Autowired
	private CommonService commonEngine;
	
	@Autowired
	private CommonApiTools commonApiTools;	
	
	@Autowired
	private CompanyApiTools companyApiTools;	

	@Autowired
	private CompanyQueries companyQueries;

	@Autowired
	private ValueChainApiTools valueChainApiTools;

	@Autowired
	private ValueChainQueries valueChainQueries;

	public static ApiProductListResponse toApiProductListResponse(long userId, Product product) {
		if (product == null) return null;
		
		ApiProductListResponse apiProduct = new ApiProductListResponse();
		CommonApiTools.updateApiBaseEntity(apiProduct, product);
		apiProduct.name = product.getName();
		apiProduct.photoStorageId = product.getPhoto() == null ? null : StorageKeyCache.put(product.getPhoto().getStorageKey(), userId);
		return apiProduct;
	}
	
	public ApiProduct toApiProduct(Long userId, Product p) {

		if (p == null) {
			return null;
		}
		
		ApiProduct ap = new ApiProduct();
		updateApiProductContent(userId, ap, p);
		
		ap.origin.locations = p.getOriginLocations().stream().map(ProductApiTools::toApiLocation).collect(Collectors.toList());
		ap.company = companyApiTools.toApiCompany(userId, p.getCompany(), null);
		ap.valueChain = valueChainApiTools.toApiValueChain(p.getValueChain());
		ap.associatedCompanies = p.getAssociatedCompanies().stream().map(ProductApiTools::toApiProductCompany).collect(Collectors.toList());

		ap.setDataSharingAgreements(p.getDataSharingAgreements().stream()
				.map(pdsa -> ProductApiTools.toApiProductDataSharingAgreement(pdsa, userId))
				.collect(Collectors.toList()));

		return ap;
	}

	public ApiProductLabelContent toApiProductLabelContent(Long userId, ProductLabelContent p) {
		if (p == null) return null;
		
		ApiProductLabelContent ap = new ApiProductLabelContent();
		updateApiProductContent(userId, ap, p);
		
		ap.origin.locations = p.getOriginLocations().stream().map(ProductApiTools::toApiLocation).collect(Collectors.toList());
		ap.company = companyApiTools.toApiCompany(userId, p.getCompany(), null);
		return ap;
	}
	
	public void updateApiProductContent(Long userId, ApiProductContent ap, ProductContent p) {
		CommonApiTools.updateApiBaseEntity(ap, p);
		ap.name = p.getName();
		ap.photo = CommonApiTools.toApiDocument(p.getPhoto(), userId);
		ap.description = p.getDescription();
		ap.ingredients = p.getIngredients();
		ap.nutritionalValue = p.getNutritionalValue();
		ap.howToUse = p.getHowToUse();
		ap.origin = new ApiProductOrigin();
		ap.origin.text = p.getOriginText();
		ap.keyMarketsShare = p.getKeyMarketsShare();
		ap.process = toApiProcess(userId, p.getProcess());
		ap.responsibility = toApiResponsibility(userId, p.getResponsibility());
		ap.sustainability = toApiSustainability(p.getSustainability());
		ap.settings = toApiSettings(userId, p.getSettings());
		ap.comparisonOfPrice = toApiComparisonOfPrice(p.getComparisonOfPrice());
		ap.knowledgeBlog = p.getKnowledgeBlog();
		ap.specialityDocument = CommonApiTools.toApiDocument(p.getSpecialityDocument(), userId);
		ap.specialityDescription = p.getSpecialityDescription();
		ap.setBusinessToCustomerSettings(toApiBusinessToCustomerSettings(p.getBusinessToCustomerSettings()));
	}
	
	
	public static ApiProcess toApiProcess(Long userId, Process p) {
		if (p == null) return null;

		ApiProcess ap = new ApiProcess();
		ap.production = p.getProduction();
		ap.storage = p.getStorage();
		ap.codesOfConduct = p.getCodesOfConduct();
		ap.standards = p.getStandards().stream().map(s -> toApiProcessStandard(userId, s)).collect(Collectors.toList());
		ap.records = p.getRecords().stream().map(s -> toApiProcessDocument(userId, s)).collect(Collectors.toList());
		return ap;
	}
	
	public static ApiSustainability toApiSustainability(Sustainability s) {
		if (s == null) return null;
		
		ApiSustainability as = new ApiSustainability();
		as.production = s.getProduction();
		as.packaging = s.getPackaging();
		as.co2Footprint = s.getCo2Footprint();
		return as;
	}
	
	public static ApiProductSettings toApiSettings(Long userId, ProductSettings ps) {
		if (ps == null) return null;
		
		ApiProductSettings aps = new ApiProductSettings();
		aps.checkAuthenticity = ps.getCheckAuthenticity();
		aps.traceOrigin = ps.getTraceOrigin();
		aps.giveFeedback = ps.getGiveFeedback();
		aps.costBreakdown = ps.getCostBreakdown();
		aps.increaseOfCoffee = ps.getIncreaseOfCoffee();
		aps.pricingTransparency = ps.getPricingTransparency();
		aps.incomeIncreaseDocument = CommonApiTools.toApiDocument(ps.getIncomeIncreaseDocument(), userId);
		aps.incomeIncreaseDescription = ps.getIncomeIncreaseDescription();
		aps.language = ps.getLanguage();
		aps.gdprText = ps.getGdprText();
		aps.privacyPolicyText = ps.getPrivacyPolicyText();
		aps.termsOfUseText = ps.getTermsOfUseText();
		return aps;
	}

	public static ApiComparisonOfPrice toApiComparisonOfPrice(ComparisonOfPrice cp) {
		if (cp == null) return null;
		
		ApiComparisonOfPrice acp = new ApiComparisonOfPrice();
		acp.description = cp.getDescription();
		acp.prices = cp.getPrices();
		return acp;
	}
	
	public static ApiResponsibility toApiResponsibility(Long userId, Responsibility r) {
		if (r == null) return null;
		
		ApiResponsibility ar = new ApiResponsibility();
		ar.laborPolicies = r.getLaborPolicies();
		ar.relationship = r.getRelationship();
		ar.farmer = r.getFarmer();
		ar.pictures = r.getPictures().stream().map(s -> toApiResponsibilityFarmerPicture(userId, s)).collect(Collectors.toList());
		ar.story = r.getStory();
		return ar;
	}

	public static ApiCertification toApiProcessStandard(Long userId, ProcessStandard ps) {
		if (ps == null) return null;
		
		ApiCertification aps = new ApiCertification();
		aps.description = ps.getDescription();
		aps.type = ps.getType();
		aps.validity = ps.getValidity();
		aps.certificate = CommonApiTools.toApiDocument(ps.getCertificate(), userId);
		return aps;
	}
	
	public static ApiProcessDocument toApiProcessDocument(Long userId, ProcessDocument pd) {
		if (pd == null) return null;
		
		ApiProcessDocument apd = new ApiProcessDocument();
		apd.description = pd.getDescription();
		apd.document = CommonApiTools.toApiDocument(pd.getDocument(), userId);
		return apd;
	}
	
	public static ApiResponsibilityFarmerPicture toApiResponsibilityFarmerPicture(Long userId, ResponsibilityFarmerPicture rfp) {
		if (rfp == null) return null;
		
		ApiResponsibilityFarmerPicture arfp = new ApiResponsibilityFarmerPicture();
		arfp.description = rfp.getDescription();
		arfp.document = CommonApiTools.toApiDocument(rfp.getDocument(), userId);
		return arfp;
	}	

	public static ApiLocation toApiLocation(Location l) {
		if (l == null) return null;
		
		ApiLocation al = new ApiLocation();
		al.address = CommonApiTools.toApiAddress(l.getAddress());
		al.latitude = l.getLatitude();
		al.longitude = l.getLongitude();
		al.numberOfFarmers = l.getNumberOfFarmers();
		al.pinName = l.getPinName();
		return al;
	}
	
	public static ApiProductCompany toApiProductCompany(ProductCompany pc) {
		ApiProductCompany apc = new ApiProductCompany();
		apc.company = CompanyApiTools.toApiCompanyListResponse(pc.getCompany());
		apc.type = pc.getType();
		return apc;
	}

	public static ApiBankInformation toApiBankInformation(BankInformation bankInformation) {
		if (bankInformation == null) return null;

		ApiBankInformation apiBankInformation = new ApiBankInformation();
		apiBankInformation.setBankName(bankInformation.getBankName());
		apiBankInformation.setAccountNumber(bankInformation.getAccountNumber());
		apiBankInformation.setAccountHolderName(bankInformation.getAccountHolderName());
		apiBankInformation.setAdditionalInformation(bankInformation.getAdditionalInformation());

		return apiBankInformation;
	}
	
	public static ApiCompanyCustomer toApiCompanyCustomer(CompanyCustomer pc) {
		if (pc == null) return null;
		
		ApiCompanyCustomer apc = new ApiCompanyCustomer();
		apc.id = pc.getId();
		apc.companyId = pc.getCompany() != null ? pc.getCompany().getId() : null;
		apc.name = pc.getName();
		apc.officialCompanyName = pc.getOfficialCompanyName();
		apc.vatId = pc.getVatId();
		apc.phone = pc.getPhone();
		apc.email = pc.getEmail();
		apc.location = CommonApiTools.toApiGeoAddress(pc.getLocation());
		return apc;
	}

	public static ApiProductDataSharingAgreement toApiProductDataSharingAgreement(ProductDataSharingAgreement pdsa, Long userId) {

		if (pdsa == null) {
			return null;
		}

		ApiProductDataSharingAgreement apiProductDataSharingAgreement = new ApiProductDataSharingAgreement();
		apiProductDataSharingAgreement.setId(pdsa.getId());
		apiProductDataSharingAgreement.setDescription(pdsa.getDescription());
		apiProductDataSharingAgreement.setDocument(CommonApiTools.toApiDocument(pdsa.getDocument(), userId));

		return apiProductDataSharingAgreement;
	}

	public static ApiBusinessToCustomerSettings toApiBusinessToCustomerSettings(BusinessToCustomerSettings businessToCustomerSettings) {
		if (businessToCustomerSettings == null) {
			return null;
		}

		ApiBusinessToCustomerSettings apiBusinessToCustomerSettings = new ApiBusinessToCustomerSettings();
		apiBusinessToCustomerSettings.setPrimaryColor(businessToCustomerSettings.getPrimaryColor());
		apiBusinessToCustomerSettings.setSecondaryColor(businessToCustomerSettings.getSecondaryColor());
		apiBusinessToCustomerSettings.setHeadingColor(businessToCustomerSettings.getHeadingColor());
		apiBusinessToCustomerSettings.setTextColor(businessToCustomerSettings.getTextColor());
		apiBusinessToCustomerSettings.setTabFairPrices(businessToCustomerSettings.getTabFairPrices());
		apiBusinessToCustomerSettings.setTabFeedback(businessToCustomerSettings.getTabFeedback());
		apiBusinessToCustomerSettings.setTabProducers(businessToCustomerSettings.getTabProducers());
		apiBusinessToCustomerSettings.setTabQuality(businessToCustomerSettings.getTabQuality());

		return apiBusinessToCustomerSettings;
	}
	
	public void updateProduct(CustomUserDetails authUser, Product p, ApiProduct pu) throws ApiException {

		updateProductContent(authUser.getUserId(), p, pu);

		if (pu.origin != null) {
			if (pu.origin.locations != null) updateOriginLocations(p, p.getOriginLocations(), pu.origin.locations);
		}
		if (pu.company != null) {
			p.setCompany(companyQueries.fetchCompany(pu.company.id));
		}
		if (pu.valueChain != null) {
			p.setValueChain(valueChainQueries.fetchValueChain(pu.valueChain.getId()));
		}
		if (pu.associatedCompanies != null && authUser.getUserRole() == UserRole.ADMIN) {
			updateProductCompanies(p, p.getAssociatedCompanies(), pu.associatedCompanies);
		}

		// Update the data sharing agreements
		p.getDataSharingAgreements().removeIf(pdsa -> pu.getDataSharingAgreements().stream().noneMatch(item -> pdsa.getId().equals(item.getId())));
		for (ApiProductDataSharingAgreement apiDataSharingAgreement : pu.getDataSharingAgreements()) {

			ProductDataSharingAgreement productDataSharingAgreement;
			if (apiDataSharingAgreement.getId() == null) {
				productDataSharingAgreement = new ProductDataSharingAgreement();
				productDataSharingAgreement.setProduct(p);
				p.getDataSharingAgreements().add(productDataSharingAgreement);
			} else {
				productDataSharingAgreement = p.getDataSharingAgreements().stream()
						.filter(pdsa -> pdsa.getId().equals(apiDataSharingAgreement.getId())).findAny().orElseThrow();
			}

			productDataSharingAgreement.setDescription(apiDataSharingAgreement.getDescription());
			productDataSharingAgreement.setDocument(
					commonEngine.fetchDocument(authUser.getUserId(), apiDataSharingAgreement.getDocument()));
		}
	}

	public void updateProductContent(Long userId, ProductContent p, ApiProductContent pu) throws ApiException {
		p.setName(pu.name);
		p.setPhoto(commonEngine.fetchDocument(userId, pu.photo));
		p.setDescription(pu.description);
		p.setIngredients(pu.ingredients);
		p.setNutritionalValue(pu.nutritionalValue);
		p.setHowToUse(pu.howToUse);
		if (pu.origin != null) {
			p.setOriginText(pu.origin.text);
		}
		if (pu.keyMarketsShare != null) p.setKeyMarketsShare(pu.keyMarketsShare);
		if (pu.process != null) updateProcess(userId, p.getProcess(), pu.process);
		if (pu.responsibility != null) updateResponsibility(userId, p.getResponsibility(), pu.responsibility);
		if (pu.sustainability != null) updateSustainability(p.getSustainability(), pu.sustainability);
		if (pu.settings != null) updateSettings(userId, p.getSettings(), pu.settings);
		if (pu.comparisonOfPrice != null) updateComparisonOfPrice(p.getComparisonOfPrice(), pu.comparisonOfPrice);
		if (pu.getBusinessToCustomerSettings() != null) {
			updateBusinessToCustomerSettings(p.getBusinessToCustomerSettings(), pu.getBusinessToCustomerSettings());
		} else {
			// Fill defaults
			ApiBusinessToCustomerSettings apiBusinessToCustomerSettings = new ApiBusinessToCustomerSettings();
			apiBusinessToCustomerSettings.setPrimaryColor("#25265e");
			apiBusinessToCustomerSettings.setSecondaryColor("#5dbccf");
			apiBusinessToCustomerSettings.setHeadingColor("#000000");
			apiBusinessToCustomerSettings.setTextColor("#000000");
			apiBusinessToCustomerSettings.setTabFairPrices(Boolean.TRUE);
			apiBusinessToCustomerSettings.setTabProducers(Boolean.TRUE);
			apiBusinessToCustomerSettings.setTabQuality(Boolean.TRUE);
			apiBusinessToCustomerSettings.setTabFeedback(Boolean.TRUE);

			updateBusinessToCustomerSettings(p.getBusinessToCustomerSettings(), apiBusinessToCustomerSettings);
		}
		p.setSpecialityDocument(commonEngine.fetchDocument(userId, pu.specialityDocument));
		p.setSpecialityDescription(pu.specialityDescription);
		p.setKnowledgeBlog(pu.knowledgeBlog);
	}
	
	public void updateProductLabelContent(Long userId, ProductLabelContent p, ApiProductLabelContent pu) throws ApiException {
		updateProductContent(userId, p, pu);
		if (pu.origin != null) {
			if (pu.origin.locations != null) updateOriginLocations(p, p.getOriginLocations(), pu.origin.locations);
		}
	}
	
	private void updateProductCompanies(Product p, List<ProductCompany> ac, List<ApiProductCompany> aac) throws ApiException {
		ac.clear();
		for (ApiProductCompany apc : aac) {
			ProductCompany pc = new ProductCompany();
			pc.setCompany(companyQueries.fetchCompany(apc.company.id));
			pc.setProduct(p);
			pc.setType(apc.type);
			ac.add(pc);
		}
	}

	private void updateOriginLocations(Product p, List<ProductLocation> pls, List<ApiLocation> als) throws ApiException  {
		pls.clear();
		for (ApiLocation apl : als) {
			pls.add(toProductLocation(p, apl));
		}
	}

	private void updateOriginLocations(ProductLabelContent p, List<ProductLocation> pls, List<ApiLocation> als) throws ApiException  {
		pls.clear();
		for (ApiLocation apl : als) {
			pls.add(toProductLocation(p, apl));
		}
	}
	
	
	private void updateLocation(Location l, ApiLocation apl) throws ApiException {
		l.setAddress(commonApiTools.toAddress(apl.address));
		l.setLatitude(apl.latitude);
		l.setLongitude(apl.longitude);
		l.setNumberOfFarmers(apl.numberOfFarmers);
		l.setPinName(apl.pinName);
	}
	
	private ProductLocation toProductLocation(Product p, ApiLocation al) throws ApiException {
		ProductLocation pl = new ProductLocation();
		pl.setProduct(p);
		updateLocation(pl, al);
		return pl;
	}

	private ProductLocation toProductLocation(ProductLabelContent p, ApiLocation al) throws ApiException {
		ProductLocation pl = new ProductLocation();
		pl.setContent(p);
		updateLocation(pl, al);
		return pl;
	}
	
	private void updateSustainability(Sustainability s, ApiSustainability as) {
		s.setProduction(as.production);
		s.setPackaging(as.packaging);
		s.setCo2Footprint(as.co2Footprint);
	}
	
	private void updateSettings(Long userId, ProductSettings ps, ApiProductSettings aps) throws ApiException {
		ps.setCheckAuthenticity(aps.checkAuthenticity);
		ps.setTraceOrigin(aps.traceOrigin);
		ps.setGiveFeedback(aps.giveFeedback);
		ps.setCostBreakdown(aps.costBreakdown);
		ps.setIncreaseOfCoffee(aps.increaseOfCoffee);
		ps.setPricingTransparency(aps.pricingTransparency);
		ps.setIncomeIncreaseDocument(commonEngine.fetchDocument(userId, aps.incomeIncreaseDocument));
		ps.setIncomeIncreaseDescription(aps.incomeIncreaseDescription);
		ps.setLanguage(aps.language);
		ps.setGdprText(aps.gdprText);
		ps.setPrivacyPolicyText(aps.privacyPolicyText);
		ps.setTermsOfUseText(aps.termsOfUseText);
	}

	private void updateComparisonOfPrice(ComparisonOfPrice ps, ApiComparisonOfPrice aps) {
		ps.setPrices(aps.prices);
		ps.setDescription(aps.description);
	}

	private void updateBusinessToCustomerSettings(BusinessToCustomerSettings b2c, ApiBusinessToCustomerSettings ab2c) {
		b2c.setPrimaryColor(ab2c.getPrimaryColor());
		b2c.setSecondaryColor(ab2c.getSecondaryColor());
		b2c.setHeadingColor(ab2c.getHeadingColor());
		b2c.setTextColor(ab2c.getTextColor());
		b2c.setTabFairPrices(ab2c.getTabFairPrices());
		b2c.setTabFeedback(ab2c.getTabFeedback());
		b2c.setTabProducers(ab2c.getTabProducers());
		b2c.setTabQuality(ab2c.getTabQuality());
	}
	
	private void updateResponsibility(Long userId, Responsibility r, ApiResponsibility ar) throws ApiException {
		r.setLaborPolicies(ar.laborPolicies);
		r.setRelationship(ar.relationship);
		r.setFarmer(ar.farmer);
		if (ar.pictures != null) {
			r.getPictures().clear();
			r.getPictures().addAll(ListTools.mapThrowable(ar.pictures, arfp -> toResponsibilityFarmerPicture(userId, r, arfp)));
		}
		r.setStory(ar.story);
	}

	private void updateProcess(Long userId, Process p, ApiProcess ap) throws ApiException {
		p.setProduction(ap.production);
		p.setStorage(ap.storage);
		p.setCodesOfConduct(ap.codesOfConduct);
		if (ap.standards != null) {
			p.getStandards().clear();
			p.getStandards().addAll(ListTools.mapThrowable(ap.standards, aps -> toProcessStandard(userId, p, aps)));
		}
		if (ap.records != null) {
			p.getRecords().clear();
			p.getRecords().addAll(ListTools.mapThrowable(ap.records, apr -> toProcessDocument(userId, p, apr)));
		}
	}

	private ResponsibilityFarmerPicture toResponsibilityFarmerPicture(Long userId, Responsibility r, ApiResponsibilityFarmerPicture arfp) throws ApiException {
		ResponsibilityFarmerPicture rfp = new ResponsibilityFarmerPicture();
		rfp.setResponsibility(r);
		rfp.setDescription(arfp.description);
		rfp.setDocument(commonEngine.fetchDocument(userId, arfp.document));
		return rfp;
	}
	
	private ProcessDocument toProcessDocument(Long userId, Process p, ApiProcessDocument ad) throws ApiException {
		ProcessDocument pd = new ProcessDocument();
		pd.setProcess(p);
		pd.setDescription(ad.description);
		pd.setDocument(commonEngine.fetchDocument(userId, ad.document));
		return pd;
	}
	
	private ProcessStandard toProcessStandard(Long userId, Process p, ApiCertification aps) throws ApiException {
		ProcessStandard ps = new ProcessStandard();
		ps.setProcess(p);
		ps.setDescription(aps.description);
		ps.setType(aps.type);
		ps.setValidity(aps.validity);
		ps.setCertificate(commonEngine.fetchDocument(userId, aps.certificate));
		return ps;
	}

	public ApiProductLabelValues toApiProductLabelValues(Long userId, ProductLabel pl) throws ApiException {
		if (pl == null) return null;
		
		ApiProductLabelValues apl = new ApiProductLabelValues();
		updateApiProductLabelValues(userId, pl, apl);
		return apl;
	}

	public void updateApiProductLabelValues(Long userId, ProductLabel pl, ApiProductLabelValues apl) throws ApiException {
		ApiProductLabelContent apiObject = toApiProductLabelContent(userId, pl.getContent()); // convert to api class to avoid possible field name inconsistencies
		updateApiProductLabelBase(apl, pl);
		apl.fields = new ArrayList<>(pl.getFields().size());
		for (ProductLabelField f : pl.getFields()) {
			if (Boolean.TRUE.equals(f.getVisible())) {
				apl.fields.add(new ApiProductLabelFieldValue(
					f.getName(),
					f.getSection(),
					FieldTools.getFieldValue(apiObject, f.getName())));
			}
		}
	}
	
	public static ApiProductLabel toApiProductLabel(ProductLabel pl) {
		if (pl == null) return null;
		
		ApiProductLabel apl = new ApiProductLabel();
		updateApiProductLabelBase(apl, pl);
		apl.fields = pl.getFields().stream().map(ProductApiTools::toApiProductLabelField).collect(Collectors.toList());
		return apl;
	}

	public static ApiProductLabelBase toApiProductLabelBase(ProductLabel pl) {
		if (pl == null) return null;
		
		ApiProductLabel apl = new ApiProductLabel();
		updateApiProductLabelBase(apl, pl);
		return apl;
	}
	
	private static ApiProductLabelField toApiProductLabelField(ProductLabelField plf) {
		if (plf == null) return null;
		
		ApiProductLabelField aplf = new ApiProductLabelField();
		aplf.name = plf.getName();
		aplf.visible = plf.getVisible();
		aplf.section = plf.getSection();
		return aplf;
	}
	
	private static ProductLabelField toProductLabelField(ApiProductLabelField aplf) {
		if (aplf == null) return null;
		
		ProductLabelField plf = new ProductLabelField();
		plf.setName(aplf.name);
		plf.setVisible(aplf.visible);
		plf.setSection(aplf.section);
		return plf;
	}
	
	private static void updateApiProductLabelBase(ApiProductLabelBase apl, ProductLabel pl) {
		CommonApiTools.updateApiBaseEntity(apl, pl);
		apl.status = pl.getStatus();
		apl.uuid = pl.getUuid();
		apl.productId = pl.getProduct().getId();
		apl.title = pl.getTitle();
		apl.language = pl.getLanguage();
	}
	
	public static void updateProductLabelBase(ProductLabel pl, ApiProductLabelBase apl) {
		pl.setTitle(apl.title);
	}

	public static void updateProductLabel(ProductLabel pl, ApiProductLabel apl) {
		updateProductLabelBase(pl, apl);
		if (apl.fields != null) pl.setFields(apl.fields.stream().map(ProductApiTools::toProductLabelField).collect(Collectors.toList()));
	}

	public void updateProductLabelFields(Long userId, ProductLabel pl, ApiProductLabelUpdateValues aplf) throws ApiException {
		updateProductLabelBase(pl, aplf);
		
		if (aplf.fields == null) return;
		
		ApiProductLabelContent ap = toApiProductLabelContent(userId, pl.getContent());
		for (ApiProductLabelFieldValue afv : aplf.fields) {
			FieldTools.updateField(ap, afv.name, afv.value);
		}
		updateProductLabelContent(userId, pl.getContent(), ap);
	}

	public void updateProductWithLabels(Long userId, ApiProduct ap, Product p) throws ApiException {
		ap.labels = new ArrayList<>();
		for (ProductLabel pl : p.getLabels()) {
			ap.labels.add(toApiProductLabelValues(userId, pl));
		}
	}
	
	public static ApiProductLabelBatch toApiProductLabelBatch(Long userId, ProductLabelBatch plb) {
		if (plb == null) return null;
		
		ApiProductLabelBatch aplb = new ApiProductLabelBatch();
		CommonApiTools.updateApiBaseEntity(aplb, plb);
		aplb.number = plb.getNumber();
		aplb.productionDate = plb.getProductionDate();
		aplb.expiryDate = plb.getExpiryDate();
		aplb.locations = plb.getLocations().stream().map(ProductApiTools::toApiLocation).collect(Collectors.toList());
		aplb.photo = CommonApiTools.toApiDocument(plb.getPhoto(), userId);
		aplb.checkAuthenticity = plb.getCheckAuthenticity();
		aplb.traceOrigin = plb.getTraceOrigin();
		return aplb;
	}

	public void updateProductLabelBatch(Long userId, ProductLabelBatch plb, ApiProductLabelBatch aplb) throws ApiException {
		plb.setNumber(aplb.number);
		plb.setProductionDate(aplb.productionDate);
		plb.setExpiryDate(aplb.expiryDate);
		if (aplb.locations != null) updateBatchLocations(plb, plb.getLocations(), aplb.locations);
		if (aplb.photo != null) plb.setPhoto(commonEngine.fetchDocument(userId, aplb.photo));
		plb.setCheckAuthenticity(aplb.checkAuthenticity);
		plb.setTraceOrigin(aplb.traceOrigin);
	}
	
	private void updateBatchLocations(ProductLabelBatch plb, List<BatchLocation> bls, List<ApiLocation> als) throws ApiException  {
		bls.clear();
		for (ApiLocation apl : als) {
			bls.add(toBatchLocation(plb, apl));
		}
	}
	
	private BatchLocation toBatchLocation(ProductLabelBatch plb, ApiLocation al) throws ApiException {
		BatchLocation bl = new BatchLocation();
		bl.setBatch(plb);
		updateLocation(bl, al);
		return bl;
	}

	public void updateCompanyCustomer(CompanyCustomer pc, ApiCompanyCustomer apc) throws ApiException {
		pc.setName(apc.name);
		pc.setOfficialCompanyName(apc.officialCompanyName);
		pc.setVatId(apc.vatId);
		pc.setContact(apc.contact);
		pc.setPhone(apc.phone);
		pc.setEmail(apc.email);
		pc.setLocation(commonApiTools.toGeoAddress(apc.location));
	}
	
	public ApiKnowledgeBlog toApiKnowledgeBlog(Long userId, KnowledgeBlog kb) {
		if (kb == null) return null;
		
		ApiKnowledgeBlog akb = new ApiKnowledgeBlog();
		updateApiKnowledgeBlogBase(kb, akb);
		akb.summary = kb.getSummary();
		akb.content = kb.getContent();
		akb.documents = kb.getDocuments().stream().map(d -> CommonApiTools.toApiDocument(d, userId)).collect(Collectors.toList());
		return akb;
	}
	
	public static ApiKnowledgeBlogBase toApiKnowledgeBlogBase(KnowledgeBlog kb) {
		if (kb == null) return null;
		
		ApiKnowledgeBlogBase akbb = new ApiKnowledgeBlogBase();
		updateApiKnowledgeBlogBase(kb, akbb);
		return akbb;
	}
	
	public static void updateApiKnowledgeBlogBase(KnowledgeBlog kb, ApiKnowledgeBlogBase akbb) {
		akbb.id = kb.getId();
		akbb.type = kb.getType();
		akbb.title = kb.getTitle();
		akbb.date = kb.getDate();
		akbb.youtubeUrl = kb.getYoutubeUrl();
	}

	public void updateKnowledgeBlog(Long userId, KnowledgeBlog kb, ApiKnowledgeBlog akb) throws ApiException {
		if (akb == null) return;
		
		kb.setTitle(akb.getTitle());
		kb.setSummary(akb.getSummary());
		kb.setContent(akb.getContent());
		kb.setDate(akb.getDate());
		kb.setYoutubeUrl(akb.getYoutubeUrl());
		if (akb.documents != null) {
			kb.getDocuments().clear();
			kb.getDocuments().addAll(ListTools.mapThrowable(akb.documents, ad -> commonEngine.fetchDocument(userId, ad)));
		}
	}
	
	public static ApiProductLabelFeedback toApiProductLabelFeedback(ProductLabelFeedback fb) {
		if (fb == null) return null;
		
		ApiProductLabelFeedback afb = new ApiProductLabelFeedback();
		afb.id = fb.getId();
		afb.labelId = fb.getLabel().getId();
		afb.type = fb.getType();
		afb.email = fb.getEmail();
		afb.feedback = fb.getFeedback();
		afb.gdprConsent = fb.getGdprConsent();
		afb.privacyPolicyConsent = fb.getPrivacyPolicyConsent();
		afb.termsOfUseConsent = fb.getTermsOfUseConsent();
		afb.questionnaireAnswers = fb.getQuestionnaireAnswers();
		return afb;
	}	

	public static void updateProductLabelFeedback(ProductLabelFeedback fb, ApiProductLabelFeedback afb) {
		if (afb == null) return;
		
		fb.setType(afb.type);
		fb.setEmail(afb.email);
		fb.setFeedback(afb.feedback);
		fb.setGdprConsent(afb.gdprConsent);
		fb.setPrivacyPolicyConsent(afb.privacyPolicyConsent);
		fb.setTermsOfUseConsent(afb.termsOfUseConsent);
		fb.setQuestionnaireAnswers(afb.questionnaireAnswers);
	}

	public static ApiFinalProduct toApiFinalProductBase(FinalProduct entity) {

		if (entity == null) {
			return null;
		}

		ApiFinalProduct apiFinalProduct = new ApiFinalProduct();
		apiFinalProduct.setId(entity.getId());
		apiFinalProduct.setName(entity.getName());

		// Map the basic info of the owner product
		apiFinalProduct.setProduct(new ApiProductBase());
		apiFinalProduct.getProduct().setId(entity.getProduct().getId());
		apiFinalProduct.getProduct().setName(entity.getProduct().getName());

		return apiFinalProduct;
	}

	public static ApiFinalProduct toApiFinalProduct(FinalProduct entity) {

		ApiFinalProduct apiFinalProduct = toApiFinalProductBase(entity);

		if (apiFinalProduct == null) {
			return null;
		}

		apiFinalProduct.setId(entity.getId());
		apiFinalProduct.setName(entity.getName());
		apiFinalProduct.setDescription(entity.getDescription());
		apiFinalProduct.setMeasurementUnitType(MeasureUnitTypeMapper.toApiMeasureUnitType(entity.getMeasurementUnitType()));

		return apiFinalProduct;
	}

	public static ApiFinalProduct toApiFinalProductWithLabels(FinalProduct entity) {

		ApiFinalProduct apiFinalProduct = toApiFinalProduct(entity);

		if (apiFinalProduct == null) {
			return null;
		}

		apiFinalProduct.setLabels(entity.getFinalProductLabels().stream()
				.map(finalProductLabel -> ProductApiTools.toApiProductLabelBase(finalProductLabel.getProductLabel()))
				.collect(Collectors.toList()));

		return apiFinalProduct;
	}

	public void loadBusinessToCustomerSettings(ProductLabel productLabel, ApiProductLabelValuesExtended apiProductLabelValuesExtended) {
		if (apiProductLabelValuesExtended.getBusinessToCustomerSettings() == null) {
			apiProductLabelValuesExtended.setBusinessToCustomerSettings(new ApiBusinessToCustomerSettings());
		}
		ApiBusinessToCustomerSettings b2cSettings = apiProductLabelValuesExtended.getBusinessToCustomerSettings();

		// Load B2C settings from product
		BusinessToCustomerSettings b2cSettingsProduct = productLabel.getProduct().getBusinessToCustomerSettings();
		b2cSettings.setPrimaryColor(b2cSettingsProduct.getPrimaryColor());
		b2cSettings.setSecondaryColor(b2cSettingsProduct.getSecondaryColor());
		b2cSettings.setHeadingColor(b2cSettings.getHeadingColor());
		b2cSettings.setTextColor(b2cSettings.getTextColor());

		// If product label defines values, overwrite settings from product
		BusinessToCustomerSettings b2cSettingsProductLabel = productLabel.getContent().getBusinessToCustomerSettings();
		if (b2cSettingsProductLabel.getPrimaryColor() != null) {
			b2cSettings.setPrimaryColor(b2cSettingsProductLabel.getPrimaryColor());
		}
		if (b2cSettingsProductLabel.getSecondaryColor() != null) {
			b2cSettings.setSecondaryColor(b2cSettingsProductLabel.getSecondaryColor());
		}
		if (b2cSettingsProductLabel.getHeadingColor() != null) {
			b2cSettings.setHeadingColor(b2cSettingsProductLabel.getHeadingColor());
		}
		if (b2cSettingsProductLabel.getTextColor() != null) {
			b2cSettings.setTextColor(b2cSettingsProductLabel.getTextColor());
		}
		if (b2cSettingsProductLabel.getTabFairPrices() != null) {
			b2cSettings.setTabFairPrices(b2cSettingsProductLabel.getTabFairPrices());
		}
		if (b2cSettingsProductLabel.getTabFeedback() != null) {
			b2cSettings.setTabFeedback(b2cSettingsProductLabel.getTabFeedback());
		}
		if (b2cSettingsProductLabel.getTabProducers() != null) {
			b2cSettings.setTabProducers(b2cSettingsProductLabel.getTabProducers());
		}
		if (b2cSettingsProductLabel.getTabQuality() != null) {
			b2cSettings.setTabQuality(b2cSettingsProductLabel.getTabQuality());
		}
	}
	
}
