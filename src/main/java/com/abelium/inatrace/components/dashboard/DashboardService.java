package com.abelium.inatrace.components.dashboard;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.measure_unit_type.api.ApiMeasureUnitType;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.dashboard.api.*;
import com.abelium.inatrace.components.processingaction.ProcessingActionService;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.facility.Facility;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.db.entities.processingorder.ProcessingOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrderPEFieldValue;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.db.entities.stockorder.enums.OrderType;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.ProcessingActionType;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Lazy
@Service
public class DashboardService extends BaseService {

    private final ProcessingActionService processingActionService;

    @Autowired
    public DashboardService(ProcessingActionService processingActionService) {
        this.processingActionService = processingActionService;
    }

    public ApiDeliveriesTotal getDeliveriesAggregatedData(ApiAggregationTimeUnit aggregationType,
                                                          ApiDeliveriesQueryRequest apiDeliveriesQueryRequest) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<StockOrder> root = cq.from(StockOrder.class);

        List<Expression<Integer>> aggregationExpressions = new ArrayList<>();

        // Prepare groupby expression by year/month/week
        switch (aggregationType) {
            case YEAR:
                aggregationExpressions.add(cb.function("YEAR", Integer.class, root.get("productionDate")));
                break;
            case MONTH:
                aggregationExpressions.add(cb.function("MONTH", Integer.class, root.get("productionDate")));
                aggregationExpressions.add(cb.function("YEAR", Integer.class, root.get("productionDate")));
                break;
            case WEEK:
                aggregationExpressions.add(cb.function("WEEK", Integer.class, root.get("productionDate")));
                aggregationExpressions.add(cb.function("YEAR", Integer.class, root.get("productionDate")));
                break;
            case DAY:
            default:
                aggregationExpressions.add(root.get("productionDate"));
                break;
        }

        // Prepare predicates
        // Order type must be of type PURCHASE_ORDER
        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(cb.equal(root.get("orderType"), OrderType.PURCHASE_ORDER));

        // Other predicate from the request params
        if (apiDeliveriesQueryRequest != null) {

            if (apiDeliveriesQueryRequest.companyId != null) {
                predicateList.add(cb.equal(root.get("company").get("id"), apiDeliveriesQueryRequest.companyId));
            }
            if (apiDeliveriesQueryRequest.facilityIds != null) {
                predicateList.add(root.get("facility").get("id").in(apiDeliveriesQueryRequest.facilityIds));
            }
            if (apiDeliveriesQueryRequest.farmerId != null) {
                predicateList.add(cb.equal(root.get("producerUserCustomer").get("id"), apiDeliveriesQueryRequest.farmerId));
            }
            if (apiDeliveriesQueryRequest.representativeOfProducerUserCustomerId != null) {
                predicateList.add(cb.equal(root.get("representativeOfProducerUserCustomer").get("id"), apiDeliveriesQueryRequest.representativeOfProducerUserCustomerId));
            }
            if (apiDeliveriesQueryRequest.semiProductId != null) {
                predicateList.add(cb.equal(root.get("semiProduct").get("id"), apiDeliveriesQueryRequest.semiProductId));
            }
            if (apiDeliveriesQueryRequest.isWomenShare != null) {
                predicateList.add(cb.equal(root.get("womenShare"), apiDeliveriesQueryRequest.isWomenShare));
            }
            if (apiDeliveriesQueryRequest.organicOnly != null) {
                predicateList.add(cb.equal(root.get("organic"), apiDeliveriesQueryRequest.organicOnly));
            }
            if (apiDeliveriesQueryRequest.priceDeterminedLater != null) {
                predicateList.add(cb.equal(root.get("priceDeterminedLater"), apiDeliveriesQueryRequest.priceDeterminedLater));
            }
            if (apiDeliveriesQueryRequest.productionDateStart != null) {
                predicateList.add(cb.greaterThanOrEqualTo(root.get("productionDate"), apiDeliveriesQueryRequest.productionDateStart));
            }
            if (apiDeliveriesQueryRequest.productionDateEnd != null) {
                predicateList.add(cb.lessThanOrEqualTo(root.get("productionDate"), apiDeliveriesQueryRequest.productionDateEnd));
            }
        }

        BigDecimal measuringUnitNormalisationQuotient = calculateSemiProductNormalisationQuotient(
                apiDeliveriesQueryRequest.semiProductId);

        List<ApiDeliveriesTotalItem> totalQuantityList;

