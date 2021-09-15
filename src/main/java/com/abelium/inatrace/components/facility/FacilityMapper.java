package com.abelium.inatrace.components.facility;

import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.facility.api.ApiFacilityLocation;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.common.api.ApiCountry;
import com.abelium.inatrace.components.company.api.ApiAddress;
import com.abelium.inatrace.components.facility.api.ApiFacilitySemiProduct;
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

		// Simplest apiFacility object
		ApiFacility apiFacility = new ApiFacility();
		apiFacility.setId(entity.getId());
		apiFacility.setName(entity.getName());
		apiFacility.setIsCollectionFacility(entity.getIsCollectionFacility());
		apiFacility.setIsPublic(entity.getIsPublic());

		ApiFacilityLocation apiFacilityLocation = new ApiFacilityLocation();
		ApiAddress apiAddress = new ApiAddress();
		ApiCountry apiCountry = new ApiCountry();

		apiAddress.setAddress(entity.getFacilityLocation().getAddress().getAddress());
		apiAddress.setCity(entity.getFacilityLocation().getAddress().getCity());
		apiAddress.setState(entity.getFacilityLocation().getAddress().getState());
		apiAddress.setZip(entity.getFacilityLocation().getAddress().getZip());

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

		List<ApiFacilitySemiProduct> apiFacilitySemiProductList = new ArrayList<>();

		for (FacilitySemiProduct facilitySemiProduct : entity.getFacilitySemiProducts()) {
			ApiFacilitySemiProduct apiFacilitySemiProduct = new ApiFacilitySemiProduct();

			ApiSemiProduct apiSemiProduct = new ApiSemiProduct();
			apiSemiProduct.setId(facilitySemiProduct.getSemiProduct().getId());
			apiSemiProduct.setName(facilitySemiProduct.getSemiProduct().getName());

			apiFacilitySemiProduct.setId(facilitySemiProduct.getId());
			apiFacilitySemiProduct.setApiSemiProduct(apiSemiProduct);

			apiFacilitySemiProductList.add(apiFacilitySemiProduct);
		}
		apiFacility.setFacilitySemiProductList(apiFacilitySemiProductList);

		return apiFacility;
	}
}
