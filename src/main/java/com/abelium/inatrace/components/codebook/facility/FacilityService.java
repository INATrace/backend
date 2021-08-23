package com.abelium.inatrace.components.codebook.facility;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.facility.api.ApiFacility;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

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

    return PaginationTools
      .createPaginatedResponse(
        em, 
        request, 
        () -> facilityQueryObject(request),
        FacilityMapper::toApiFacility
      );

  }

  private Facility facilityQueryObject(ApiPaginatedRequest request) {

    Facility facilityProxy = Torpedo.from(Facility.class);

    // TODO: By which attributes are we gonna be able to sortBy?
    // I've set name and id for a facility
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
      // TODO: Are also facilities containing code and label?
      // Still not sure what those fields are
//      entity.setCode(apiFacility.getCode());
    }
//    entity.setLabel(apiFacilityType.getLabel());

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

}
