package com.abelium.inatrace.components.codebook.processing_evidence_type;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.processing_evidence_type.api.ApiProcessingEvidenceType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for processing evidence type entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/processing-evidence-type")
public class ProcessingEvidenceTypeController {

	private final ProcessingEvidenceTypeService processingEvidenceTypeService;

	@Autowired
	public ProcessingEvidenceTypeController(ProcessingEvidenceTypeService processingEvidenceTypeService) {
		this.processingEvidenceTypeService = processingEvidenceTypeService;
	}

	@GetMapping("list")
	@ApiOperation("Get a paginated list of processing evidence types.")
	public ApiPaginatedResponse<ApiProcessingEvidenceType> getProcessingEvidenceTypeList(@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(processingEvidenceTypeService.getProcEvidenceTypeList(request));
	}

	@GetMapping("list/value-chain/{id}")
	@ApiOperation("Get a list of processing evidence types by value chain ID.")
	public ApiPaginatedResponse<ApiProcessingEvidenceType> listProcessingEvidenceTypesByValueChain(
			@Valid @ApiParam(value = "Value chain ID", required = true) @PathVariable("id") Long valueChainId,
			@Valid ApiPaginatedRequest request) {

		return new ApiPaginatedResponse<>(processingEvidenceTypeService.listProcessingEvidenceTypesByValueChain(valueChainId, request));
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single processing evidence type with the provided ID.")
	public ApiResponse<ApiProcessingEvidenceType> getProcessingEvidenceType(
			@Valid @ApiParam(value = "Processing evidence type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(processingEvidenceTypeService.getProcessingEvidenceType(id));
	}

	@PutMapping
	@ApiOperation("Create or update processing evidence type. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateProcessingEvidenceType(
			@Valid @RequestBody ApiProcessingEvidenceType apiProcessingEvidenceType) throws ApiException {

		return new ApiResponse<>(
				processingEvidenceTypeService.createOrUpdateProcessingEvidenceType(apiProcessingEvidenceType));
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a processing evidence type with the provided ID.")
	public ApiDefaultResponse deleteProcessingEvidenceType(
			@Valid @ApiParam(value = "Processing evidence type ID", required = true) @PathVariable("id") Long id) throws ApiException {

		processingEvidenceTypeService.deleteProcessingEvidenceType(id);
		return new ApiDefaultResponse();
	}

}