        if (aggregationExpressions.size() < 2) {
            cq.multiselect(aggregationExpressions.get(0), cb.sum(root.get("totalQuantity")))
                    .where(predicateList.toArray(new Predicate[0]))
                    .groupBy(aggregationExpressions.get(0));

            totalQuantityList = em.createQuery(cq).getResultList().stream()
                    .map(data -> new ApiDeliveriesTotalItem(String.valueOf(data[0]), null,
                            ((BigDecimal) data[1]).multiply(measuringUnitNormalisationQuotient)
                                    .setScale(2, RoundingMode.HALF_UP)))
                    .collect(Collectors.toList());
        }  else {
            cq.multiselect(aggregationExpressions.get(0), aggregationExpressions.get(1), cb.sum(root.get("totalQuantity")))
                    .where(predicateList.toArray(new Predicate[0]))
                    .groupBy(aggregationExpressions.get(0), aggregationExpressions.get(1));

            totalQuantityList = em.createQuery(cq).getResultList().stream()
                    .map(data -> new ApiDeliveriesTotalItem(
                            String.valueOf(data[0]), (Integer) data[1],
                            ((BigDecimal) data[2]).multiply(measuringUnitNormalisationQuotient)
                                    .setScale(2, RoundingMode.HALF_UP)))
                    .collect(Collectors.toList());
        }

