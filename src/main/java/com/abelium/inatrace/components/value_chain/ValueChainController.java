package com.abelium.inatrace.components.value_chain;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.components.value_chain.api.ApiValueChainListRequest;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for value chain entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/value-chain")
public class ValueChainController {

	private final ValueChainService valueChainService;

	@Autowired
	public ValueChainController(ValueChainService valueChainService) {
		this.valueChainService = valueChainService;
	}

	@GetMapping("list")
	@Operation(summary ="Get a list of value chains defined in the system.")
	public ApiPaginatedResponse<ApiValueChain> getValueChainList(@Valid ApiValueChainListRequest request) {

		return new ApiPaginatedResponse<>(valueChainService.getValueChainList(request));
	}

	@GetMapping("{id}")
	@Operation(summary ="Get a single value chain with the provided ID.")
	public ApiResponse<ApiValueChain> getValueChain(
			@Valid @Parameter(description = "Value chain ID", required = true) @PathVariable("id") Long id,
			@RequestHeader(value = "language", defaultValue = "EN", required = false) Language language) throws ApiException {

		return new ApiResponse<>(valueChainService.getValueChain(id, language));
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN', 'REGIONAL_ADMIN')")
	@Operation(summary ="Create or update value chain. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateValueChain(
			@AuthenticationPrincipal CustomUserDetails authUser,
			@Valid @RequestBody ApiValueChain apiValueChain) throws ApiException {

		return new ApiResponse<>(valueChainService.createOrUpdateValueChain(authUser, apiValueChain));
	}

	@PostMapping("{id}/enable")
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	@Operation(summary ="Set the status of the value chain with the provided ID as 'ENABLED'.")
	public ApiResponse<ApiBaseEntity> enableValueChain(
			@Valid @Parameter(description = "Value chain ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(valueChainService.enableValueChain(id));
	}

	@PostMapping("{id}/disable")
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	@Operation(summary ="Set the status of the value chain with the provided ID as 'DISABLED'.")
	public ApiResponse<ApiBaseEntity> disableValueChain(
			@Valid @Parameter(description = "Value chain ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(valueChainService.disableValueChain(id));
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	@Operation(summary ="Deletes a value chain with the provided ID.")
	public ApiDefaultResponse deleteValueChain(
			@Valid @Parameter(description = "Value chain ID", required = true) @PathVariable("id") Long id) throws ApiException {

		valueChainService.deleteValueChain(id);
		return new ApiDefaultResponse();
	}

}
