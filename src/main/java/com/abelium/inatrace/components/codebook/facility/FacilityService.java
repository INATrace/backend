package com.abelium.inatrace.components.codebook.facility;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.facility.api.ApiFacility;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import com.abelium.inatrace.db.entities.common.Address;
import com.abelium.inatrace.db.entities.common.Country;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.facility.FacilityLocation;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

/**
 * Service for facility entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Lazy
@Service
public class FacilityService extends BaseService {

	public ApiPaginatedList<ApiFacility> getFacilityList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> facilityQueryObject(request), FacilityMapper::toApiFacility);

	}

	private Facility facilityQueryObject(ApiPaginatedRequest request) {

		Facility facilityProxy = Torpedo.from(Facility.class);
		switch (request.sortBy) {
			case "name":
				QueryTools.orderBy(request.sort, facilityProxy.getName());
				break;
			default:
				QueryTools.orderBy(request.sort, facilityProxy.getId());
				break;
		}

		return facilityProxy;
	}

	public ApiFacility getFacility(Long id) throws ApiException {
		return FacilityMapper.toApiFacility(fetchFacility(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdateFacility(ApiFacility apiFacility) throws ApiException {

		Facility entity;
		if (apiFacility.getId() != null) {

			entity = fetchFacility(apiFacility.getId());

		} else {

			entity = new Facility();

			entity.setName(apiFacility.getName());
			entity.setIsCollectionFacility(apiFacility.getIsCollectionFacility());
			entity.setIsPublic(apiFacility.getIsPublic());

			FacilityLocation facilityLocation = new FacilityLocation();
			facilityLocation.setLatitude(apiFacility.getFacilityLocation().getLatitude());
			facilityLocation.setLongitude(apiFacility.getFacilityLocation().getLongitude());
			facilityLocation.setNumberOfFarmers(apiFacility.getFacilityLocation().getNumberOfFarmers());
			facilityLocation.setPinName(apiFacility.getFacilityLocation().getPinName());

			Address address = new Address();
			address.setAddress(apiFacility.getFacilityLocation().getAddress().getAddress());
			address.setCity(apiFacility.getFacilityLocation().getAddress().getCity());
			address.setState(apiFacility.getFacilityLocation().getAddress().getState());
			address.setZip(apiFacility.getFacilityLocation().getAddress().getZip());

			Country country = new Country();
			country.setCode(apiFacility.getFacilityLocation().getAddress().getCountry().getCode());
			country.setName(apiFacility.getFacilityLocation().getAddress().getCountry().getName());

			address.setCountry(country);
			facilityLocation.setAddress(address);

			FacilityType facilityType = new FacilityType();
			facilityType.setCode(apiFacility.getFacilityType().getCode());
			facilityType.setLabel(apiFacility.getFacilityType().getLabel());

			entity.setFacilityLocation(facilityLocation);
			entity.setFacilityType(facilityType);

		}

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deleteFacility(Long id) throws ApiException {

		Facility facility = fetchFacility(id);
		em.remove(facility);

	}

	public Facility fetchFacility(Long id) throws ApiException {

		Facility facility = Queries.get(em, Facility.class, id);
		if (facility == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid facility ID");
		}

		return facility;

	}
	
	public List<ApiFacility> listFacilitiesByCompany(Long companyId) {

		List<Facility> facilities = 
			em.createNamedQuery("Facility.listFacilitiesByCompany", Facility.class)
				.setParameter("companyId", companyId)
				.getResultList();

		return facilities.stream().map(f -> FacilityMapper.toApiFacility(f)).collect(Collectors.toList());

	}
	
	public List<ApiFacility> listCollectingFacilitiesByCompany(Long companyId) {

		List<Facility> facilities = 
			em.createNamedQuery("Facility.listCollectingFacilitiesByCompany", Facility.class)
				.setParameter("companyId", companyId)
				.getResultList();

		return facilities.stream().map(f -> FacilityMapper.toApiFacility(f)).collect(Collectors.toList());

	}

}
