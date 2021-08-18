package com.abelium.inatrace.components.codebook.grade_abbreviation;

import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.components.codebook.grade_abbreviation.api.ApiGradeAbbreviation;
import com.abelium.inatrace.components.codebook.grade_abbreviation.api.GradeAbbreviationMapper;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.GradeAbbreviationType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

/**
 * Service for grade abbreviation entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class GradeAbbreviationService extends BaseService {

	public ApiPaginatedList<ApiGradeAbbreviation> getGradeAbbreviationList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> gradeAbrvQueryObject(request),
				GradeAbbreviationMapper::toApiGradeAbbreviation);
	}

	private GradeAbbreviationType gradeAbrvQueryObject(ApiPaginatedRequest request) {

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
}
