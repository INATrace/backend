package com.abelium.inatrace.components.processingaction;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.ProcessingActionType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	
	@GetMapping("list/company/{id}")
	@ApiOperation("Get a list of processing actions by company ID.")
	public ApiPaginatedResponse<ApiProcessingAction> listProcessingActionsByCompany(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @ApiParam(value = "Company ID", required = true) @PathVariable("id") Long companyId,
			@Valid @ApiParam(value = "Processing action type") @RequestParam(value = "actionType", required = false) ProcessingActionType actionType,
			@Valid @ApiParam(value = "Only final product actions") @RequestParam(value = "onlyFinalProducts", required = false) Boolean onlyFinalProducts,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language,
			@Valid ApiPaginatedRequest request) throws ApiException {

		return new ApiPaginatedResponse<>(
				processingActionService.listProcessingActionsByCompany(companyId, authUser, language, request, actionType,
						onlyFinalProducts));
	}
	
	@GetMapping("{id}")
	@ApiOperation("Get a single processing action with the provided ID.")
	public ApiResponse<ApiProcessingAction> getProcessingAction(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @ApiParam(value = "ProcessingAction ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(processingActionService.getProcessingAction(id, authUser, language));
	}

	@GetMapping("{id}/detail")
	@ApiOperation("Get a single processing action by the provided ID with all translations.")
	public ApiResponse<ApiProcessingAction> getProcessingActionDetail(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @ApiParam(value = "ProcessingAction ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {
		return new ApiResponse<>(processingActionService.getProcessingActionDetail(id, authUser, language));
	}

	@PutMapping
	@ApiOperation("Create or update processing action. If ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateProcessingAction(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @RequestBody ApiProcessingAction apiProcessingAction) throws ApiException {

		return new ApiResponse<>(processingActionService.createOrUpdateProcessingAction(apiProcessingAction, authUser));
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a processing action with the provided ID.")
	public ApiDefaultResponse deleteProcessingAction(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @ApiParam(value = "ProcessingAction ID", required = true) @PathVariable("id") Long id) throws ApiException {

		processingActionService.deleteProcessingAction(id, authUser);
		return new ApiDefaultResponse();
	}

}
