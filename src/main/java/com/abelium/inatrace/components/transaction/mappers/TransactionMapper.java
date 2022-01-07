package com.abelium.inatrace.components.transaction.mappers;

import com.abelium.inatrace.components.codebook.grade_abbreviation.GradeAbbreviationMapper;
import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductMapper;
import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.facility.FacilityMapper;
import com.abelium.inatrace.components.product.ProductApiTools;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.types.Language;

public class TransactionMapper {

    public static ApiTransaction toApiTransactionBase(Transaction entity) {

        if (entity == null) {
            return null;
        }

        ApiTransaction apiTransaction = new ApiTransaction();
        apiTransaction.setId(entity.getId());
        apiTransaction.setInitiationUserId(entity.getInitiationUserId());
        apiTransaction.setIsProcessing(entity.getIsProcessing());
        apiTransaction.setInputQuantity(entity.getInputQuantity());
        apiTransaction.setOutputQuantity(entity.getOutputQuantity());
        apiTransaction.setStatus(entity.getStatus());
        apiTransaction.setRejectComment(entity.getRejectComment());
        apiTransaction.setSourceStockOrder(StockOrderMapper.toApiStockOrderBase(entity.getSourceStockOrder()));
        apiTransaction.setInputMeasureUnitType(MeasureUnitTypeMapper.toApiMeasureUnitTypeBase(entity.getInputMeasureUnitType()));

        return apiTransaction;
    }

    public static ApiTransaction toApiTransaction(Transaction entity, Language language) {

        ApiTransaction apiTransaction = toApiTransactionBase(entity);

        if (apiTransaction == null) {
            return null;
        }

        apiTransaction.setCompany(CompanyMapper.toApiCompanyBase(entity.getCompany()));
        apiTransaction.setTargetStockOrder(StockOrderMapper.toApiStockOrder(entity.getTargetStockOrder(), null, language));
        apiTransaction.setSemiProduct(SemiProductMapper.toApiSemiProduct(entity.getSemiProduct(), language));
        apiTransaction.setFinalProduct(ProductApiTools.toApiFinalProduct(entity.getFinalProduct()));
        apiTransaction.setSourceFacility(FacilityMapper.toApiFacilityBase(entity.getSourceFacility(), language));
        apiTransaction.setTargetFacility(FacilityMapper.toApiFacilityBase(entity.getTargetFacility(), language));
        apiTransaction.setShipmentId(entity.getShipmentId());
        apiTransaction.setOutputMeasureUnitType(MeasureUnitTypeMapper.toApiMeasureUnitType(entity.getOutputMeasureUnitType()));
        apiTransaction.setPricePerUnit(entity.getPricePerUnit());
        apiTransaction.setCurrency(entity.getCurrency());
        apiTransaction.setGradeAbbreviation(GradeAbbreviationMapper.toApiGradeAbbreviation(entity.getGradeAbbreviation()));

        return apiTransaction;
    }

    /**
     * Mapping method used for input and output transactions when fetching Stock order history
     * @param entity The Transaction entity.
     * @return API model for Transaction entity.
     */
    public static ApiTransaction toApiTransactionHistory(Transaction entity) {

        if (entity == null) {
            return null;
        }

        ApiTransaction apiTransaction = new ApiTransaction();
        apiTransaction.setId(entity.getId());
        apiTransaction.setStatus(entity.getStatus());
        apiTransaction.setInputQuantity(entity.getInputQuantity());
        apiTransaction.setRejectComment(entity.getRejectComment());
        apiTransaction.setInputMeasureUnitType(MeasureUnitTypeMapper.toApiMeasureUnitTypeBase(entity.getInputMeasureUnitType()));

        apiTransaction.setSourceStockOrder(new ApiStockOrder());
        apiTransaction.getSourceStockOrder().setId(entity.getSourceStockOrder().getId());

        return apiTransaction;
    }

}
