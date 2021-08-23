package com.abelium.inatrace.components.codebook.facility;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.facility.api.ApiFacility;

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
 * REST controller for facility entity.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@RestController
@RequestMapping("/chain/facility")
public class FacilityController {

  @Autowired
  private FacilityService facilityService;

  @GetMapping("list")
  @ApiOperation("Get a paginated list of facilities.")
  public ApiPaginatedResponse<ApiFacility> getFacilityList(@Valid ApiPaginatedRequest request) {

    return new ApiPaginatedResponse<>(facilityService.getFacilityList(request));

  }

  @GetMapping("{id}")
  @ApiOperation("Get a single facility with the provided ID.")
  public ApiResponse<ApiFacility> getFacility(
    @Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("id") Long id) throws ApiException {

    return new ApiResponse<>(facilityService.getFacility(id));
    
  }

  @PutMapping
  @ApiOperation("Create or update facility. If ID is provided, then the entity with the provided ID is updated.")
  public ApiResponse<ApiBaseEntity> createOrUpdateFacilityType(
    @Valid @RequestBody ApiFacility apiFacility) throws ApiException {

    return new ApiResponse<>(facilityService.createOrUpdateFacility(apiFacility));

  }

  @DeleteMapping("{id}")
  @ApiOperation("Deletes a facility with the provided ID.")
  public ApiDefaultResponse deleteFacilityType(
    @Valid @ApiParam(value = "Facility ID", required = true) @PathVariable("id") Long id) throws ApiException {

    facilityService.deleteFacility(id);
    return new ApiDefaultResponse();

  }
}
