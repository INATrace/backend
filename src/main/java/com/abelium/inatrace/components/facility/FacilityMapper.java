package com.abelium.inatrace.components.facility;

import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductMapper;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.common.api.ApiCountry;
import com.abelium.inatrace.components.common.mappers.CountryMapper;
import com.abelium.inatrace.components.company.api.ApiAddress;
import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.facility.api.ApiFacilityLocation;
import com.abelium.inatrace.components.facility.api.ApiFacilityTranslation;
import com.abelium.inatrace.components.product.ProductApiTools;
import com.abelium.inatrace.components.value_chain.ValueChainMapper;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.facility.FacilitySemiProduct;
import com.abelium.inatrace.db.entities.facility.FacilityTranslation;
import com.abelium.inatrace.types.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Facility entity.
 *
 * @author Rene Flores, Pece Adjievski, Sunesis d.o.o.
 */
public final class FacilityMapper {

	private FacilityMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiFacility toApiFacilityBase(Facility entity, Language language) {

		if (entity == null) {
			return null;
		}

		FacilityTranslation translation = entity.getFacilityTranslations()
				.stream()
				.filter(facilityTranslation -> facilityTranslation.getLanguage().equals(language))
				.findFirst()
				.orElse(new FacilityTranslation());

		ApiFacility apiFacility = new ApiFacility();

		apiFacility.setId(entity.getId());
		apiFacility.setName(translation.getName());
		apiFacility.setIsCollectionFacility(entity.getIsCollectionFacility());
		apiFacility.setIsPublic(entity.getIsPublic());
		apiFacility.setDeactivated(entity.getIsDeactivated());
        
        ApiFacilityLocation apiFacilityLocation = new ApiFacilityLocation();
        apiFacilityLocation.setLatitude(entity.getFacilityLocation().getLatitude());
        apiFacilityLocation.setLongitude(entity.getFacilityLocation().getLongitude());
        apiFacility.setFacilityLocation(apiFacilityLocation);

		apiFacility.setCompany(CompanyMapper.toApiCompanyBase(entity.getCompany()));

		return apiFacility;
	}

	public static ApiFacility toApiFacility(Facility entity, Language language) {

		// Simplest apiFacility object
		ApiFacility apiFacility = toApiFacilityBase(entity, language);

		if (apiFacility == null) {
			return null;
		}

		apiFacility.setDisplayMayInvolveCollectors(entity.getDisplayMayInvolveCollectors());
		apiFacility.setDisplayOrganic(entity.getDisplayOrganic());
		apiFacility.setDisplayPriceDeductionDamage(entity.getDisplayPriceDeductionDamage());
		apiFacility.setDisplayWeightDeductionDamage(entity.getDisplayWeightDeductionDamage());
		apiFacility.setDisplayTare(entity.getDisplayTare());
		apiFacility.setDisplayWomenOnly(entity.getDisplayWomenOnly());
		apiFacility.setDisplayPriceDeterminedLater(entity.getDisplayPriceDeterminedLater());

		// Map facility location data
		ApiFacilityLocation apiFacilityLocation = new ApiFacilityLocation();
		ApiAddress apiAddress = new ApiAddress();

		apiAddress.setAddress(entity.getFacilityLocation().getAddress().getAddress());
		apiAddress.setCity(entity.getFacilityLocation().getAddress().getCity());
		apiAddress.setState(entity.getFacilityLocation().getAddress().getState());
		apiAddress.setZip(entity.getFacilityLocation().getAddress().getZip());
		apiAddress.setCell(entity.getFacilityLocation().getAddress().getCell());
		apiAddress.setSector(entity.getFacilityLocation().getAddress().getSector());
		apiAddress.setVillage(entity.getFacilityLocation().getAddress().getVillage());

		apiAddress.setCountry(CountryMapper.toApiCountry(entity.getFacilityLocation().getAddress().getCountry()));

		apiFacilityLocation.setAddress(apiAddress);
		apiFacilityLocation.setLatitude(entity.getFacilityLocation().getLatitude());
		apiFacilityLocation.setLongitude(entity.getFacilityLocation().getLongitude());
		apiFacilityLocation.setNumberOfFarmers(entity.getFacilityLocation().getNumberOfFarmers());
		apiFacilityLocation.setPinName(entity.getFacilityLocation().getPinName());
		apiFacilityLocation.setPubliclyVisible(entity.getFacilityLocation().getPubliclyVisible());
		apiFacility.setFacilityLocation(apiFacilityLocation);

		// Map facility type
		ApiFacilityType apiFacilityType = new ApiFacilityType();
		apiFacilityType.setId(entity.getFacilityType().getId());
		apiFacilityType.setCode(entity.getFacilityType().getCode());
		apiFacilityType.setLabel(entity.getFacilityType().getLabel());
		apiFacility.setFacilityType(apiFacilityType);

		// Map facility semi-products
		List<ApiSemiProduct> apiSemiProductList = new ArrayList<>();
		for (FacilitySemiProduct facilitySemiProduct : entity.getFacilitySemiProducts()) {
			apiSemiProductList.add(SemiProductMapper.toApiSemiProductBase(facilitySemiProduct.getSemiProduct(), ApiSemiProduct.class, language));
		}
		apiFacility.setFacilitySemiProductList(apiSemiProductList);

		// Map the facility final products
		apiFacility.setFacilityFinalProducts(entity.getFacilityFinalProducts().stream()
				.map(ffp -> ProductApiTools.toApiFinalProduct(ffp.getFinalProduct())).collect(Collectors.toList()));

		// Map the api facility value chain
		apiFacility.setFacilityValueChains(entity.getFacilityValueChains().stream()
				.map(fvc -> ValueChainMapper.toApiValueChainBase(fvc.getValueChain())).collect(Collectors.toList()));

		return apiFacility;
	}

	public static ApiFacility toApiFacilityDetail(Facility facility, Language language) {

		ApiFacility apiFacility = toApiFacility(facility, language);

		if (apiFacility == null) {
			return null;
		}

		apiFacility.setTranslations(facility
				.getFacilityTranslations()
				.stream()
				.map(FacilityMapper::toApiFacilityTranslation)
				.collect(Collectors.toList()));

		return apiFacility;
	}

	public static ApiFacilityTranslation toApiFacilityTranslation(FacilityTranslation facilityTranslation) {

		ApiFacilityTranslation apiFacilityTranslation = new ApiFacilityTranslation();
		apiFacilityTranslation.setName(facilityTranslation.getName());
		apiFacilityTranslation.setLanguage(facilityTranslation.getLanguage());

		return apiFacilityTranslation;
	}
}
