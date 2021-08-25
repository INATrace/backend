package com.abelium.inatrace.components.codebook.semiproduct;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.api.ApiPaginatedList;
import com.abelium.inatrace.api.ApiPaginatedRequest;
import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeService;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Service for SemiProduct entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Lazy
@Service
public class SemiProductService extends BaseService {

	private final MeasureUnitTypeService measureUnitTypeService;

	@Autowired
	public SemiProductService(MeasureUnitTypeService measureUnitTypeService) {
		this.measureUnitTypeService = measureUnitTypeService;
	}

	public ApiPaginatedList<ApiSemiProduct> getSemiProductList(ApiPaginatedRequest request) {

		return PaginationTools.createPaginatedResponse(em, request, () -> semiProductQueryObject(request),
				SemiProductMapper::toApiSemiProduct);
	}

	private SemiProduct semiProductQueryObject(ApiPaginatedRequest request) {

		SemiProduct semiProductProxy = Torpedo.from(SemiProduct.class);

		switch (request.sortBy) {
			case "name":
				QueryTools.orderBy(request.sort, semiProductProxy.getName());
				break;
			case "description":
				QueryTools.orderBy(request.sort, semiProductProxy.getDescription());
				break;
			case "measurementUnitType":
				QueryTools.orderBy(request.sort, semiProductProxy.getMeasurementUnitType().getLabel());
				break;
			default:
				QueryTools.orderBy(request.sort, semiProductProxy.getId());
		}

		return semiProductProxy;
	}

	public ApiSemiProduct getSemiProduct(Long id) throws ApiException {

		return SemiProductMapper.toApiSemiProduct(fetchSemiProduct(id));
	}

	@Transactional
	public ApiBaseEntity createOrUpdateSemiProduct(ApiSemiProduct apiSemiProduct) throws ApiException {

		SemiProduct semiProduct;

		if (apiSemiProduct.getId() != null) {
			semiProduct = fetchSemiProduct(apiSemiProduct.getId());
		} else {
			semiProduct = new SemiProduct();
		}

		semiProduct.setName(apiSemiProduct.getName());
		semiProduct.setDescription(apiSemiProduct.getDescription());
		semiProduct.setSKU(apiSemiProduct.getSKU());
		semiProduct.setBuyable(apiSemiProduct.getBuyable());
		semiProduct.setSKUEndCustomer(apiSemiProduct.getSKUEndCustomer());

		if (apiSemiProduct.getApiMeasureUnitType() != null &&
				apiSemiProduct.getApiMeasureUnitType().getId() != null &&
				(semiProduct.getMeasurementUnitType() == null ||
						!Objects.equals(semiProduct.getMeasurementUnitType().getId(),
								apiSemiProduct.getApiMeasureUnitType().getId()))) {
			MeasureUnitType measureUnitType = measureUnitTypeService.fetchMeasureUnitType(apiSemiProduct.getId());
			semiProduct.setMeasurementUnitType(measureUnitType);
		} else if (apiSemiProduct.getApiMeasureUnitType() == null || apiSemiProduct.getApiMeasureUnitType().getId() == null) {
			semiProduct.setMeasurementUnitType(null);
		}

		if (semiProduct.getId() == null) {
			em.persist(semiProduct);
		}

		return new ApiBaseEntity(semiProduct);
	}

	@Transactional
	public void deleteSemiProduct(Long id) throws ApiException {

		SemiProduct semiProduct = fetchSemiProduct(id);
		em.remove(semiProduct);
	}

	private SemiProduct fetchSemiProduct(Long id) throws ApiException {

		SemiProduct semiProduct = Queries.get(em, SemiProduct.class, id);
		if (semiProduct == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid semi-product ID");
		}

		return semiProduct;
	}

}
