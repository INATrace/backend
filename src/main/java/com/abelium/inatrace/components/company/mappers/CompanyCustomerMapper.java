package com.abelium.inatrace.components.company.mappers;

import com.abelium.inatrace.components.common.mappers.CountryMapper;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.company.api.ApiGeoAddress;
import com.abelium.inatrace.db.entities.common.GeoAddress;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;

public class CompanyCustomerMapper {

	public static ApiCompanyCustomer toApiCompanyCustomerBase(CompanyCustomer companyCustomer) {

		if (companyCustomer == null) {
			return null;
		}

		ApiCompanyCustomer apiCompanyCustomer = new ApiCompanyCustomer();
		apiCompanyCustomer.setId(companyCustomer.getId());
		apiCompanyCustomer.setCompanyId(companyCustomer.getCompany().getId());
		apiCompanyCustomer.setName(companyCustomer.getName());
		apiCompanyCustomer.setEmail(companyCustomer.getEmail());

		return apiCompanyCustomer;
	}

	public static ApiCompanyCustomer toApiCompanyCustomer(CompanyCustomer companyCustomer) {

		ApiCompanyCustomer apiCompanyCustomer = toApiCompanyCustomerBase(companyCustomer);
		if (apiCompanyCustomer == null) {
			return null;
		}

		apiCompanyCustomer.setContact(companyCustomer.getContact());
		apiCompanyCustomer.setLocation(toApiGeoAddress(companyCustomer.getLocation()));
		apiCompanyCustomer.setOfficialCompanyName(companyCustomer.getOfficialCompanyName());
		apiCompanyCustomer.setPhone(companyCustomer.getPhone());
		apiCompanyCustomer.setVatId(companyCustomer.getVatId());

		return apiCompanyCustomer;
	}

	private static ApiGeoAddress toApiGeoAddress(GeoAddress geoAddress) {

		if(geoAddress == null) return null;

		ApiGeoAddress apiGeoAddress = new ApiGeoAddress();
		apiGeoAddress.setAddress(geoAddress.getAddress());
		apiGeoAddress.setCell(geoAddress.getCell());
		apiGeoAddress.setCity(geoAddress.getCity());
		apiGeoAddress.setCountry(CountryMapper.toApiCountry(geoAddress.getCountry()));
		apiGeoAddress.setHondurasDepartment(geoAddress.getHondurasDepartment());
		apiGeoAddress.setHondurasFarm(geoAddress.getHondurasFarm());
		apiGeoAddress.setHondurasMunicipality(geoAddress.getHondurasMunicipality());
		apiGeoAddress.setHondurasVillage(geoAddress.getHondurasVillage());
		apiGeoAddress.setLatitude(geoAddress.getLatitude());
		apiGeoAddress.setLongitude(geoAddress.getLongitude());
		apiGeoAddress.setSector(geoAddress.getSector());
		apiGeoAddress.setState(geoAddress.getState());
		apiGeoAddress.setVillage(geoAddress.getVillage());
		apiGeoAddress.setOtherAddress(geoAddress.getOtherAddress());
		apiGeoAddress.setZip(geoAddress.getZip());

		return apiGeoAddress;
	}
}
