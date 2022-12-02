package com.abelium.inatrace.components.codebook.action_type;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.action_type.api.ApiActionType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST resource for action type entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/action-type")
public class ActionTypeController {

	private final ActionTypeService actionTypeService;

	@Autowired
	public ActionTypeController(ActionTypeService actionTypeService) {
		this.actionTypeService = actionTypeService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of action types.")
	public ApiPaginatedResponse<ApiActionType> getActionTypeList(@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(actionTypeService.getActionTypeList(request));
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single action type with the provided ID.")
	public ApiResponse<ApiActionType> getActionType(@Valid @ApiParam(value = "Record id", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(actionTypeService.getActionType(id));
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Create or update action type. If ID is provided the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateActionType(@Valid @RequestBody ApiActionType apiActionType) throws ApiException {

		return new ApiResponse<>(actionTypeService.createOrUpdateActionType(apiActionType));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Deletes an action type with the provided ID.")
	public ApiDefaultResponse deleteActionType(@Valid @ApiParam(value = "Action type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		actionTypeService.deleteActionType(id);
		return new ApiDefaultResponse();
	}

}
