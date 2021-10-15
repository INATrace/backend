package com.abelium.inatrace.components.codebook.grade_abbreviation;

import com.abelium.inatrace.components.codebook.grade_abbreviation.api.ApiGradeAbbreviation;
import com.abelium.inatrace.db.entities.codebook.GradeAbbreviationType;

/**
 * Mapper for GradeAbbreviation entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class GradeAbbreviationMapper {

	private GradeAbbreviationMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiGradeAbbreviation toApiGradeAbbreviation(GradeAbbreviationType entity) {
		if(entity == null) return null;

		ApiGradeAbbreviation apiGradeAbbreviation = new ApiGradeAbbreviation();
		apiGradeAbbreviation.setId(entity.getId());
		apiGradeAbbreviation.setCode(entity.getCode());
		apiGradeAbbreviation.setLabel(entity.getLabel());

		return apiGradeAbbreviation;
	}
}
