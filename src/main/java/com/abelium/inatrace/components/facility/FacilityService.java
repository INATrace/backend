package com.abelium.inatrace.components.facility;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductService;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.facility.api.ApiFacility;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.Address;
import com.abelium.inatrace.db.entities.common.Country;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.facility.FacilityLocation;
import com.abelium.inatrace.db.entities.facility.FacilitySemiProduct;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for facility entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Lazy
@Service
public class FacilityService extends BaseService {

	@Autowired
	private SemiProductService semiProductService;

	public ApiPaginatedList<ApiFacility> getFacilityList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> facilityQueryObject(request), FacilityMapper::toApiFacility);

	}

	private Facility facilityQueryObject(ApiPaginatedRequest request) {

		Facility facilityProxy = Torpedo.from(Facility.class);
		if ("name".equals(request.sortBy)) {
			QueryTools.orderBy(request.sort, facilityProxy.getName());
		} else {
			QueryTools.orderBy(request.sort, facilityProxy.getId());
		}

		return facilityProxy;
	}

	public ApiFacility getFacility(Long id) throws ApiException {
		return FacilityMapper.toApiFacility(fetchFacility(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdateFacility(ApiFacility apiFacility) throws ApiException {

		Facility entity;
		FacilityLocation facilityLocation;
		Address address;
		Company company;

		if (apiFacility.getId() != null) {
			entity = fetchFacility(apiFacility.getId());
			facilityLocation = entity.getFacilityLocation();
			address = entity.getFacilityLocation().getAddress();
			company = entity.getCompany();
		} else {
			entity = new Facility();
			facilityLocation = new FacilityLocation();
			address = new Address();
			company = (Company) em.createNamedQuery("Company.getCompanyById").setParameter("companyId", apiFacility.getCompany().getId()).getSingleResult();
		}

		entity.setName(apiFacility.getName());
		entity.setIsCollectionFacility(apiFacility.getIsCollectionFacility());
		entity.setIsPublic(apiFacility.getIsPublic());

		facilityLocation.setLatitude(apiFacility.getFacilityLocation().getLatitude());
		facilityLocation.setLongitude(apiFacility.getFacilityLocation().getLongitude());
		facilityLocation.setNumberOfFarmers(apiFacility.getFacilityLocation().getNumberOfFarmers());
		facilityLocation.setPinName(apiFacility.getFacilityLocation().getPinName());
		facilityLocation.setPubliclyVisible(apiFacility.getFacilityLocation().getPubliclyVisible());

		address.setAddress(apiFacility.getFacilityLocation().getAddress().getAddress());
		address.setCity(apiFacility.getFacilityLocation().getAddress().getCity());
		address.setState(apiFacility.getFacilityLocation().getAddress().getState());
		address.setZip(apiFacility.getFacilityLocation().getAddress().getZip());
		address.setCell(apiFacility.getFacilityLocation().getAddress().getCell());
		address.setSector(apiFacility.getFacilityLocation().getAddress().getSector());
		address.setVillage(apiFacility.getFacilityLocation().getAddress().getVillage());

		Country country = Queries.get(em, Country.class, apiFacility.getFacilityLocation().getAddress().getCountry().getId());
		address.setCountry(country);
		facilityLocation.setAddress(address);

		FacilityType facilityType = Queries.get(em, FacilityType.class, apiFacility.getFacilityType().getId());
		entity.setFacilityType(facilityType);
		
		entity.setFacilityLocation(facilityLocation);

		entity.setCompany(company);

		if (entity.getId() == null) {
			em.persist(entity);
		}

		entity.getFacilitySemiProducts().removeIf(facilitySemiProduct -> apiFacility.getFacilitySemiProductList().stream().noneMatch(apiFacilitySemiProduct -> apiFacilitySemiProduct.getId().equals(facilitySemiProduct.getId())));

		for (ApiSemiProduct apiSemiProduct : apiFacility.getFacilitySemiProductList()) {
			FacilitySemiProduct facilitySemiProduct = new FacilitySemiProduct();
			SemiProduct semiProduct = semiProductService.fetchSemiProduct(apiSemiProduct.getId());
			facilitySemiProduct.setFacility(entity);
			facilitySemiProduct.setSemiProduct(semiProduct);
			entity.getFacilitySemiProducts().add(facilitySemiProduct);
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
	
	public ApiPaginatedList<ApiFacility> listFacilitiesByCompany(Long companyId, ApiPaginatedRequest request) {

		TypedQuery<Facility> facilitiesQuery = em.createNamedQuery("Facility.listFacilitiesByCompany", Facility.class)
				.setParameter("companyId", companyId)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<Facility> facilities = facilitiesQuery.getResultList();

		Long count = em.createNamedQuery("Facility.countFacilitiesByCompany", Long.class)
				.setParameter("companyId", companyId).getSingleResult();

		return new ApiPaginatedList<>(
				facilities.stream().map(FacilityMapper::toApiFacility).collect(Collectors.toList()), count);
	}
	
	public ApiPaginatedList<ApiFacility> listCollectingFacilitiesByCompany(Long companyId, ApiPaginatedRequest request) {

		TypedQuery<Facility> collectingFacilitiesQuery = em.createNamedQuery("Facility.listCollectingFacilitiesByCompany",
						Facility.class)
				.setParameter("companyId", companyId)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit());

		List<Facility> facilities = collectingFacilitiesQuery.getResultList();

		Long count = em.createNamedQuery("Facility.countCollectingFacilitiesByCompany", Long.class)
				.setParameter("companyId", companyId).getSingleResult();

		return new ApiPaginatedList<>(
				facilities.stream().map(FacilityMapper::toApiFacility).collect(Collectors.toList()), count);
	}

}
