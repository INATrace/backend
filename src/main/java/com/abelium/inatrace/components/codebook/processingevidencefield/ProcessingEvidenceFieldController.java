package com.abelium.inatrace.components.codebook.processingevidencefield;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.processingevidencefield.api.ApiProcessingEvidenceField;
import com.abelium.inatrace.types.Language;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	
	@GetMapping("list/value-chain/{id}")
	@ApiOperation("Get a list of processing evidence fields by value chain ID.")
	public ApiPaginatedResponse<ApiProcessingEvidenceField> listProcessingEvidenceFieldsByValueChain(
		@Valid @ApiParam(value = "Value chain ID", required = true) @PathVariable("id") Long valueChainId, 
		@Valid ApiPaginatedRequest request,
		@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) {

		return new ApiPaginatedResponse<>(processingEvidenceFieldService.listProcessingEvidenceFieldsByValueChain(valueChainId, request, language));
	}
	
	@GetMapping("{id}")
	@ApiOperation("Get a single processing evidence field with the provided ID.")
	public ApiResponse<ApiProcessingEvidenceField> getProcessingEvidenceField(
			@Valid @ApiParam(value = "ProcessingEvidenceField ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(processingEvidenceFieldService.getProcessingEvidenceField(id, language));
	}

	@PutMapping
	@ApiOperation("Create or update processing evidence field. If ID is provided, then the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateProcessingEvidenceField(@Valid @RequestBody ApiProcessingEvidenceField apiProcessingEvidenceField) throws ApiException {

		return new ApiResponse<>(processingEvidenceFieldService.createOrUpdateProcessingEvidenceField(apiProcessingEvidenceField));
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a processing evidence field with the provided ID.")
	public ApiDefaultResponse deleteProcessingEvidenceField(@Valid @ApiParam(value = "ProcessingEvidenceField ID", required = true) @PathVariable("id") Long id) throws ApiException {

		processingEvidenceFieldService.deleteProcessingEvidenceField(id);
		return new ApiDefaultResponse();
	}
}
