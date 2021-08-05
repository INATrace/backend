package com.abelium.inatrace.components.pub;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abelium.inatrace.api.ApiDefaultResponse;
import com.abelium.inatrace.api.ApiPaginatedResponse;
import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.analytics.RequestLogEngine;
import com.abelium.inatrace.components.analytics.api.ApiLogRequest;
import com.abelium.inatrace.components.common.CommonEngine;
import com.abelium.inatrace.components.common.GlobalSettingsEngine;
import com.abelium.inatrace.components.common.api.ApiGlobalSettingsValue;
import com.abelium.inatrace.components.company.CompanyEngine;
import com.abelium.inatrace.components.company.api.ApiCompanyPublic;
import com.abelium.inatrace.components.product.ProductEngine;
import com.abelium.inatrace.components.product.api.ApiKnowledgeBlog;
import com.abelium.inatrace.components.product.api.ApiListProductLabelFeedbackRequest;
import com.abelium.inatrace.components.product.api.ApiLocation;
import com.abelium.inatrace.components.product.api.ApiProductLabelBatch;
import com.abelium.inatrace.components.product.api.ApiProductLabelBatchCheckAuthenticity;
import com.abelium.inatrace.components.product.api.ApiProductLabelBatchCheckOrigin;
import com.abelium.inatrace.components.product.api.ApiProductLabelFeedback;
import com.abelium.inatrace.components.product.api.ApiProductLabelValuesExtended;
import com.abelium.inatrace.types.Language;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private CompanyEngine companyEngine;
	
	@Autowired
	private ProductEngine productEngine;
	
	//	@Autowired
	//	private NotificationEngine notificationEngine;
	
    @Autowired
    private CommonEngine commonEngine;
    
	@Autowired
	private RequestLogEngine requestLogEngine;
	
    @Autowired
    private GlobalSettingsEngine globalSettingsEngine;
    

	//    @GetMapping(value = "/test")
	//    public String validatePublicEmail(@Valid @RequestParam(value = "name", required = true) String name,
	//    		@RequestParam(value = "surname", required = true) String surname) {
	//        return notificationEngine.createEmailConfirmationEmail(name, surname, "https://google.com");
	//    }
	//    
	//    @GetMapping(value = "/test2")
	//    public String validatePublicEmail2(@Valid @RequestParam(value = "name", required = true) String name,
	//    		@RequestParam(value = "surname", required = true) String surname) {
	//        return notificationEngine.createConfirmationEmail(name, surname);
	//    }

    @GetMapping(value = "/company/{id}")
    @ApiOperation(value = "Get public data about company")
    public ApiResponse<ApiCompanyPublic> getPublicCompany(
    		@Valid @ApiParam(value = "id", required = true) @PathVariable("id") Long id,
    		@Valid @ApiParam(value = "language", required = false) @RequestParam(value = "language", defaultValue = "EN") String language) throws ApiException {
    	return new ApiResponse<>(companyEngine.getCompanyPublic(id, Language.valueOf(language)));
    }
    
    @GetMapping(value = "/product/label/{uid}")
    @ApiOperation(value = "Get label with field values")
    public ApiResponse<ApiProductLabelValuesExtended> getPublicProductLabelValues(@Valid @ApiParam(value = "Label uid", required = true) @PathVariable("uid") String uid) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabelValuesPublic(uid));
    }
    
    @GetMapping(value = "/product/knowledgeBlog/{id}")
    @ApiOperation(value = "Get knowledge blog by id")
    public ApiResponse<ApiKnowledgeBlog> getPublicProductKnowledgeBlog(
    		@Valid @ApiParam(value = "id", required = true) @PathVariable("id") Long id) throws ApiException {
    	return new ApiResponse<>(productEngine.getKnowledgeBlogPublic(id));
    }

    @GetMapping(value = "/product/label_batch/{uid}/{number}")
    @ApiOperation(value = "Get batch by label and number")
    public ApiResponse<ApiProductLabelBatch> getPublicProductLabelBatch(@Valid @ApiParam(value = "Label uid", required = true) @PathVariable("uid") String uid,
    		@Valid @ApiParam(value = "Batch number", required = true)  @PathVariable("number") String number) throws ApiException {
    	return new ApiResponse<>(productEngine.getProductLabelBatchPublic(uid, number));
    }
    
    @GetMapping(value = "/product/label/{uid}/verify_batch_authenticity")
    @ApiOperation(value = "Check batch by number and given date(s)")
    public ApiResponse<Boolean> checkPublicProductLabelBatchAuthenticity(HttpServletRequest servletRequest,
    		@Valid @ApiParam(value = "Label uid", required = true) @PathVariable("uid") String uid,
    		@Valid ApiProductLabelBatchCheckAuthenticity request) throws ApiException {
    	return new ApiResponse<>(productEngine.checkProductLabelBatchAuthenticityPublic(uid, request, servletRequest));
    }
    
    @GetMapping(value = "/product/label/{uid}/verify_batch_origin")
    @ApiOperation(value = "Get batch by label and number")
    public ApiResponse<List<ApiLocation>> checkPublicProductLabelBatchOrigin(HttpServletRequest servletRequest,
    		@Valid @ApiParam(value = "Label uid", required = true) @PathVariable("uid") String uid,
    		@Valid ApiProductLabelBatchCheckOrigin request) throws ApiException {
    	return new ApiResponse<>(productEngine.checkProductLabelBatchOriginPublic(uid, request, servletRequest));
    }
    
    @PostMapping(value = "/logRequest")
    @ApiOperation(value = "Write data to request log for analytics")
    public ApiDefaultResponse logPublicRequest(HttpServletRequest servletRequest,
    		@Valid @RequestBody ApiLogRequest request) throws ApiException {
    	requestLogEngine.log(servletRequest, request);
    	return new ApiDefaultResponse();
    }
    
    @ApiOperation(value = "Returns file contents for given storage key")
    @GetMapping(value = "/document/{storageKey}")
    public ResponseEntity<byte[]> getPublicDocument(@Valid @PathVariable(value = "storageKey", required = true) String storageKey) throws ApiException {
        return commonEngine.getDocument(null, storageKey, null).toResponseEntity();
    }
    
    @ApiOperation(value = "Returns image contents for given storage key")
    @GetMapping(value = "/image/{storageKey}")
    public ResponseEntity<byte[]> getPublicImage(@Valid @PathVariable(value = "storageKey", required = true) String storageKey) throws ApiException {
        return commonEngine.getImage(null, storageKey, null).toResponseEntity();
    }

    @ApiOperation(value = "Returns image contents for given storage key")
    @GetMapping(value = "/image/{storageKey}/{size}")
    public ResponseEntity<byte[]> getPublicResizedImage(@Valid @PathVariable(value = "storageKey", required = true) String storageKey,
    		@Valid @PathVariable(value = "size", required = false) String size) throws ApiException {
        return commonEngine.getImage(null, storageKey, size).toResponseEntity();
    }
    
    @GetMapping(value = "/product/label/feedback/list/{labelUid}")
    @ApiOperation(value = "List feedback for a label uid")
    public ApiPaginatedResponse<ApiProductLabelFeedback> listProductLabelFeedbacks(
    		@Valid @ApiParam(value = "Label id", required = true) @PathVariable("labelUid") String labelUid,
    		@Valid ApiListProductLabelFeedbackRequest request) throws ApiException {
    	return new ApiPaginatedResponse<>(productEngine.listProductLabelFeedbacks(labelUid, request));
    }    

    @PostMapping(value = "/product/label/feedback/{labelUid}")
    @ApiOperation(value = "Add a feedback to a label with a label uid")
    public ApiDefaultResponse addProductLabelFeedback(
    		@Valid @ApiParam(value = "Label id", required = true) @PathVariable("labelUid") String labelUid,
    		@Valid @RequestBody ApiProductLabelFeedback request) throws ApiException {
    	productEngine.addProductLabelFeedback(labelUid, request);
    	return new ApiDefaultResponse();
    }    

    @ApiOperation(value = "Returns 'global settings' value")
    @GetMapping(value = "/globalSettings/{name}")
    public ApiResponse<ApiGlobalSettingsValue> getPublicGlobalSettings(@Valid @PathVariable(value = "name", required = true) String name) {
        return new ApiResponse<>(new ApiGlobalSettingsValue(globalSettingsEngine.getSettings(name, true)));
    }

}
