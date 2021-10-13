package com.abelium.inatrace.components.stockorder.mappers;

import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductMapper;
import com.abelium.inatrace.components.common.mappers.ActivityProofMapper;
import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.facility.FacilityMapper;
import com.abelium.inatrace.components.usercustomer.mappers.UserCustomerMapper;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.user.mappers.UserMapper;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;

import java.util.stream.Collectors;

public class StockOrderMapper {

    public static ApiStockOrder toApiStockOrder(StockOrder entity, Long userId) {

        ApiStockOrder apiStockOrder = new ApiStockOrder();
        apiStockOrder.setId(entity.getId());
        apiStockOrder.setIdentifier(entity.getIdentifier());
        apiStockOrder.setCreatedBy(UserMapper.toSimpleApiUser(entity.getCreatedBy()));
        apiStockOrder.setUpdatedBy(UserMapper.toSimpleApiUser(entity.getUpdatedBy()));
        apiStockOrder.setUpdateTimestamp(entity.getUpdateTimestamp());
        apiStockOrder.setCreatorId(entity.getCreatorId());
        apiStockOrder.setRepresentativeOfProducerUserCustomer(UserCustomerMapper.toApiUserCustomerBase(entity.getRepresentativeOfProducerUserCustomer()));
        apiStockOrder.setProducerUserCustomer(UserCustomerMapper.toApiUserCustomerBase(entity.getProducerUserCustomer()));
        apiStockOrder.setProductionLocation(StockOrderLocationMapper.toApiStockOrderLocation(entity.getProductionLocation()));

//        apiStockOrder.setCertifications(entity.getCertifications()
//                .stream()
//                .map(CertificationMapper::toApiCertification)
//                .collect(Collectors.toList()));

        if (!entity.getActivityProofs().isEmpty()) {
            apiStockOrder.setActivityProofs(entity.getActivityProofs().stream()
                    .map(ap -> ActivityProofMapper.toApiActivityProof(ap.getActivityProof(), userId))
                    .collect(Collectors.toList()));
        }

        if(entity.getSemiProduct() != null) {
            apiStockOrder.setSemiProduct(SemiProductMapper.toApiSemiProduct(entity.getSemiProduct()));
        }

        apiStockOrder.setFacility(FacilityMapper.toApiFacility(entity.getFacility()));
        apiStockOrder.setCompany(CompanyMapper.toApiCompanyBase(entity.getCompany()));
        apiStockOrder.setMeasureUnitType(MeasureUnitTypeMapper.toApiMeasureUnitType(entity.getMeasurementUnitType()));
        apiStockOrder.setTotalQuantity(entity.getTotalQuantity());
        apiStockOrder.setFulfilledQuantity(entity.getFulfilledQuantity());
        apiStockOrder.setAvailableQuantity(entity.getAvailableQuantity());
        apiStockOrder.setAvailable(entity.getAvailable());
        apiStockOrder.setProductionDate(entity.getProductionDate());
//        apiStockOrder.setExpiryDate(entity.getExpiryDate());
//        apiStockOrder.setEstimatedDeliveryDate(entity.getEstimatedDeliveryDate());
        apiStockOrder.setDeliveryTime(entity.getDeliveryTime());
//        apiStockOrder.setOrderId(entity.getOrderId());
//        apiStockOrder.setGlobalOrderId(entity.getGlobalOrderId());
        apiStockOrder.setPricePerUnit(entity.getPricePerUnit());
//        apiStockOrder.setSalesPricePerUnit(entity.getSalesPricePerUnit());
        apiStockOrder.setCurrency(entity.getCurrency());
//        apiStockOrder.setSalesCurrency(entity.getSalesCurrency());
        apiStockOrder.setPurchaseOrder(entity.getPurchaseOrder());
        apiStockOrder.setOrderType(entity.getOrderType());
        apiStockOrder.setInternalLotNumber(entity.getInternalLotNumber());
//        apiStockOrder.setLotNumber(entity.getLotNumber());
//        apiStockOrder.setLotLabel(entity.getLotLabel());
//        apiStockOrder.setScreenSize(entity.getScreenSize());
//        apiStockOrder.setComments(entity.getComments());
//        apiStockOrder.setActionType(ActionTypeMapper.toApiActionType(entity.getActionType()));
        apiStockOrder.setWomenShare(entity.getWomenShare());
        apiStockOrder.setCost(entity.getCost());
        apiStockOrder.setPaid(entity.getPaid());
        apiStockOrder.setBalance(entity.getBalance());
//        apiStockOrder.setStartOfDrying(entity.getStartOfDrying());
//        apiStockOrder.setClient(CompanyMapper.toApiCompany(entity.getClient()));
//        apiStockOrder.setFlavourProfile(entity.getFlavourProfile());
//        apiStockOrder.setProcessingAction(ProcessingActionMapper.toApiProcessingAction(entity.getProcessingAction()));
        apiStockOrder.setPreferredWayOfPayment(entity.getPreferredWayOfPayment());
//        apiStockOrder.setSacNumber(entity.getSacNumber());
//        apiStockOrder.setOpenOrder(entity.getOpenOrder());
//        apiStockOrder.setQuoteFacility(entity.getQuoteFacility());
//        apiStockOrder.setQuoteCompany(entity.getQuoteCompany());
//        apiStockOrder.setPricePerUnitForOwner(entity.getPricePerUnitForOwner());
//        apiStockOrder.setPricePerUnitForBuyer(entity.getPricePerUnitForBuyer());
//        apiStockOrder.setExchangeRateAtBuyer(entity.getExchangeRateAtBuyer());
//        apiStockOrder.setPricePerUnitForEndCustomer(entity.getPricePerUnitForEndCustomer());
//        apiStockOrder.setExchangeRateAtEndCustomer(entity.getExchangeRateAtEndCustomer());
//        apiStockOrder.setCuppingResult(entity.getCuppingResult());
//        apiStockOrder.setCuppingGrade(entity.getCuppingGrade());
//        apiStockOrder.setCuppingFlavour(entity.getCuppingFlavour());
//        apiStockOrder.setRoastingDate(entity.getRoastingDate());
//        apiStockOrder.setRoastingProfile(entity.getRoastingProfile());
//        apiStockOrder.setShipperDetails(entity.getShipperDetails());
//        apiStockOrder.setCarrierDetails(entity.getCarrierDetails());
//        apiStockOrder.setPortOfLoading(entity.getPortOfLoading());
//        apiStockOrder.setPortOfDischarge(entity.getPortOfDischarge());
//        apiStockOrder.setDateOfEndDelivery(entity.getDateOfEndDelivery());
//        apiStockOrder.setRequiredWomensCoffee(entity.getRequiredWomensCoffee());
//        apiStockOrder.setShippedAtDateFromOriginPort(entity.getShippedAtDateFromOriginPort());
//        apiStockOrder.setArrivedAtDateToDestinationPort(entity.getArrivedAtDateToDestinationPort());

        return apiStockOrder;
    }

}
