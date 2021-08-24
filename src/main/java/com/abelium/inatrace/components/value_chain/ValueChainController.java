package com.abelium.inatrace.components.value_chain;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.components.value_chain.api.ApiValueChainListRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
	@ApiOperation("Get a list of value chains defined in the system.")
	public ApiPaginatedResponse<ApiValueChain> getValueChainList(@Valid ApiValueChainListRequest request) {

		return new ApiPaginatedResponse<>(valueChainService.getValueChainList(request));
	}

	@GetMapping("{id}")
	@ApiOperation("Get a single value chain with the provided ID.")
	public ApiResponse<ApiValueChain> getValueChain(
			@Valid @ApiParam(value = "Value chain ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(valueChainService.getValueChain(id));
	}

	@PutMapping
	@ApiOperation("Create or update value chain. If ID is provided, the entity with the provided ID is updated.")
	public ApiResponse<ApiBaseEntity> createOrUpdateValueChain(@Valid @RequestBody ApiValueChain apiValueChain) {

		return new ApiResponse<>(valueChainService.createOrUpdateValueChain(apiValueChain));
	}

	@PostMapping("{id}/enable")
	@ApiOperation("Set the status of the value chain with the provided ID as 'ENABLED'.")
	public ApiResponse<ApiBaseEntity> enableValueChain(
			@Valid @ApiParam(value = "Value chain ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(valueChainService.enableValueChain(id));
	}

	@PostMapping("{id}/disable")
	@ApiOperation("Set the status of the value chain with the provided ID as 'DISABLED'.")
	public ApiResponse<ApiBaseEntity> disableValueChain(
			@Valid @ApiParam(value = "Value chain ID", required = true) @PathVariable("id") Long id) throws ApiException {

		return new ApiResponse<>(valueChainService.disableValueChain(id));
	}

	@DeleteMapping("{id}")
	@ApiOperation("Deletes a value chain with the provided ID.")
	public ApiDefaultResponse deleteValueChain(
			@Valid @ApiParam(value = "Value chain ID", required = true) @PathVariable("id") Long id) throws ApiException {

		valueChainService.deleteValueChain(id);
		return new ApiDefaultResponse();
	}

}