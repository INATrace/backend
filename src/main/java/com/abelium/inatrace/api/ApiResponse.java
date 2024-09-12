package com.abelium.inatrace.api;

import com.abelium.inatrace.api.errors.validation.ApiValidationErrorDetails;
import com.abelium.inatrace.api.exceptions.ApiUpstreamApplicationException;
import com.abelium.inatrace.api.exceptions.ApiUpstreamServiceException;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Validated
@Schema(description = "Generic API response. See documentation for data type for specific type details.")
public class ApiResponse<DATATYPE>
{

    /**
     * Response status. OK for successful reponses.
     */
    @NotNull
    @JsonProperty(required = true)
    @Schema(description = "Response status. OK for successful reponses.", example = "OK", requiredMode = Schema.RequiredMode.REQUIRED)
    private ApiStatus status;
    
    /**
     * Simple message to explain client developers the reason for error.
     * If status is OK, this field should be null. 
     */
    @Schema(description = "Simple message to explain client developers the reason for error.")
    private String errorMessage;
    
    /**
     * Response body for successful responses.
     */
    @Schema(description = "Response body for successful responses.")
    private DATATYPE data;

    /**
     * Optional details for error responses.
     */
    @Schema(description = "Optional details for unexpected error responses.")
    private String errorDetails;
    
    /**
     * Optional details for validation error responses.
     */
    @Schema(description = "Optional details for validation error responses.")
    private ApiValidationErrorDetails validationErrorDetails;

    /**
     * Only for deserialization and inheritance.
     */
    protected ApiResponse() {
        this.status = ApiStatus.OK;
    }
    
    /**
     * Successful response. Status will be OK.
     * @param data response data
     */
    public ApiResponse(DATATYPE data) {
        this.status = ApiStatus.OK;
        this.data = data;
    }
    
    /**
     * Error response.
     * @param status the error status code (anything except OK)
     * @param errorMessage short error message for client developers (may be null)
     */
    public ApiResponse(ApiStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    /**
     * Error response.
     * @param status the error status code (anything except OK)
     * @param errorMessage short error message for client developers (may be null)
     * @param validationErrorDetails object with details about validation errors
     * @param errorDetails object with details about errors
     */
    public ApiResponse(ApiStatus status, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.validationErrorDetails = validationErrorDetails;
        this.errorDetails = errorDetails;
    }
    
    public static ApiDefaultResponse ok() {
        return new ApiDefaultResponse();
    }
    
    public static <DATATYPE> ApiResponse<DATATYPE> ok(DATATYPE data) {
        return new ApiResponse<>(data);
    }
    
    /**
     * For use in {@link RestTemplate} or in {@link AbServiceRequest} for type parameters, because you cannot say
     * <code>ApiResponse&lt;DataType>.class</code>, use instead {@code ApiResponse.wrap(DataType.class)}.
     * @param <DataType>  
     * @param dataTypeClass DataType.class
     * @return ParameterizedTypeReference for use in {@link RestTemplate} or in {@link AbServiceRequest} get/post/... methods. 
     */
    public static <DATATYPE> ParameterizedTypeReference<ApiResponse<DATATYPE>> wrap(Class<DATATYPE> dataTypeClass) {
        Type fullType = TypeUtils.parameterize(ApiResponse.class, dataTypeClass);
        return ParameterizedTypeReference.forType(fullType);
    }

    /**
     * For use in {@link RestTemplate} or in {@link AbServiceRequest} for type parameters, because you cannot say
     * <code>ApiResponse&lt;List&lt;DataType>>.class</code>, use instead {@code ApiResponse.wrapList(DataType.class)}.
     * @param <DataType>  
     * @param dataTypeClass DataType.class
     * @return ParameterizedTypeReference for use in {@link RestTemplate} or in {@link AbServiceRequest} get/post/... methods. 
     */
    public static <DATATYPE> ParameterizedTypeReference<ApiResponse<List<DATATYPE>>> wrapList(Class<DATATYPE> dataTypeClass) {
        Type listType = TypeUtils.parameterize(List.class, dataTypeClass);
        Type fullType = TypeUtils.parameterize(ApiResponse.class, listType);
        return ParameterizedTypeReference.forType(fullType);
    }

    /**
     * For use in {@link RestTemplate} or in {@link AbServiceRequest} for type parameters, because you cannot say
     * <code>ApiResponse&lt;Map&lt;String, DataType>>.class</code>, use instead {@code ApiResponse.wrapMap(DataType.class)}.
     * @param <DataType>  
     * @param dataTypeClass DataType.class
     * @return ParameterizedTypeReference for use in {@link RestTemplate} or in {@link AbServiceRequest} get/post/... methods. 
     */
    public static <DATATYPE> ParameterizedTypeReference<ApiResponse<Map<String, DATATYPE>>> wrapMap(Class<DATATYPE> dataTypeClass) {
        Type mapType = TypeUtils.parameterize(Map.class, String.class, dataTypeClass);
        Type fullType = TypeUtils.parameterize(ApiResponse.class, mapType);
        return ParameterizedTypeReference.forType(fullType);
    }
    
    /**
     * Call on response returned from an upstream service to check that status is ok.
     * @throws ApiUpstreamServiceException if response status is not {@link ApiStatus#OK}
     */
    public void checkResponse() throws ApiUpstreamServiceException {
        if (status != ApiStatus.OK) {
            throw new ApiUpstreamApplicationException(status, errorMessage, validationErrorDetails, errorDetails);
        }
    }
    
    /**
     * Call on response returned from an upstream service to check that status is ok and obtain response data.
     * @return the data contained in this response
     * @throws ApiUpstreamServiceException if response status is not {@link ApiStatus#OK}
     */
    public DATATYPE checkGetData() throws ApiUpstreamServiceException {
        checkResponse();
        return data;
    }

    // automatic getters and setters
    
    public ApiStatus getStatus() {
        return status;
    }

    public void setStatus(ApiStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public DATATYPE getData() {
        return data;
    }

    public void setData(DATATYPE data) {
        this.data = data;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public ApiValidationErrorDetails getValidationErrorDetails() {
        return validationErrorDetails;
    }

    public void setValidationErrorDetails(ApiValidationErrorDetails validationErrorDetails) {
        this.validationErrorDetails = validationErrorDetails;
    }
}
