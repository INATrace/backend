package com.abelium.inatrace.components.value_chain;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.user.UserService;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.components.value_chain.api.ApiValueChainListRequest;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import com.abelium.inatrace.db.entities.value_chain.enums.ValueChainStatus;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;

/**
 * Service for value chain entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class ValueChainService extends BaseService {

	private final UserService userService;

	@Autowired
	public ValueChainService(UserService userService) {
		this.userService = userService;
	}

	public ApiPaginatedList<ApiValueChain> getValueChainList(ApiValueChainListRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> valueChainQueryObject(request),
				ValueChainMapper::toApiValueChainBase);
	}

	private ValueChain valueChainQueryObject(ApiValueChainListRequest request) {

		ValueChain valueChainProxy = Torpedo.from(ValueChain.class);

		OnGoingLogicalCondition condition = Torpedo.condition();

		if (StringUtils.isNotBlank(request.getName())) {
			condition = condition.and(valueChainProxy.getName()).like().any(request.getName());
		}
		if (request.getValueChainStatus() != null) {
			condition = condition.and(valueChainProxy.getValueChainStatus()).eq(request.getValueChainStatus());
		}

		Torpedo.where(condition);

		switch (request.sortBy) {
			case "name":
				QueryTools.orderBy(request.sort, valueChainProxy.getName());
				break;
			case "description":
				QueryTools.orderBy(request.sort, valueChainProxy.getDescription());
				break;
			default:
				QueryTools.orderBy(request.sort, valueChainProxy.getId());
		}

		return valueChainProxy;
	}

	public ApiValueChain getValueChain(Long id) throws ApiException {

		ValueChain valueChain = fetchValueChain(id);

		return ValueChainMapper.toApiValueChain(valueChain);
	}

	@Transactional
	public ApiBaseEntity createOrUpdateValueChain(Long userId, ApiValueChain apiValueChain) throws ApiException {

		User user = userService.fetchUserById(userId);
		ValueChain entity;

		if (apiValueChain.getId() != null) {
			entity = fetchValueChain(apiValueChain.getId());
			entity.setUpdatedBy(user);
		} else {
			entity = new ValueChain();
			entity.setCreatedBy(user);
			entity.setValueChainStatus(ValueChainStatus.ENABLED);
		}

		entity.setName(apiValueChain.getName());
		entity.setDescription(apiValueChain.getDescription());

		// TODO: update value chain connected entities

		if (entity.getId() == null) {
			em.persist(entity);
		}

		return new ApiBaseEntity(entity);
	}

	@Transactional
	public ApiBaseEntity enableValueChain(Long id) throws ApiException {

		ValueChain valueChain = fetchValueChain(id);
		valueChain.setValueChainStatus(ValueChainStatus.ENABLED);

		return new ApiBaseEntity(valueChain);
	}

	@Transactional
	public ApiBaseEntity disableValueChain(Long id) throws ApiException {

		ValueChain valueChain = fetchValueChain(id);
		valueChain.setValueChainStatus(ValueChainStatus.DISABLED);

		return new ApiBaseEntity(valueChain);
	}

	@Transactional
	public void deleteValueChain(Long id) throws ApiException {

		ValueChain valueChain = fetchValueChain(id);
		em.remove(valueChain);
	}

	private ValueChain fetchValueChain(Long id) throws ApiException {

		ValueChain valueChain = Queries.get(em, ValueChain.class, id);
		if (valueChain == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid value chain ID");
		}

		return valueChain;
	}

}
