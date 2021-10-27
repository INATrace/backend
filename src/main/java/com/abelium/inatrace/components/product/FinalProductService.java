package com.abelium.inatrace.components.product;

import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.product.api.ApiFinalProduct;
import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.product.FinalProduct;
import com.abelium.inatrace.db.entities.product.Product;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Final product entity.
 */
@Lazy
@Service
public class FinalProductService extends BaseService {

	/**
	 * Get a paginated list of Final prooducts for a provided company (the Final products that this company has access to).
	 *
	 * @param request Pagination requets.
	 * @param companyId Company ID.
	 * @return List of Final products.
	 */
	public ApiPaginatedList<ApiFinalProduct> getFinalProductsForCompany(ApiPaginatedRequest request, Long companyId) {

		// Fetch the prodcuts that the company with the provded ID is in any role in the value chain
		TypedQuery<Product> companyProductsQuery = em.createNamedQuery("ProductCompany.getCompanyProductsWithAnyRole",
				Product.class).setParameter("companyId", companyId);
		List<Long> companyProductsIds = companyProductsQuery.getResultList().stream().map(BaseEntity::getId)
				.collect(Collectors.toList());

		if (companyProductsIds.isEmpty()) {
			return new ApiPaginatedList<>(Collections.emptyList(), 0);
		}

		// Fetch the final prodcuts for the products IDs of the provided company
		List<ApiFinalProduct> finalProducts = em.createNamedQuery("FinalProduct.getFinalProductsForProductsIds", FinalProduct.class)
				.setParameter("productsIds", companyProductsIds)
				.setFirstResult(request.getOffset())
				.setMaxResults(request.getLimit())
				.getResultList().stream()
				.map(ProductApiTools::toApiFinalProduct)
				.collect(Collectors.toList());

		Long count = em.createNamedQuery("FinalProduct.countFinalProductsForProductsIds", Long.class)
				.setParameter("productsIds", companyProductsIds)
				.getSingleResult();

		return new ApiPaginatedList<>(finalProducts, count);
	}

	/**
	 * Fetch Final product with the provided ID.
	 */
	public FinalProduct fetchFinalProduct(Long id) throws ApiException {

		FinalProduct finalProduct = em.find(FinalProduct.class, id);
		if (finalProduct == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid Final product ID");
		}

		return finalProduct;
	}

}