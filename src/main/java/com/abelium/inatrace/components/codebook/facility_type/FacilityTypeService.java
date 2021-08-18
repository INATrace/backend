package com.abelium.inatrace.components.codebook.facility_type;

import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

/**
 * Service for facility type entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class FacilityTypeService extends BaseService {

	public ApiPaginatedList<ApiFacilityType> getFacilityTypeList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> facilityTypeQueryObject(request),
				FacilityTypeMapper::toApiFacilityType);
	}

	private FacilityType facilityTypeQueryObject(ApiPaginatedRequest request) {

		FacilityType facilityTypeProxy = Torpedo.from(FacilityType.class);

		switch (request.sortBy) {
			case "code":
				QueryTools.orderBy(request.sort, facilityTypeProxy.getCode());
				break;
			case "label":
				QueryTools.orderBy(request.sort, facilityTypeProxy.getLabel());
				break;
			default:
				QueryTools.orderBy(request.sort, facilityTypeProxy.getId());
		}

		return facilityTypeProxy;
	}

}
