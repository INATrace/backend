package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
	public ApiPaginatedResponse<ApiProcessingAction> getProcessingActionList(
			@Valid ApiPaginatedRequest request,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(processingActionService.listProcessingActions(request, language));
	}
	
	@GetMapping("list/company/{id}")
	@ApiOperation("Get a list of processing actions by company ID.")
	public ApiPaginatedResponse<ApiProcessingAction> listProcessingActionsByCompany(
		@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long companyId,
		@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
		@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(processingActionService.listProcessingActionsByCompany(companyId, language, request));
	}
	
	@GetMapping("{id}")
	@ApiOperation("Get a single processing action with the provided ID.")
	public ApiResponse<ApiProcessingAction> getProcessingAction(
			@Valid @ApiParam(value = "ProcessingAction ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(processingActionService.getProcessingAction(id, language));
	}

	@GetMapping("{id}/detail")
	@ApiOperation("Get a single processing action by the provided ID with all translations.")
	public ApiResponse<ApiProcessingAction> getProcessingActionDetail(
			@Valid @ApiParam(value = "ProcessingAction ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {
		return new ApiResponse<>(processingActionService.getProcessingActionDetail(id, language));
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
