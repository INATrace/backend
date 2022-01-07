package com.abelium.inatrace.components.codebook.action_type;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.action_type.api.ApiActionType;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.ActionType;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;

/**
 * Service for action type entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class ActionTypeService extends BaseService {

	public ApiPaginatedList<ApiActionType> getActionTypeList(ApiPaginatedRequest request) {

		ActionType actionTypeProxy = Torpedo.from(ActionType.class);

		switch (request.sortBy) {
			case "code":
				QueryTools.orderBy(request.sort, actionTypeProxy.getCode());
				break;
			case "label":
				QueryTools.orderBy(request.sort, actionTypeProxy.getLabel());
				break;
			default:
				QueryTools.orderBy(request.sort, actionTypeProxy.getId());
		}

		return PaginationTools.createPaginatedResponse(em, request, () -> actionTypeProxy, ActionTypeMapper::toApiActionType);
	}

	public ApiActionType getActionType(Long id) throws ApiException {

		return ActionTypeMapper.toApiActionType(fetchActionType(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdateActionType(ApiActionType apiActionType) throws ApiException {

		ActionType entity;

		if (apiActionType.getId() != null) {
			entity = fetchActionType(apiActionType.getId());
		} else {

			entity = new ActionType();
			entity.setCode(apiActionType.getCode());
		}
		entity.setLabel(apiActionType.getLabel());

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public void deleteActionType(Long id) throws ApiException {

		ActionType actionType = fetchActionType(id);
		em.remove(actionType);
	}

	private ActionType fetchActionType(Long id) throws ApiException {

		ActionType actionType = Queries.get(em, ActionType.class, id);
		if (actionType == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid action type ID");
		}

		return actionType;
	}
}
