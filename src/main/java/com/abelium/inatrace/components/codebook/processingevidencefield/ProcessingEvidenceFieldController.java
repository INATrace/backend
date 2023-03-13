package com.abelium.inatrace.components.codebook.processingevidencefield;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.types.Language;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for processing evidence field entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/processing-evidence-field")
public class ProcessingEvidenceFieldController {

	private final ProcessingEvidenceFieldService processingEvidenceFieldService;

	@Autowired
	public ProcessingEvidenceFieldController(ProcessingEvidenceFieldService processingEvidenceFieldService) {
		this.processingEvidenceFieldService = processingEvidenceFieldService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of processing evidence fields.")
	public ApiPaginatedResponse<ApiProcessingEvidenceField> getProcessingEvidenceFieldList(
			@Valid ApiPaginatedRequest request,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(processingEvidenceFieldService.getProcessingEvidenceFieldList(request, language));
	}

	@Deprecated
	@GetMapping("list/value-chain/{id}")
	@ApiOperation("Get a list of processing evidence fields by value chain ID.")
	public ApiPaginatedResponse<ApiProcessingEvidenceField> listProcessingEvidenceFieldsByValueChain(
		@Valid @ApiParam(value = "Value chain ID", required = true) @PathVariable("id") Long valueChainId, 
		@Valid ApiPaginatedRequest request,
		@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(processingEvidenceFieldService.listProcessingEvidenceFieldsByValueChain(valueChainId, request, language));
	}

	@GetMapping("list/by-value-chains")
	@ApiOperation("Get a list of processing evidence fields by value chain ID list.")
	public ApiPaginatedResponse<ApiProcessingEvidenceField> listProcessingEvidenceFieldsByValueChains(
			@ApiParam(value = "Value chain IDs", required = true) @RequestParam(value = "valueChainIds")
			List<Long> valueChainIds,
			@Valid ApiPaginatedRequest request,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(processingEvidenceFieldService.listProcessingEvidenceFieldsByValueChainList(valueChainIds, request, language));
	}
	
	@GetMapping("{id}")
	@ApiOperation("Get a single processing evidence field with the provided ID.")
	public ApiResponse<ApiProcessingEvidenceField> getProcessingEvidenceField(
			@Valid @ApiParam(value = "ProcessingEvidenceField ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(processingEvidenceFieldService.getProcessingEvidenceField(id, language));
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Create or update processing evidence field. If ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateProcessingEvidenceField(@Valid @RequestBody ApiProcessingEvidenceField apiProcessingEvidenceField) throws ApiException {

		return new ApiResponse<>(processingEvidenceFieldService.createOrUpdateProcessingEvidenceField(apiProcessingEvidenceField));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation("Deletes a processing evidence field with the provided ID.")
	public ApiDefaultResponse deleteProcessingEvidenceField(@Valid @ApiParam(value = "ProcessingEvidenceField ID", required = true) @PathVariable("id") Long id) throws ApiException {

		processingEvidenceFieldService.deleteProcessingEvidenceField(id);
		return new ApiDefaultResponse();
	}
}
