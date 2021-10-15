package com.abelium.inatrace.components.codebook.action_type;

import com.abelium.inatrace.components.codebook.action_type.api.ApiActionType;
import com.abelium.inatrace.db.entities.codebook.ActionType;

/**
 * Action type entity mapper helper class.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class ActionTypeMapper {

	private ActionTypeMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiActionType toApiActionType(ActionType entity) {
		if (entity == null) return null;

		ApiActionType apiActionType = new ApiActionType();
		apiActionType.setId(entity.getId());
		apiActionType.setCode(entity.getCode());
		apiActionType.setLabel(entity.getLabel());

		return apiActionType;
	}

}
