package com.abelium.inatrace.components.dashboard;

import com.abelium.inatrace.api.ApiResponse;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.dashboard.api.*;
import com.abelium.inatrace.components.stockorder.StockOrderQueryRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("deliveries-aggregated-data")
    public ApiResponse<ApiDeliveriesTotal> getDeliveriesAggregatedData(
            @Valid @ApiParam(value = "Company ID", required = true) @RequestParam("companyId") Long companyId,
            @Valid @ApiParam(value = "Facility IDs") @RequestParam(value = "facilityIds", required = false) List<Long> facilityIds,
            @Valid @ApiParam(value = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @ApiParam(value = "Farmer (UserCustomer) ID") @RequestParam(value = "farmerId", required = false) Long farmerId,
            @Valid @ApiParam(value = "Collector (Representative of producer UserCustomer) ID") @RequestParam(value = "collectorId", required = false) Long collectorId,
            @Valid @ApiParam(value = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @ApiParam(value = "Organic only") @RequestParam(value = "organicOnly", required = false) Boolean organicOnly,
            @Valid @ApiParam(value = "Price determined later") @RequestParam(value = "priceDeterminedLater", required = false) Boolean priceDeterminedLater,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
            @Valid @ApiParam(value = "Aggregation type", required = true) @RequestParam(value = "aggregationType") ApiAggregationTimeUnit aggregationType
    ) {
        return new ApiResponse<>(dashboardService.getDeliveriesAggregatedData(
                aggregationType,
                new StockOrderQueryRequest(
                        companyId,
                        facilityIds,
                        farmerId,
                        collectorId,
                        semiProductId,
                        isWomenShare,
                        organicOnly,
                        priceDeterminedLater,
                        productionDateStart,
                        productionDateEnd
                )));
    }

    @GetMapping("deliveries-aggregated-data/export")
    public ResponseEntity<byte[]> exportDeliveriesData(
            @Valid @ApiParam(value = "Company ID", required = true) @RequestParam("companyId") Long companyId,
            @Valid @ApiParam(value = "Facility IDs") @RequestParam(value = "facilityIds", required = false) List<Long> facilityIds,
            @Valid @ApiParam(value = "Semi-product ID") @RequestParam(value = "semiProductId", required = false) Long semiProductId,
            @Valid @ApiParam(value = "Farmer (UserCustomer) ID") @RequestParam(value = "farmerId", required = false) Long farmerId,
            @Valid @ApiParam(value = "Collector (Representative of producer UserCustomer) ID") @RequestParam(value = "collectorId", required = false) Long collectorId,
            @Valid @ApiParam(value = "Is women share") @RequestParam(value = "isWomenShare", required = false) Boolean isWomenShare,
            @Valid @ApiParam(value = "Organic only") @RequestParam(value = "organicOnly", required = false) Boolean organicOnly,
            @Valid @ApiParam(value = "Price determined later") @RequestParam(value = "priceDeterminedLater", required = false) Boolean priceDeterminedLater,
            @Valid @ApiParam(value = "Production date range start") @RequestParam(value = "productionDateStart", required = false) LocalDate productionDateStart,
            @Valid @ApiParam(value = "Production date range end") @RequestParam(value = "productionDateEnd", required = false) LocalDate productionDateEnd,
            @Valid @ApiParam(value = "Aggregation type", required = true) @RequestParam(value = "aggregationType") ApiAggregationTimeUnit aggregationType,
            @Valid @ApiParam(value = "Export type", required = true) @RequestParam(value = "exportType") ApiExportType exportType
    ) throws ApiException {

        byte[] response = null;

        ApiDeliveriesTotal total = dashboardService.getDeliveriesAggregatedData(
            aggregationType,
            new StockOrderQueryRequest(
                    companyId,
                    facilityIds,
                    farmerId,
                    collectorId,
                    semiProductId,
                    isWomenShare,
                    organicOnly,
                    priceDeterminedLater,
                    productionDateStart,
                    productionDateEnd
            ));

        Map<String, String> additionalFiltersMap = new HashMap<>();

        if (companyId != null) {
            additionalFiltersMap.put("Company", companyId.toString());
        }
        if (facilityIds != null) {
            additionalFiltersMap.put("Facilities", facilityIds.toString());
        }
        if (farmerId != null) {
            additionalFiltersMap.put("Farmer", farmerId.toString());
        }
        if (collectorId != null) {
            additionalFiltersMap.put("Collector", collectorId.toString());
        }
        if (semiProductId != null) {
            additionalFiltersMap.put("Semi-product", semiProductId.toString());
        }
        if (organicOnly != null && organicOnly) {
            additionalFiltersMap.put("Organic", organicOnly.toString());
        }
        if (isWomenShare != null && isWomenShare) {
            additionalFiltersMap.put("Women only", isWomenShare.toString());
        }
        if (priceDeterminedLater != null && priceDeterminedLater) {
            additionalFiltersMap.put("Product in deposit", priceDeterminedLater.toString());
        }

        try {
            switch (exportType) {
                case CSV:
                    response = dashboardService.convertDeliveryDataToCsv(total, additionalFiltersMap);
                    break;
                case PDF:
                    response = dashboardService.convertDeliveryDataToPDF(total, additionalFiltersMap);
                    break;
                case EXCEL:
                        response = dashboardService.convertDeliveryDataToExcel(total, additionalFiltersMap);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new ApiException(ApiStatus.ERROR, "Error while exporting file!");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);
    }

    @PostMapping(value = "processing-performance-data")
    @ApiOperation("Calculates processing performance data")
    public ApiResponse<ApiProcessingPerformanceTotal> calculateProcessingPerformanceData(
            @Valid @RequestBody ApiProcessingPerformanceRequest processingPerformanceRequest
    ) throws ApiException {
        return new ApiResponse<>(dashboardService.calculateProcessingPerformanceData(processingPerformanceRequest));
    }

    @PostMapping(value = "processing-performance-data/export")
    @ApiOperation("Exports processing performance data to the requested format")
    public ResponseEntity<byte[]> exportProcessingPerformanceData(
            @Valid @RequestBody ApiProcessingPerformanceRequest processingPerformanceRequest
    ) throws ApiException {

        byte[] response = null;

        ApiProcessingPerformanceTotal total = dashboardService.calculateProcessingPerformanceData(processingPerformanceRequest);

        Map<String, String> additionalFiltersMap = new HashMap<>();

        if (processingPerformanceRequest.getCompanyId() != null) {
            additionalFiltersMap.put("Company", processingPerformanceRequest.getCompanyId().toString());
        }
        if (processingPerformanceRequest.getFacilityId() != null) {
            additionalFiltersMap.put("Facility", processingPerformanceRequest.getFacilityId().toString());
        }
        if (processingPerformanceRequest.getProcessActionId() != null) {
            additionalFiltersMap.put("Processing", processingPerformanceRequest.getProcessActionId().toString());
        }
        processingPerformanceRequest.getEvidenceFields().forEach(evidenceField -> {
            if (evidenceField.getStringValue() != null) {
                additionalFiltersMap.put(evidenceField.getEvidenceField().getFieldName(),
                        evidenceField.getStringValue());
            } else if (evidenceField.getNumericValue() != null) {
                additionalFiltersMap.put(evidenceField.getEvidenceField().getFieldName(),
                        evidenceField.getNumericValue().toString());
            } else if (evidenceField.getInstantValue() != null) {
                additionalFiltersMap.put(evidenceField.getEvidenceField().getFieldName(),
                        evidenceField.getInstantValue().toString());
            }
        });

        try {
            switch (processingPerformanceRequest.getExportType()) {
                case CSV:
                    response = dashboardService.convertPerformanceDataToCsv(total, additionalFiltersMap);
                    break;
                case PDF:
                    response = dashboardService.convertPerformanceDataToPDF(total, additionalFiltersMap);
                    break;
                case EXCEL:
                    response = dashboardService.convertPerformanceDataToExcel(total, additionalFiltersMap);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new ApiException(ApiStatus.ERROR, "Error while exporting file!");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);
    }
}