        return new ApiDeliveriesTotal(aggregationType, totalQuantityList);
    }




    public ApiProcessingPerformanceTotal calculateProcessingPerformanceData(
            ApiProcessingPerformanceRequest apiProcessingPerformanceRequest) throws ApiException {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> transactionQuery = cb.createQuery(Object[].class);
        Root<Transaction> transactionRoot = transactionQuery.from(Transaction.class);
        Join<Transaction, ProcessingOrder> transactionProcessingOrderJoin = transactionRoot.join(
                "targetProcessingOrder");
        Join<ProcessingOrder, ProcessingAction> processingActionJoin = transactionProcessingOrderJoin.join(
                "processingAction");

        // Request validation
        if (apiProcessingPerformanceRequest.getCompanyId() == null) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Company id needs to be provided!");
        }
        if (apiProcessingPerformanceRequest.getAggregationType() == null) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "AggregationType needs to be provided!");
        }

        // Input calculation query
        // Prepare groupby expression by year/month/week
        List<Expression<Integer>> aggregationInputExpressions = new ArrayList<>();

        switch (apiProcessingPerformanceRequest.getAggregationType()) {
            case YEAR:
                aggregationInputExpressions.add(cb.function("YEAR", Integer.class,
                        transactionProcessingOrderJoin.get("processingDate")));
                break;
            case MONTH:
                aggregationInputExpressions.add(cb.function("MONTH", Integer.class,
                        transactionProcessingOrderJoin.get("processingDate")));
                aggregationInputExpressions.add(cb.function("YEAR", Integer.class,
                        transactionProcessingOrderJoin.get("processingDate")));
                break;
            case WEEK:
                aggregationInputExpressions.add(cb.function("WEEK", Integer.class,
                        transactionProcessingOrderJoin.get("processingDate")));
                aggregationInputExpressions.add(cb.function("YEAR", Integer.class,
                        transactionProcessingOrderJoin.get("processingDate")));
                break;
            case DAY:
            default:
                aggregationInputExpressions.add(transactionProcessingOrderJoin.get("processingDate"));
                break;
        }

        // Prepare predicates
        // Statuses for processing: PROCESSING, FINAL_PROCESSING
        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(processingActionJoin.get("type")
                .in(ProcessingActionType.PROCESSING, ProcessingActionType.FINAL_PROCESSING));

        // Other predicates from request params
        if (apiProcessingPerformanceRequest.getCompanyId() != null) {
            predicateList.add(
                    cb.equal(transactionRoot.get("company").get("id"), apiProcessingPerformanceRequest.getCompanyId()));
        }
        if (apiProcessingPerformanceRequest.getFacilityId() != null) {
            predicateList.add(cb.equal(transactionRoot.get("sourceFacility").get("id"),
                    apiProcessingPerformanceRequest.getFacilityId()));
        }
        if (apiProcessingPerformanceRequest.getProcessActionId() != null) {
            predicateList.add(
                    cb.equal(processingActionJoin.get("id"), apiProcessingPerformanceRequest.getProcessActionId()));
        }
        if (apiProcessingPerformanceRequest.getDateStart() != null) {
            predicateList.add(cb.greaterThanOrEqualTo(transactionProcessingOrderJoin.get("processingDate"),
                    apiProcessingPerformanceRequest.getDateStart()));
        }
        if (apiProcessingPerformanceRequest.getDateEnd() != null) {
            predicateList.add(cb.lessThanOrEqualTo(transactionProcessingOrderJoin.get("processingDate"),
                    apiProcessingPerformanceRequest.getDateEnd()));
        }

        // If evidence fields filter present, first retrieve list of processing order ids
        // then use them as search predicate
        List<Long> poIds = null;
        if (apiProcessingPerformanceRequest.getEvidenceFields() != null &&
            !apiProcessingPerformanceRequest.getEvidenceFields().isEmpty()) {
            poIds = retrieveProcessingOrdersFromPEFields(apiProcessingPerformanceRequest);
            predicateList.add(transactionProcessingOrderJoin.get("id").in(poIds));
        }

        BigDecimal inputQuotient = calculateInputNormalisationQuotient(
                apiProcessingPerformanceRequest.getProcessActionId());

        ApiMeasureUnitType apiMeasureUnitType = retrieveOutputMeasuringUnitType(
                apiProcessingPerformanceRequest.getProcessActionId());

        // Map is used for combining queries for input and output
        Map<String, ApiProcessingPerformanceTotalItem> mapProcPerf = new HashMap<>();

        if (aggregationInputExpressions.size() < 2) {
            transactionQuery.multiselect(aggregationInputExpressions.get(0), cb.sum(transactionRoot.get("inputQuantity")))
                    .where(predicateList.toArray(new Predicate[0])).groupBy(aggregationInputExpressions.get(0));

            // Query for input calcuclation, then add to the map
            em.createQuery(transactionQuery).getResultList().forEach(data -> mapProcPerf.put(String.valueOf(data[0]),
                    new ApiProcessingPerformanceTotalItem(String.valueOf(data[0]),
                            ((BigDecimal) data[1]).multiply(inputQuotient).setScale(2, RoundingMode.HALF_UP),
                            BigDecimal.ZERO, BigDecimal.ZERO)));
        } else {
            transactionQuery.multiselect(aggregationInputExpressions.get(0), aggregationInputExpressions.get(1),
                            cb.sum(transactionRoot.get("inputQuantity"))).where(predicateList.toArray(new Predicate[0]))
                    .groupBy(aggregationInputExpressions.get(0), aggregationInputExpressions.get(1));

            // Query for input calcuclation, then add to the map
            em.createQuery(transactionQuery).getResultList().forEach(data -> mapProcPerf.put(
                    data[0] + "-" + data[1],
                    new ApiProcessingPerformanceTotalItem(String.valueOf(data[0]), (Integer) data[1],
                            ((BigDecimal) data[2]).multiply(inputQuotient).setScale(2, RoundingMode.HALF_UP),
                            BigDecimal.ZERO, BigDecimal.ZERO)));
        }

        // Output calculation
        CriteriaQuery<Object[]> stockOrderQuery = cb.createQuery(Object[].class);
        Root<StockOrder> stockOrderRoot = stockOrderQuery.from(StockOrder.class);
        Join<StockOrder, ProcessingOrder> stockOrderProcessingOrderJoin = stockOrderRoot.join("processingOrder");
        Join<ProcessingOrder, ProcessingAction> stockOrderProcessingActionJoin = stockOrderProcessingOrderJoin.join(
                "processingAction");

        // Prepare groupby expression by year/month/week
        List<Expression<Integer>> aggregationOutputExpressions = new ArrayList<>();

        switch (apiProcessingPerformanceRequest.getAggregationType()) {
            case YEAR:
                aggregationOutputExpressions.add(cb.function("YEAR", Integer.class,
                        stockOrderProcessingOrderJoin.get("processingDate")));
                break;
            case MONTH:
                aggregationOutputExpressions.add(cb.function("MONTH", Integer.class,
                        stockOrderProcessingOrderJoin.get("processingDate")));
                aggregationOutputExpressions.add(cb.function("YEAR", Integer.class,
                        stockOrderProcessingOrderJoin.get("processingDate")));
                break;
            case WEEK:
                aggregationOutputExpressions.add(cb.function("WEEK", Integer.class,
                        stockOrderProcessingOrderJoin.get("processingDate")));
                aggregationOutputExpressions.add(cb.function("YEAR", Integer.class,
                        stockOrderProcessingOrderJoin.get("processingDate")));
                break;
            case DAY:
            default:
                aggregationOutputExpressions.add(stockOrderProcessingOrderJoin.get("processingDate"));
                break;
        }


        // Prepare predicates
        // Order type for processing: PROCESSING_ORDER
        // Statuses for processing: PROCESSING, FINAL_PROCESSING
        predicateList = new ArrayList<>();
        predicateList.add(cb.equal(stockOrderRoot.get("orderType"), OrderType.PROCESSING_ORDER));

        predicateList.add(stockOrderProcessingActionJoin.get("type")
                .in(ProcessingActionType.PROCESSING, ProcessingActionType.FINAL_PROCESSING));

        // Other predicates from request params
        if (apiProcessingPerformanceRequest.getCompanyId() != null) {
            predicateList.add(
                    cb.equal(stockOrderRoot.get("company").get("id"), apiProcessingPerformanceRequest.getCompanyId()));
        }
        if (apiProcessingPerformanceRequest.getFacilityId() != null) {
            predicateList.add(
                    cb.equal(stockOrderRoot.get("facility").get("id"), apiProcessingPerformanceRequest.getFacilityId()));
        }
        if (apiProcessingPerformanceRequest.getProcessActionId() != null) {
            predicateList.add(cb.equal(stockOrderProcessingActionJoin.get("id"),
                    apiProcessingPerformanceRequest.getProcessActionId()));
        }
        if (apiProcessingPerformanceRequest.getDateStart() != null) {
            predicateList.add(cb.greaterThanOrEqualTo(stockOrderProcessingOrderJoin.get("processingDate"),
                    apiProcessingPerformanceRequest.getDateStart()));
        }
        if (apiProcessingPerformanceRequest.getDateEnd() != null) {
            predicateList.add(cb.lessThanOrEqualTo(stockOrderProcessingOrderJoin.get("processingDate"),
                    apiProcessingPerformanceRequest.getDateEnd()));
        }

        // If evidence fields filter present, use processing order ids as search predicate
        if (apiProcessingPerformanceRequest.getEvidenceFields() != null &&
                !apiProcessingPerformanceRequest.getEvidenceFields().isEmpty()) {
            predicateList.add(stockOrderProcessingOrderJoin.get("id").in(poIds));
        }

        List<ApiProcessingPerformanceTotalItem> resultList = null;

        if (aggregationInputExpressions.size() < 2) {
            stockOrderQuery.multiselect(aggregationOutputExpressions.get(0),
                            cb.sum(stockOrderRoot.get("totalQuantity"))).where(predicateList.toArray(new Predicate[0]))
                    .groupBy(aggregationOutputExpressions.get(0));

            // Merge inputs and outputs into the map,
            // Also calculate percentage of output/input
            em.createQuery(stockOrderQuery).getResultList().forEach(data -> {
                if (!mapProcPerf.containsKey(String.valueOf(data[0]))) {
                    mapProcPerf.put(String.valueOf(data[0]),
                            new ApiProcessingPerformanceTotalItem(String.valueOf(data[0]), BigDecimal.ZERO,
                                    (BigDecimal) data[1], BigDecimal.ZERO));
                } else {
                    mapProcPerf.get(String.valueOf(data[0])).setOutputQuantity((BigDecimal) data[1]);
                    mapProcPerf.get(String.valueOf(data[0])).setRatio(
                            ((BigDecimal) data[1]).multiply(BigDecimal.valueOf(100L))
                                    .divide(mapProcPerf.get(String.valueOf(data[0])).getInputQuantity(),
                                            RoundingMode.HALF_UP));
                }
            });

            resultList = new ArrayList<>(mapProcPerf.values());

            // Sort the aggregation results
            resultList.sort((item1,item2) -> {
                if (item1 == null) return 1;
                if (item2 == null) return -1;
                if (apiProcessingPerformanceRequest.getAggregationType().equals(ApiAggregationTimeUnit.DAY)) {
                    return LocalDate.parse(item1.getUnit()).compareTo(LocalDate.parse(item2.getUnit()));
                } else {
                    return Integer.valueOf(item1.getUnit()).compareTo(Integer.valueOf(item2.getUnit()));
                }

            });


        } else {
            stockOrderQuery.multiselect(aggregationOutputExpressions.get(0), aggregationOutputExpressions.get(1),
                            cb.sum(stockOrderRoot.get("totalQuantity"))).where(predicateList.toArray(new Predicate[0]))
                    .groupBy(aggregationOutputExpressions.get(0), aggregationOutputExpressions.get(1));

            // Merge inputs and outputs into the map,
            // Also calculate percentage of output/input
            em.createQuery(stockOrderQuery).getResultList().forEach(data -> {
                if (!mapProcPerf.containsKey(data[0] + "-" + data[1])) {
                    mapProcPerf.put(data[0] + "-" + data[1],
                            new ApiProcessingPerformanceTotalItem(String.valueOf(data[0]), (Integer) data[1], BigDecimal.ZERO,
                                    (BigDecimal) data[2], BigDecimal.ZERO));
                } else {
                    mapProcPerf.get(data[0] + "-" + data[1]).setOutputQuantity((BigDecimal) data[2]);
                    mapProcPerf.get(data[0] + "-" + data[1]).setRatio(
                            ((BigDecimal) data[2]).multiply(BigDecimal.valueOf(100L))
                                    .divide(mapProcPerf.get(data[0] + "-" + data[1]).getInputQuantity(),
                                            RoundingMode.HALF_UP));
                }
            });

            resultList = new ArrayList<>(mapProcPerf.values());

            // Sort the aggregation results
            resultList.sort((item1,item2) -> {
                if (item1 == null) return 1;
                if (item2 == null) return -1;
                if (item1.getYear().compareTo(item2.getYear()) == 0) {
                    return Integer.valueOf(item1.getUnit()).compareTo(Integer.valueOf(item2.getUnit()));
                } else {
                    return item1.getYear().compareTo(item2.getYear());
                }
            });
        }

        return new ApiProcessingPerformanceTotal(apiProcessingPerformanceRequest.getAggregationType(),
                apiMeasureUnitType, resultList);
    }

    private ApiMeasureUnitType retrieveOutputMeasuringUnitType(Long idProcessingAction) throws ApiException {

        ProcessingAction processingAction = processingActionService.fetchProcessingAction(idProcessingAction);

        if (ProcessingActionType.FINAL_PROCESSING.equals(processingAction.getType())) {
            if (processingAction.getOutputFinalProduct() != null) {
                return MeasureUnitTypeMapper.toApiMeasureUnitType(
                        processingAction.getOutputFinalProduct().getMeasurementUnitType());
            }
        } else {

            if (processingAction.getOutputSemiProducts() != null && !processingAction.getOutputSemiProducts().isEmpty() &&
                    processingAction.getOutputSemiProducts().get(0) != null &&
                    processingAction.getOutputSemiProducts().get(0).getOutputSemiProduct() != null) {
                return MeasureUnitTypeMapper.toApiMeasureUnitType(
                        processingAction.getOutputSemiProducts().get(0).getOutputSemiProduct().getMeasurementUnitType());
            }
        }
        
        return null;
    }

    private BigDecimal calculateSemiProductNormalisationQuotient(Long idSemiProduct) {

        if (idSemiProduct == null) {
            return BigDecimal.ONE;
        }

        SemiProduct semiProduct = Queries.get(em, SemiProduct.class, idSemiProduct);

        if (semiProduct == null || semiProduct.getMeasurementUnitType() == null) {
            return BigDecimal.ONE;
        }

        return semiProduct.getMeasurementUnitType().getWeight();
    }

    private BigDecimal calculateInputNormalisationQuotient(Long idProcessingAction) throws ApiException {

        ProcessingAction processingAction = processingActionService.fetchProcessingAction(idProcessingAction);

        if (processingAction == null || processingAction.getInputSemiProduct() == null ||
                processingAction.getInputSemiProduct().getMeasurementUnitType() == null) {
            return BigDecimal.ONE;
        }
        BigDecimal inputWeight = processingAction.getInputSemiProduct().getMeasurementUnitType().getWeight();

        BigDecimal outputWeight = BigDecimal.ONE;

        if (ProcessingActionType.FINAL_PROCESSING.equals(processingAction.getType())) {

            if (processingAction.getOutputFinalProduct() != null &&
                    processingAction.getOutputFinalProduct().getMeasurementUnitType() != null) {
                outputWeight = processingAction.getOutputFinalProduct().getMeasurementUnitType().getWeight();
            }
        } else {

            // default return 1, if not found
            if (processingAction.getOutputSemiProducts() != null &&
                    !processingAction.getOutputSemiProducts().isEmpty() &&
                    processingAction.getOutputSemiProducts().get(0) != null &&
                    processingAction.getOutputSemiProducts().get(0).getOutputSemiProduct() != null &&
                    processingAction.getOutputSemiProducts().get(0).getOutputSemiProduct().getMeasurementUnitType() !=
                            null) {
                outputWeight = processingAction.getOutputSemiProducts().get(0).getOutputSemiProduct()
                        .getMeasurementUnitType().getWeight();
            }
        }

        return inputWeight.divide(outputWeight, RoundingMode.HALF_UP);

    }

    /**
     * Query the processing orders that match the processing evindence fields conditions. Conditions are combined
     * using or:
     *      (fieldName eq fieldValue) or (fieldName eq fieldValue) ...
     *
     * @param request - operation request
     * @return - list of processingOrder ids, that match the condition
     */
    private List<Long> retrieveProcessingOrdersFromPEFields(ApiProcessingPerformanceRequest request) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> processingOrdersQuery = cb.createQuery(Long.class);
        Root<StockOrderPEFieldValue> soPevRoot = processingOrdersQuery.from(StockOrderPEFieldValue.class);
        Join<StockOrderPEFieldValue, StockOrder> sopevStockOrderJoin = soPevRoot.join("stockOrder");

        List<Predicate> predicateList = new ArrayList<>();

        for (ApiProcessingPerformanceRequestEvidenceField pefield : request.getEvidenceFields()) {
            if (pefield.getStringValue() != null) {
                predicateList.add(cb.and(cb.equal(soPevRoot.get("processingEvidenceField").get("fieldName"),
                                pefield.getEvidenceField().getFieldName()),
                        cb.like(soPevRoot.get("stringValue"), pefield.getStringValue())));
            } else if (pefield.getNumericValue() != null) {
                predicateList.add(cb.and(cb.equal(soPevRoot.get("processingEvidenceField").get("fieldName"),
                                pefield.getEvidenceField().getFieldName()),
                        cb.equal(soPevRoot.get("numericValue"), pefield.getNumericValue())));
            } else if (pefield.getInstantValue() != null) {
                predicateList.add(cb.and(cb.equal(soPevRoot.get("processingEvidenceField").get("fieldName"),
                                pefield.getEvidenceField().getFieldName()),
                        cb.equal(soPevRoot.get("instantValue"), pefield.getInstantValue())));
            }
        }
        Predicate orPredicate = cb.or(predicateList.toArray(new Predicate[0]));

        processingOrdersQuery.multiselect(sopevStockOrderJoin.get("processingOrder").get("id")).where(orPredicate);

        return new ArrayList<>(em.createQuery(processingOrdersQuery).getResultList());
    }

    public byte[] convertDeliveryDataToCsv(ApiDeliveriesTotal total, ApiDeliveriesQueryRequest request) throws IOException {

        // get additional filters
        Map<String, String> additionalFilters = createAdditionalFiltersFromRequest(request);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(bytes), CSVFormat.RFC4180)) {

            List<String> headerRecord = new ArrayList<>();

            headerRecord.add("Date");
            headerRecord.add("Total quantity");
            headerRecord.add("Measuring unit");

            // Add additional  filters
            headerRecord.addAll(additionalFilters.keySet());

            // Generate headers
            csvPrinter.printRecord(headerRecord);

            for (ApiDeliveriesTotalItem item: total.getTotals()) {

                List<String> dataValues = new ArrayList<>();
                dataValues.add(createDateColumn(item, total.getUnitType()));
                dataValues.add(item.getTotalQuantity().toString());

                // deliveries unit is always in kg
                dataValues.add("kg");

                // Prepare additional filter values
                dataValues.addAll(additionalFilters.values());

                // Generate data
                csvPrinter.printRecord(dataValues);
            }

            csvPrinter.flush();
        }

        return bytes.toByteArray();
    }

    public byte[] convertDeliveryDataToPDF(ApiDeliveriesTotal total, ApiDeliveriesQueryRequest request) throws ApiException {

        // get additional filters
        Map<String, String> additionalFilters = createAdditionalFiltersFromRequest(request);

        List<String> headerCells = new ArrayList<>();
        headerCells.add("Date");
        headerCells.add("Total quantity");
        headerCells.add("Measuring unit");
        headerCells.addAll(additionalFilters.keySet());

        PdfPTable table = new PdfPTable(headerCells.size());

        // Table header cells
        headerCells.forEach(title -> {
            PdfPCell header = new PdfPCell();
            header.setPhrase(new Phrase(title));
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setBorderWidth(2);
            table.addCell(header);
        });

        total.getTotals().forEach(totalItem -> {

            table.addCell(createDateColumn(totalItem, total.getUnitType()));
            table.addCell(totalItem.getTotalQuantity().toString());

            // deliveries unit is always in kg
            table.addCell("kg");

            additionalFilters.values().forEach(table::addCell);
        });

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);

            document.open();
            document.setPageSize(PageSize.A4.rotate());
            document.newPage();
            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new ApiException(ApiStatus.ERROR, "Error while creating PDF file!");
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] convertDeliveryDataToExcel(ApiDeliveriesTotal total, ApiDeliveriesQueryRequest request) throws IOException {

        // get additional filters
        Map<String, String> additionalFilters = createAdditionalFiltersFromRequest(request);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Deliveries");

            // generate header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0, CellType.STRING).setCellValue("Date");
            headerRow.createCell(1, CellType.STRING).setCellValue("Total quantity");
            headerRow.createCell(2, CellType.STRING).setCellValue("Measuring unit");

            int headerColumnIndex = 3;
            // additional selected filters
            for (String filterName : additionalFilters.keySet()) {
                headerRow.createCell(headerColumnIndex++, CellType.STRING).setCellValue(filterName);
            }

            for (int i = 0; i < total.getTotals().size(); i++) {

                Row row = sheet.createRow(i + 1);

                // generate common fields
                row.createCell(0, CellType.STRING).setCellValue(createDateColumn(total.getTotals().get(i), total.getUnitType()));
                row.createCell(1, CellType.NUMERIC).setCellValue(total.getTotals().get(i).getTotalQuantity().doubleValue());

                // deliveries unit is always in kg
                row.createCell(2, CellType.STRING).setCellValue("kg");

                int columnIndex = 3;
                // generate additional fields
                for (String filterValue : additionalFilters.values()) {
                    row.createCell(columnIndex++, CellType.STRING).setCellValue(filterValue);
                }
            }

            for (int j = 0; j < headerColumnIndex; j++) {
                sheet.setColumnWidth(j, 4000);
            }

            workbook.write(byteArrayOutputStream);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] convertPerformanceDataToCsv(ApiProcessingPerformanceTotal total, ApiProcessingPerformanceRequest request) throws IOException {

        // get additional filters
        Map<String, String> additionalFilters = createAdditionalFiltersFromRequest(request);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(bytes), CSVFormat.RFC4180)) {

            List<String> headerRecord = new ArrayList<>();

            headerRecord.add("Date");
            headerRecord.add("Input quantity");
            headerRecord.add("Output quantity");
            headerRecord.add("Measuring unit");
            headerRecord.add("Ratio");

            // Add additional  filters
            headerRecord.addAll(additionalFilters.keySet());

            // Generate headers
            csvPrinter.printRecord(headerRecord);

            for (ApiProcessingPerformanceTotalItem item: total.getTotals()) {

                List<String> dataValues = new ArrayList<>();
                dataValues.add(createDateColumn(item, total.getUnitType()));
                dataValues.add(item.getInputQuantity().toString());
                dataValues.add(item.getOutputQuantity().toString());
                dataValues.add(total.getMeasureUnitType().getLabel());
                dataValues.add(item.getRatio().toString());

                // Prepare additional filter values
                dataValues.addAll(additionalFilters.values());

                // Generate data
                csvPrinter.printRecord(dataValues);
            }

            csvPrinter.flush();
        }

        return bytes.toByteArray();
    }

    public byte[] convertPerformanceDataToPDF(ApiProcessingPerformanceTotal total, ApiProcessingPerformanceRequest request) throws ApiException {

        // get additional filters
        Map<String, String> additionalFilters = createAdditionalFiltersFromRequest(request);

        List<String> headerCells = new ArrayList<>();
        headerCells.add("Date");
        headerCells.add("Input quantity");
        headerCells.add("Output quantity");
        headerCells.add("Measuring unit");
        headerCells.add("Ratio");
        headerCells.addAll(additionalFilters.keySet());

        PdfPTable table = new PdfPTable(headerCells.size());

        // Table header cells
        headerCells.forEach(title -> {
            PdfPCell header = new PdfPCell();
            header.setPhrase(new Phrase(title));
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setBorderWidth(2);
            table.addCell(header);
        });

        total.getTotals().forEach(totalItem -> {

            table.addCell(createDateColumn(totalItem, total.getUnitType()));
            table.addCell(totalItem.getInputQuantity().toString());
            table.addCell(totalItem.getOutputQuantity().toString());
            table.addCell(total.getMeasureUnitType().getLabel());
            table.addCell(totalItem.getRatio().toString());

            additionalFilters.values().forEach(table::addCell);
        });

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);

            document.open();
            document.setPageSize(PageSize.A4.rotate());
            document.newPage();
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new ApiException(ApiStatus.ERROR, "Error while creating PDF file!");
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] convertPerformanceDataToExcel(ApiProcessingPerformanceTotal total, ApiProcessingPerformanceRequest request) throws IOException {

        // get additional filters
        Map<String, String> additionalFilters = createAdditionalFiltersFromRequest(request);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Processing performance");

            // generate header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0, CellType.STRING).setCellValue("Date");
            headerRow.createCell(1, CellType.STRING).setCellValue("Input quantity");
            headerRow.createCell(2, CellType.STRING).setCellValue("Output quantity");
            headerRow.createCell(3, CellType.STRING).setCellValue("Measuring unit");
            headerRow.createCell(4, CellType.STRING).setCellValue("Ratio");

            int headerColumnIndex = 5;
            // additional selected filters
            for (String filterName : additionalFilters.keySet()) {
                headerRow.createCell(headerColumnIndex++, CellType.STRING).setCellValue(filterName);
            }

            for (int i = 0; i < total.getTotals().size(); i++) {

                Row row = sheet.createRow(i + 1);

                // generate common fields
                row.createCell(0, CellType.STRING).setCellValue(createDateColumn(total.getTotals().get(i), total.getUnitType()));
                row.createCell(1, CellType.NUMERIC).setCellValue(total.getTotals().get(i).getInputQuantity().doubleValue());
                row.createCell(2, CellType.NUMERIC).setCellValue(total.getTotals().get(i).getOutputQuantity().doubleValue());
                row.createCell(3, CellType.STRING).setCellValue(total.getMeasureUnitType().getLabel());
                row.createCell(4, CellType.NUMERIC).setCellValue(total.getTotals().get(i).getRatio().doubleValue());

                int columnIndex = 5;
                // generate additional fields
                for (String filterValue : additionalFilters.values()) {
                    row.createCell(columnIndex++, CellType.STRING).setCellValue(filterValue);
                }
            }

            for (int j = 0; j < headerColumnIndex; j++) {
                sheet.setColumnWidth(j, 4000);
            }

            workbook.write(byteArrayOutputStream);
        }

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Creates filter columns for Export.
     * @param queryRequest - input query of type ApiDeliveriesQueryRequest
     * @return - map containing pair (columnName, columnValue) for additional filters
     */
    private Map<String, String> createAdditionalFiltersFromRequest(ApiDeliveriesQueryRequest queryRequest) {

        Map<String, String> additionalFiltersMap = new HashMap<>();

        if (queryRequest.getCompanyId() != null) {
            Company company = Queries.get(em, Company.class, queryRequest.getCompanyId());
            additionalFiltersMap.put("Company", company.getName());
        }
        if (queryRequest.getFacilityIds() != null) {
            String facilities = "";
            for (int i=0; i< queryRequest.getFacilityIds().size(); i++) {
                Facility facility = Queries.get(em, Facility.class, queryRequest.getFacilityIds().get(i));
                facilities = facilities.concat(facility.getName());
                if (i < queryRequest.getFacilityIds().size() - 1) {
                    facilities = facilities.concat(",");
                }
            }

            additionalFiltersMap.put("Facilities", facilities);
        }
        if (queryRequest.getFarmerId() != null) {
            UserCustomer userCustomer = Queries.get(em, UserCustomer.class, queryRequest.getFarmerId());
            additionalFiltersMap.put("Farmer", userCustomer.getName() + " " + userCustomer.getSurname());
        }
        if (queryRequest.getRepresentativeOfProducerUserCustomerId() != null) {
            UserCustomer representativeCustomer = Queries.get(em, UserCustomer.class,
                    queryRequest.getRepresentativeOfProducerUserCustomerId());
            additionalFiltersMap.put("Collector",
                    representativeCustomer.getName() + " " + representativeCustomer.getSurname());
        }
        if (queryRequest.getSemiProductId() != null) {
            SemiProduct semiProduct = Queries.get(em, SemiProduct.class, queryRequest.getSemiProductId());
            additionalFiltersMap.put("Semi-product", semiProduct.getName());
        }
        if (queryRequest.getOrganicOnly() != null && queryRequest.getOrganicOnly()) {
            additionalFiltersMap.put("Organic", queryRequest.getOrganicOnly().toString());
        }
        if (queryRequest.getWomenShare() != null && queryRequest.getWomenShare()) {
            additionalFiltersMap.put("Women only", queryRequest.getWomenShare().toString());
        }
        if (queryRequest.getPriceDeterminedLater() != null && queryRequest.getPriceDeterminedLater()) {
            additionalFiltersMap.put("Product in deposit", queryRequest.getPriceDeterminedLater().toString());
        }

        return additionalFiltersMap;
    }

    /**
     * Creates filter columns for Export.
     * @param queryRequest - input query of type ApiProcessingPerformanceRequest
     * @return - map containing pair (columnName, columnValue) for additional filters
     */
    private Map<String, String> createAdditionalFiltersFromRequest(ApiProcessingPerformanceRequest queryRequest) {

        Map<String, String> additionalFiltersMap = new HashMap<>();

        if (queryRequest.getCompanyId() != null) {
            Company company = Queries.get(em, Company.class, queryRequest.getCompanyId());
            additionalFiltersMap.put("Company", company.getName());
        }
        if (queryRequest.getFacilityId() != null) {
            Facility facility = Queries.get(em, Facility.class, queryRequest.getFacilityId());
            additionalFiltersMap.put("Facility", facility.getName());
        }
        if (queryRequest.getProcessActionId() != null) {
            ProcessingAction processingAction = Queries.get(em, ProcessingAction.class, queryRequest.getProcessActionId());
            additionalFiltersMap.put("Processing", processingAction.getPublicTimelineLabel());
        }
        queryRequest.getEvidenceFields().forEach(evidenceField -> {
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

        return additionalFiltersMap;
    }

    /**
     * Generates date column for export.
     * @param totalItem - row of type ApiDeliveriesTotalItem
     * @param unitType - time unit
     * @return - formated date column
     */
    private String createDateColumn(ApiDeliveriesTotalItem totalItem, ApiAggregationTimeUnit unitType) {

        if (totalItem != null) {
            switch (unitType) {
                case WEEK:
                    return translateWeek(totalItem.getUnit()) + " " + totalItem.getYear().toString();
                case MONTH:
                    return translateMonth(totalItem.getUnit()) + " " + totalItem.getYear().toString();
                case YEAR:
                case DAY:
                default:
                    return totalItem.getUnit();
            }
        }

        return null;
    }

    /**
     * Generates date column for export.
     * @param totalItem - row of type ApiProcessingPerformanceTotalItem
     * @param unitType - time unit
     * @return - formated date column
     */
    private String createDateColumn(ApiProcessingPerformanceTotalItem totalItem, ApiAggregationTimeUnit unitType) {

        if (totalItem != null) {
            switch (unitType) {
                case WEEK:
                    return translateWeek(totalItem.getUnit()) + " " + totalItem.getYear().toString();
                case MONTH:
                    return translateMonth(totalItem.getUnit()) + " " + totalItem.getYear().toString();
                case YEAR:
                case DAY:
                default:
                    return totalItem.getUnit();
            }
        }

        return null;
    }

    /**
     * Helper method for week translation
     * @param unit - week id
     * @return - translated and formated week
     */
    private String translateWeek(String unit) {
        if (unit != null) {
            return "Week-" + unit;
        }
        return null;
    }

    /**
     * Helper method for month translations
     * @param monthNum - id of month
     * @return - translated
     */
    private String translateMonth(String monthNum) {
        switch (monthNum) {
            case "1":
                return "Jan";
            case "2":
                return "Feb";
            case "3":
                return "Mar";
            case "4":
                return "Apr";
            case "5":
                return "May";
            case "6":
                return "Jun";
            case "7":
                return "Jul";
            case "8":
                return "Aug";
            case "9":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
        }
        return null;
    }
}
