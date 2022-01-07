package com.abelium.inatrace.components.common.mappers;

import com.abelium.inatrace.components.common.CommonApiTools;
import com.abelium.inatrace.components.common.api.ApiActivityProof;
import com.abelium.inatrace.db.entities.common.ActivityProof;

/**
 * Mapper for ActivityProof entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class ActivityProofMapper {

	private ActivityProofMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiActivityProof toApiActivityProof(ActivityProof entity, Long userId) {

		if (entity == null) {
			return null;
		}

		ApiActivityProof apiActivityProof = new ApiActivityProof();
		apiActivityProof.setId(entity.getId());
		apiActivityProof.setType(entity.getType());
		apiActivityProof.setValidUntil(entity.getValidUntil());
		apiActivityProof.setFormalCreationDate(entity.getFormalCreationDate());
		apiActivityProof.setDocument(CommonApiTools.toApiDocument(entity.getDocument(), userId));

		return apiActivityProof;
	}
}
