package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for processing action entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/processing-action")
public class ProcessingActionController {

	private final ProcessingActionService processingActionService;

	@Autowired
	public ProcessingActionController(ProcessingActionService processingActionService) {
		this.processingActionService = processingActionService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of processing actions.")
	public ApiPaginatedResponse<ApiProcessingAction> getProcessingActionList(@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(processingActionService.getProcessingActionList(request));
	}
	
	@GetMapping("list/company/{id}")
	@ApiOperation("Get a list of processing actions by company ID.")
	public ApiPaginatedResponse<ApiProcessingAction> listProcessingActionsByCompany(
		@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long companyId, 
		@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(processingActionService.listProcessingActionsByCompany(companyId, request));
	}
	
	@GetMapping("{id}")
	@ApiOperation("Get a single processing action with the provided ID.")
	public ApiResponse<ApiProcessingAction> getProcessingAction(@Valid @ApiParam(value = "ProcessingAction ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(processingActionService.getProcessingAction(id));
	}

	@PutMapping
	@ApiOperation("Create or update processing action. If ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateProcessingAction(@Valid @RequestBody ApiProcessingAction apiProcessingAction) throws ApiException {

		return new ApiResponse<>(processingActionService.createOrUpdateProcessingAction(apiProcessingAction));
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a processing action with the provided ID.")
	public ApiDefaultResponse deleteProcessingAction(@Valid @ApiParam(value = "ProcessingAction ID", required = true) @PathVariable("id") Long id) throws ApiException {

		processingActionService.deleteProcessingAction(id);
		return new ApiDefaultResponse();
	}
}
