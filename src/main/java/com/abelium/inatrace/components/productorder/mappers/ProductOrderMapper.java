package com.abelium.inatrace.components.productorder.mappers;

import com.abelium.inatrace.components.company.mappers.CompanyCustomerMapper;
import com.abelium.inatrace.components.facility.FacilityMapper;
import com.abelium.inatrace.components.productorder.api.ApiProductOrder;
import com.abelium.inatrace.components.stockorder.mappers.StockOrderMapper;
import com.abelium.inatrace.db.entities.productorder.ProductOrder;
import com.abelium.inatrace.types.Language;

import java.util.stream.Collectors;

/**
 * Mapper for ProductOrder entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class ProductOrderMapper {

	private ProductOrderMapper() {
		throw new IllegalStateException("Utility class");
	}

	public static ApiProductOrder toApiProductOrder(ProductOrder entity, Language language) {

		if (entity == null) {
			return null;
		}

		ApiProductOrder apiProductOrder = new ApiProductOrder();
		apiProductOrder.setId(entity.getId());
		apiProductOrder.setOrderId(entity.getOrderId());
		apiProductOrder.setUpdateTimestamp(entity.getUpdateTimestamp());
		apiProductOrder.setDeliveryDeadline(entity.getDeliveryDeadline());
		apiProductOrder.setRequiredWomensOnly(entity.getRequiredWomensOnly());
		apiProductOrder.setRequiredOrganic(entity.getRequiredOrganic());
		apiProductOrder.setFacility(FacilityMapper.toApiFacilityBase(entity.getFacility(), language));
		apiProductOrder.setCustomer(CompanyCustomerMapper.toApiCompanyCustomerBase(entity.getCustomer()));

		// Map the ordered items contained in this order
		apiProductOrder.setItems(
				entity.getItems().stream().map(StockOrderMapper::toApiStockOrderBase)
						.collect(Collectors.toList()));

		return apiProductOrder;
	}

}
