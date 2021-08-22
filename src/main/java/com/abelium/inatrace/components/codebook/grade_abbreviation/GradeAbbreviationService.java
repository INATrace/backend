package com.abelium.inatrace.components.codebook.grade_abbreviation;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.grade_abbreviation.api.ApiGradeAbbreviation;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.GradeAbbreviationType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;

/**
 * Service for grade abbreviation entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class GradeAbbreviationService extends BaseService {

	public ApiPaginatedList<ApiGradeAbbreviation> getGradeAbbreviationList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> gradeAbbreviationQueryObject(request),
				GradeAbbreviationMapper::toApiGradeAbbreviation);
	}

	private GradeAbbreviationType gradeAbbreviationQueryObject(ApiPaginatedRequest request) {

		GradeAbbreviationType gradeAbbreviationType = Torpedo.from(GradeAbbreviationType.class);

		switch (request.sortBy) {
			case "code":
				QueryTools.orderBy(request.sort, gradeAbbreviationType.getCode());
				break;
			case "label":
				QueryTools.orderBy(request.sort, gradeAbbreviationType.getLabel());
				break;
			default:
				QueryTools.orderBy(request.sort, gradeAbbreviationType.getId());
		}

		return gradeAbbreviationType;
	}

	public ApiGradeAbbreviation getGradeAbbreviation(Long id) throws ApiException {

		return GradeAbbreviationMapper.toApiGradeAbbreviation(fetchGradeAbbreviationType(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdateGradeAbbreviation(ApiGradeAbbreviation apiGradeAbbreviation) throws ApiException {

		GradeAbbreviationType entity;

		if (apiGradeAbbreviation.getId() != null) {
			entity = fetchGradeAbbreviationType(apiGradeAbbreviation.getId());
		} else {

			entity = new GradeAbbreviationType();
			entity.setCode(apiGradeAbbreviation.getCode());
		}
		entity.setLabel(apiGradeAbbreviation.getLabel());

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deleteGradeAbbreviation(Long id) throws ApiException {

		GradeAbbreviationType gradeAbbreviationType = fetchGradeAbbreviationType(id);
		em.remove(gradeAbbreviationType);
	}

	public GradeAbbreviationType fetchGradeAbbreviationType(Long id) throws ApiException {

		GradeAbbreviationType gradeAbbreviationType = Queries.get(em, GradeAbbreviationType.class, id);
		if (gradeAbbreviationType == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid grade abbreviation type ID");
		}

		return gradeAbbreviationType;
	}
}
