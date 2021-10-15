package com.abelium.inatrace.components.facility;

import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductMapper;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.common.api.ApiCountry;
import com.abelium.inatrace.components.company.api.ApiAddress;
import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.facility.api.ApiFacilityLocation;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.facility.FacilitySemiProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for Facility entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
public final class FacilityMapper {

	private FacilityMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiFacility toApiFacility(Facility entity) {
		if (entity == null) return null;

		// Simplest apiFacility object
		ApiFacility apiFacility = new ApiFacility();
		apiFacility.setId(entity.getId());
		apiFacility.setName(entity.getName());
		apiFacility.setIsCollectionFacility(entity.getIsCollectionFacility());
		apiFacility.setIsPublic(entity.getIsPublic());
		apiFacility.setDisplayMayInvolveCollectors(entity.getDisplayMayInvolveCollectors());
		apiFacility.setDisplayOrganic(entity.getDisplayOrganic());
		apiFacility.setDisplayPriceDeductionDamage(entity.getDisplayPriceDeductionDamage());
		apiFacility.setDisplayTare(entity.getDisplayTare());
		apiFacility.setDisplayWomenOnly(entity.getDisplayWomenOnly());

		apiFacility.setCompany(CompanyMapper.toApiCompanyBase(entity.getCompany()));

		ApiFacilityLocation apiFacilityLocation = new ApiFacilityLocation();
		ApiAddress apiAddress = new ApiAddress();
		ApiCountry apiCountry = new ApiCountry();

		apiAddress.setAddress(entity.getFacilityLocation().getAddress().getAddress());
		apiAddress.setCity(entity.getFacilityLocation().getAddress().getCity());
		apiAddress.setState(entity.getFacilityLocation().getAddress().getState());
		apiAddress.setZip(entity.getFacilityLocation().getAddress().getZip());
		apiAddress.setCell(entity.getFacilityLocation().getAddress().getCell());
		apiAddress.setSector(entity.getFacilityLocation().getAddress().getSector());
		apiAddress.setVillage(entity.getFacilityLocation().getAddress().getVillage());

		apiCountry.setId(entity.getFacilityLocation().getAddress().getCountry().getId());
		apiCountry.setCode(entity.getFacilityLocation().getAddress().getCountry().getCode());
		apiCountry.setName(entity.getFacilityLocation().getAddress().getCountry().getName());
		apiAddress.setCountry(apiCountry);

		apiFacilityLocation.setAddress(apiAddress);
		apiFacilityLocation.setLatitude(entity.getFacilityLocation().getLatitude());
		apiFacilityLocation.setLongitude(entity.getFacilityLocation().getLongitude());
		apiFacilityLocation.setNumberOfFarmers(entity.getFacilityLocation().getNumberOfFarmers());
		apiFacilityLocation.setPinName(entity.getFacilityLocation().getPinName());
		apiFacilityLocation.setPubliclyVisible(entity.getFacilityLocation().getPubliclyVisible());
		apiFacility.setFacilityLocation(apiFacilityLocation);

		ApiFacilityType apiFacilityType = new ApiFacilityType();
		apiFacilityType.setId(entity.getFacilityType().getId());
		apiFacilityType.setCode(entity.getFacilityType().getCode());
		apiFacilityType.setLabel(entity.getFacilityType().getLabel());
		apiFacility.setFacilityType(apiFacilityType);

		List<ApiSemiProduct> apiSemiProductList = new ArrayList<>();
		for (FacilitySemiProduct facilitySemiProduct : entity.getFacilitySemiProducts()) {
			apiSemiProductList.add(SemiProductMapper.toApiSemiProductIdName(facilitySemiProduct.getSemiProduct()));
		}
		apiFacility.setFacilitySemiProductList(apiSemiProductList);

		return apiFacility;
	}
}
