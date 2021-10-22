package com.abelium.inatrace.components.productorder;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.productorder.api.ApiProductOrder;
import com.abelium.inatrace.components.productorder.mappers.ProductOrderMapper;
import com.abelium.inatrace.db.entities.productorder.ProductOrder;
import com.abelium.inatrace.types.Language;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Service for Product order entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Service
@Lazy
public class ProductOrderService extends BaseService {

	public ApiProductOrder getProductOrder(Long id, Language language) throws ApiException {
		return ProductOrderMapper.toApiProductOrder(fetchProductOrder(id), language);
	}

	public ApiBaseEntity createOrUpdateProductOrder(ApiProductOrder apiProductOrder) {

		// TODO: implement

		return null;
	}

	private ProductOrder fetchProductOrder(Long id) throws ApiException {

		ProductOrder productOrder = em.find(ProductOrder.class, id);
		if (productOrder == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid Product order ID");
		}

		return productOrder;
	}

}
