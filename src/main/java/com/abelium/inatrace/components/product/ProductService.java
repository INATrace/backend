package com.abelium.inatrace.components.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.abelium.inatrace.api.*;
import com.abelium.inatrace.types.ProductCompanyType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.OnGoingLogicalCondition;
import org.torpedoquery.jpa.Torpedo;

import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.analytics.AnalyticsEngine;
import com.abelium.inatrace.components.analytics.RequestLogService;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.common.StorageKeyCache;
import com.abelium.inatrace.components.company.api.ApiCompanyCustomer;
import com.abelium.inatrace.components.product.api.ApiKnowledgeBlog;
import com.abelium.inatrace.components.product.api.ApiKnowledgeBlogBase;
import com.abelium.inatrace.components.product.api.ApiListCustomersRequest;
import com.abelium.inatrace.components.product.api.ApiListKnowledgeBlogRequest;
import com.abelium.inatrace.components.product.api.ApiListProductLabelBatchesRequest;
import com.abelium.inatrace.components.product.api.ApiListProductLabelFeedbackRequest;
import com.abelium.inatrace.components.product.api.ApiListProductsRequest;
import com.abelium.inatrace.components.product.api.ApiLocation;
import com.abelium.inatrace.components.product.api.ApiProduct;
import com.abelium.inatrace.components.product.api.ApiProductLabel;
import com.abelium.inatrace.components.product.api.ApiProductLabelAnalytics;
import com.abelium.inatrace.components.product.api.ApiProductLabelBase;
import com.abelium.inatrace.components.product.api.ApiProductLabelBatch;
import com.abelium.inatrace.components.product.api.ApiProductLabelBatchCheckAuthenticity;
import com.abelium.inatrace.components.product.api.ApiProductLabelBatchCheckOrigin;
import com.abelium.inatrace.components.product.api.ApiProductLabelContent;
import com.abelium.inatrace.components.product.api.ApiProductLabelFeedback;
import com.abelium.inatrace.components.product.api.ApiProductLabelValues;
import com.abelium.inatrace.components.product.api.ApiProductLabelValuesExtended;
import com.abelium.inatrace.components.product.api.ApiProductLabelUpdateValues;
import com.abelium.inatrace.components.product.api.ApiProductListResponse;
import com.abelium.inatrace.components.product.types.ProductLabelAction;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.db.entities.product.ComparisonOfPrice;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.product.KnowledgeBlog;
import com.abelium.inatrace.db.entities.product.Product;
import com.abelium.inatrace.db.entities.product.ProductCompany;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyCustomer;
import com.abelium.inatrace.db.entities.product.ProductLabel;
import com.abelium.inatrace.db.entities.product.ProductLabelBatch;
import com.abelium.inatrace.db.entities.product.ProductLabelContent;
import com.abelium.inatrace.db.entities.product.ProductLabelFeedback;
import com.abelium.inatrace.db.entities.product.ProductSettings;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.tools.PaginationTools;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.tools.QueryTools;
import com.abelium.inatrace.tools.TorpedoProjector;
import com.abelium.inatrace.types.ProductLabelStatus;
import com.abelium.inatrace.types.RequestLogType;
import com.abelium.inatrace.types.UserRole;


@Lazy
@Service
public class ProductService extends BaseService {
	
	@Autowired
	private ProductApiTools productApiTools;

	@Autowired
	private ProductQueries productQueries;
	
	@Autowired
	private RequestLogService requestLogEngine;
	
	@Autowired
	private AnalyticsEngine analyticsEngine;

	@Autowired
	private ProductMapper productMapper;
	
    private Product productListQueryObject(ApiListProductsRequest request) {
        Product pProxy = Torpedo.from(Product.class);
        
        OnGoingLogicalCondition condition = Torpedo.condition();        
        if (StringUtils.isNotBlank(request.name)) {
            condition = condition.and(pProxy.getName()).like().any(request.name);
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "name": QueryTools.orderBy(request.sort, pProxy.getName()); break;
	        default: QueryTools.orderBy(request.sort, pProxy.getId());
        }
        return pProxy;
    }	

    @Transactional
	public ApiPaginatedList<ApiProductListResponse> listProducts(Long userId, ApiListProductsRequest request) {
    	return PaginationTools.createPaginatedResponse(em, request, () -> productListQueryObject(request), 
    			p -> ProductApiTools.toApiProductListResponse(userId, p)); 
	}
    
    private TorpedoProjector<Product, ApiProductListResponse> userProductListQueryObject1(Long userId, ApiListProductsRequest request) {
        CompanyUser cuProxy = Torpedo.from(CompanyUser.class);
        Torpedo.where(cuProxy.getUser().getId()).eq(userId);
        List<Long> companyIds = Torpedo.select(cuProxy.getCompany().getId()).list(em);
        
        ProductCompany pcProxy = Torpedo.from(ProductCompany.class);
        Torpedo.where(pcProxy.getCompany().getId()).in(companyIds);
        List<Long> productIds = Torpedo.select(pcProxy.getProduct().getId()).list(em);
        
        Product pProxy = Torpedo.from(Product.class);
        OnGoingLogicalCondition condition = Torpedo.condition(pProxy.getCompany().getId()).in(companyIds).or(pProxy.getId()).in(productIds);
        if (StringUtils.isNotBlank(request.name)) {
            condition = condition.and(pProxy.getName()).like().any(request.name);
        }
        Document dProxy = Torpedo.leftJoin(pProxy.getPhoto());        
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "name": QueryTools.orderBy(request.sort, pProxy.getName()); break;
	        default: QueryTools.orderBy(request.sort, pProxy.getId());
        }
        return new TorpedoProjector<>(pProxy, ApiProductListResponse.class).
			add(pProxy.getId(), ApiProductListResponse::setId).
			add(pProxy.getName(), ApiProductListResponse::setName).
			add(dProxy.getStorageKey(), (r, s) -> r.setPhotoStorageId(StorageKeyCache.put(s, userId)));
    }
    
    
    @Transactional
	public ApiPaginatedList<ApiProductListResponse> listUserProducts1(Long userId, ApiListProductsRequest request) {
    	return PaginationTools.createPaginatedResponse(em, request, () -> userProductListQueryObject1(userId, request)); 
	}

    @Transactional
	public ApiBaseEntity createProduct(Long userId, ApiProduct request) throws ApiException {
		Product product = new Product();
		
		productApiTools.updateProduct(userId, product, request);

		if (product.getAssociatedCompanies().stream().noneMatch(productCompany -> productCompany.getType() == ProductCompanyType.OWNER)) {
			ProductCompany productCompany = new ProductCompany();
			if (request.getCompany() == null || request.getCompany().getId() == null) {
				throw new ApiException(ApiStatus.INVALID_REQUEST, "Company ID is required");
			}
			productCompany.setCompany(em.find(Company.class, request.getCompany().getId()));
			productCompany.setProduct(product);
			productCompany.setType(ProductCompanyType.OWNER);
			product.getAssociatedCompanies().add(productCompany);
		}

		em.persist(product.getProcess());
		em.persist(product.getResponsibility());
		em.persist(product.getSustainability());
		em.persist(product.getSettings());
		em.persist(product.getComparisonOfPrice());
		em.persist(product);
		return new ApiBaseEntity(product);
	}

    @Transactional
	public ApiProduct getProduct(CustomUserDetails authUser, long id, boolean includeLabels) throws ApiException {
		Product p = fetchProductAssoc(authUser, id);
		ApiProduct ap = productApiTools.toApiProduct(authUser.getUserId(), p);
		if (includeLabels) productApiTools.updateProductWithLabels(authUser.getUserId(), ap, p);
		return ap;
	}

    @Transactional
	public void updateProduct(CustomUserDetails authUser, ApiProduct ap) throws ApiException {
		Product p = fetchProduct(authUser, ap.id);
		if (p.getSettings() == null) {
			p.setSettings(new ProductSettings());
			em.persist(p.getSettings());
		}
		if (p.getComparisonOfPrice() == null) {
			p.setComparisonOfPrice(new ComparisonOfPrice());
			em.persist(p.getComparisonOfPrice());
		}
		productApiTools.updateProduct(authUser.getUserId(), p, ap);
	}

    @Transactional
	public ApiProductLabel getProductLabel(CustomUserDetails authUser, Long id) throws ApiException {
		ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, id);
		return ProductApiTools.toApiProductLabel(pl);
	}

    @Transactional
	public ApiProductLabelValues getProductLabelValues(CustomUserDetails authUser, Long id) throws ApiException {
		ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, id);
		return productApiTools.toApiProductLabelValues(authUser.getUserId(), pl);
	}
    
    @Transactional
	public ApiProductLabelContent getProductLabelContent(CustomUserDetails authUser, Long id) throws ApiException {
		ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, id);
		return productApiTools.toApiProductLabelContent(authUser.getUserId(), pl.getContent());
	}
    
    
    @Transactional
	public ApiProductLabelValuesExtended getProductLabelValuesPublic(String uid) throws ApiException {
		ProductLabel pl = fetchProductLabelPublic(uid);
		ApiProductLabelValuesExtended aplx = new ApiProductLabelValuesExtended();
		
		productApiTools.updateApiProductLabelValues(null, pl, aplx);
		
		aplx.numberOfBatches = Queries.getCountBy(em, ProductLabelBatch.class, ProductLabelBatch::getLabel, pl);
		aplx.checkAuthenticityCount = countBatchFields(pl, ProductLabelBatch::getCheckAuthenticity, true);
		aplx.traceOriginCount = countBatchFields(pl, ProductLabelBatch::getTraceOrigin, true);
		return aplx;
	}    
    
    private <P> int countBatchFields(ProductLabel pl, Function<ProductLabelBatch, P> property, P value) {
		ProductLabelBatch plbProxy = Torpedo.from(ProductLabelBatch.class);
        Torpedo.where(plbProxy.getLabel()).eq(pl).and(property.apply(plbProxy)).eq(value);
        Optional<Long> result = Torpedo.select(Torpedo.count(plbProxy)).get(em);
        return result.isPresent() ? result.get().intValue() : 0;
    }
    
	public ApiProductLabelAnalytics getProductLabelAnalytics(CustomUserDetails authUser, String uid) throws ApiException {
		productQueries.checkProductLabelPermissionAssoc(authUser, uid);
		return analyticsEngine.createAnalyticsForLabel(uid);
	}

    
    @Transactional
	public ApiBaseEntity createProductLabel(CustomUserDetails authUser, ApiProductLabel request) throws ApiException {
    	Product p = fetchProductAssoc(authUser, request.productId);
    	
    	ProductLabelContent plc = ProductLabelContent.fromProduct(p);
    	em.persist(plc.getProcess());
    	em.persist(plc.getResponsibility());
    	em.persist(plc.getSustainability());
    	em.persist(plc.getSettings());
    	em.persist(plc.getComparisonOfPrice());
    	em.persist(plc);
    	
		ProductLabel pl = new ProductLabel();
		pl.setContent(plc);
		pl.setProduct(p);
		ProductApiTools.updateProductLabel(pl, request);
		em.persist(pl);
		return new ApiBaseEntity(pl.getId());
	}
    
    @Transactional
	public ApiDefaultResponse updateProductLabel(CustomUserDetails authUser, ApiProductLabel request) throws ApiException {
		ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, request.id);
		ProductApiTools.updateProductLabel(pl, request);
		return new ApiDefaultResponse();
	}    

    @Transactional
	public ApiDefaultResponse updateProductLabelValues(CustomUserDetails authUser, ApiProductLabelUpdateValues request) throws ApiException {
		ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, request.id);
		productApiTools.updateProductLabelFields(authUser.getUserId(), pl, request);
		return new ApiDefaultResponse();
	}
    
    @Transactional
	public ApiDefaultResponse updateProductLabelContent(CustomUserDetails authUser, ApiProductLabelContent request) throws ApiException {
		ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, request.labelId);
		ProductLabelContent plc = pl.getContent();
		productApiTools.updateProductLabelContent(authUser.getUserId(), plc, request);
		return new ApiDefaultResponse();
	}    

    @Transactional
	public List<ApiProductLabelBase> getProductLabels(CustomUserDetails authUser, Long id) throws ApiException {
    	checkProductPermissionAssoc(authUser, id);
    	
		ProductLabel plProxy = Torpedo.from(ProductLabel.class);
		Torpedo.where(plProxy.getProduct().getId()).eq(id);
		List<ProductLabel> labels = Torpedo.select(plProxy).list(em);
		List<ApiProductLabelBase> result = new ArrayList<>(labels.size());
		for (ProductLabel pl : labels) {
			result.add(ProductApiTools.toApiProductLabelBase(pl));
		}
		return result;
	}
    
    @Transactional
	public void deleteProductLabel(CustomUserDetails authUser, Long id) throws ApiException {
		ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, id);

		removeProductLabelBatches(Arrays.asList(pl.getId()));
    	em.remove(pl.getContent().getProcess());
    	em.remove(pl.getContent().getResponsibility());
    	em.remove(pl.getContent().getSustainability());
    	em.remove(pl.getContent().getSettings());
    	em.remove(pl.getContent().getComparisonOfPrice());
		em.remove(pl.getContent());
		em.remove(pl);
	}

    @Transactional
	public void updateCompanyCustomer(CustomUserDetails authUser, ApiCompanyCustomer request) throws ApiException {
		CompanyCustomer pc = fetchCompanyCustomer(authUser, request.id);
		productApiTools.updateCompanyCustomer(pc, request);
	}

    @Transactional
	public ApiBaseEntity addCompanyCustomer(CustomUserDetails authUser, Long productId, Long companyId, ApiCompanyCustomer request) throws ApiException {
		Product p = fetchProduct(authUser, productId);
		List<Long> userCompanyIds = userCompanies(authUser, productId);

		if (!userCompanyIds.contains(companyId)) 
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid company id");
		
		CompanyCustomer pc = new CompanyCustomer();
		pc.setProduct(p);
		pc.setCompany(Queries.get(em, Company.class, companyId));
		productApiTools.updateCompanyCustomer(pc, request);
		em.persist(pc);
		return new ApiBaseEntity(pc);
	}

    @Transactional
	public void deleteCompanyCustomer(CustomUserDetails authUser, Long id) throws ApiException {
		CompanyCustomer pc = fetchCompanyCustomer(authUser, id);
		em.remove(pc);
	}
    
    @Transactional
	public void deleteProductLabelFeedback(CustomUserDetails authUser, Long id) throws ApiException {
		ProductLabelFeedback fb = Queries.get(em, ProductLabelFeedback.class, id);
		if (fb == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid feedback id");
		}
		productQueries.checkProductLabelPermissionAssoc(authUser, fb.getLabel().getId());
		em.remove(fb);
	}
    
    @Transactional
	public void deleteProduct(CustomUserDetails authUser, Long id) throws ApiException {
		Product p = fetchProduct(authUser, id);
		
		ProductLabel plProxy = Torpedo.from(ProductLabel.class);
		Torpedo.where(plProxy.getProduct()).eq(p);
		List<ProductLabel> labels = Torpedo.select(plProxy).list(em);

		removeProductLabelBatches(labels.stream().map(ProductLabel::getId).collect(Collectors.toList()));
		labels.forEach(pl -> em.remove(pl));
		em.remove(p);
	}

    private void removeProductLabelBatches(List<Long> labelIds) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<ProductLabelBatch> cd = cb.createCriteriaDelete(ProductLabelBatch.class);
		Root<ProductLabelBatch> root = cd.from(ProductLabelBatch.class);
		cd.where(root.get("label").get("id").in(labelIds));
		em.createQuery(cd).executeUpdate();
    }

    @Transactional
	public void executeAction(CustomUserDetails authUser, ApiBaseEntity request, ProductLabelAction action) throws ApiException {
    	ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, request.id);
    	switch (action) {
    		case PUBLISH_LABEL: pl.setStatus(ProductLabelStatus.PUBLISHED); break;
    		case UNPUBLISH_LABEL: pl.setStatus(ProductLabelStatus.UNPUBLISHED); break;
    		default: throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid action");
    	}
	}

    @Transactional
	public ApiProductLabelBatch getProductLabelBatch(CustomUserDetails authUser, Long id) throws ApiException {
		ProductLabelBatch plb = fetchProductLabelBatch(authUser, id);
		return ProductApiTools.toApiProductLabelBatch(authUser.getUserId(), plb);
	}
    	
    @Transactional
	public ApiBaseEntity createProductLabelBatch(CustomUserDetails authUser, ApiProductLabelBatch request) throws ApiException {
    	ProductLabel pl = productQueries.fetchProductLabelAssoc(authUser, request.labelId);
		ProductLabelBatch plb = new ProductLabelBatch();
		plb.setLabel(pl);
		productApiTools.updateProductLabelBatch(authUser.getUserId(), plb, request);
		em.persist(plb);
		return new ApiBaseEntity(plb);
	}
    
    @Transactional
	public ApiDefaultResponse updateProductLabelBatch(CustomUserDetails authUser, ApiProductLabelBatch request) throws ApiException {
		ProductLabelBatch plb = fetchProductLabelBatch(authUser, request.id);
		productApiTools.updateProductLabelBatch(authUser.getUserId(), plb, request);
		return new ApiDefaultResponse();
	}    

    private ProductLabelBatch productLabelBatchListQueryObject(Long labelId, ApiListProductLabelBatchesRequest request) {
		ProductLabelBatch plbProxy = Torpedo.from(ProductLabelBatch.class);
        OnGoingLogicalCondition condition = Torpedo.condition();

        condition = condition.and(plbProxy.getLabel().getId()).eq(labelId);
        if (StringUtils.isNotBlank(request.number)) {
            condition = condition.and(plbProxy.getNumber()).like().startsWith(request.number);
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "number": QueryTools.orderBy(request.sort, plbProxy.getNumber()); break;
	        case "productionDate": QueryTools.orderBy(request.sort, plbProxy.getProductionDate()); break;
	        case "expiryDate": QueryTools.orderBy(request.sort, plbProxy.getExpiryDate()); break;
	        default: QueryTools.orderBy(request.sort, plbProxy.getId());
        }
        return plbProxy;
    }    

    @Transactional
    public ApiPaginatedList<ApiProductLabelBatch> listProductLabelBatches(CustomUserDetails authUser, Long labelId, ApiListProductLabelBatchesRequest request) throws ApiException {
    	productQueries.checkProductLabelPermission(authUser, labelId);
    	return PaginationTools.createPaginatedResponse(em, request, () -> productLabelBatchListQueryObject(labelId, request), 
    			plb -> ProductApiTools.toApiProductLabelBatch(authUser.getUserId(), plb)); 
	}
    
    @Transactional
	public void deleteProductLabelBatch(CustomUserDetails authUser, Long id) throws ApiException {
		ProductLabelBatch plb = fetchProductLabelBatch(authUser, id);
		em.remove(plb);
	}

    @Transactional
	public ApiProductLabelBatch getProductLabelBatchPublic(String uid, String number) {
    	ProductLabelBatch plbProxy = Torpedo.from(ProductLabelBatch.class);
    	Torpedo.where(plbProxy.getLabel().getUuid()).eq(uid).
    		and(plbProxy.getNumber()).eq(number);
    	Optional<ProductLabelBatch> plb = Torpedo.select(plbProxy).get(em);
    	return plb.isPresent() ? ProductApiTools.toApiProductLabelBatch(null, plb.get()) : null;
	}

    @Transactional
	public boolean checkProductLabelBatchAuthenticityPublic(String labelUid, ApiProductLabelBatchCheckAuthenticity request, HttpServletRequest servletRequest) {
    	boolean response = false;
    	ApiProductLabelBatch b = getProductLabelBatchPublic(labelUid, request.number);
    	
    	if (b != null) {
    		if (request.productionDate != null && request.expiryDate != null) {
    			response = request.productionDate.equals(b.productionDate) && request.expiryDate.equals(b.expiryDate);
    		} else if (request.productionDate != null) {
    			response = request.productionDate.equals(b.productionDate);
    		} else if (request.expiryDate != null) {
    			response = request.expiryDate.equals(b.expiryDate);
    		}
    	}
    	requestLogEngine.log(servletRequest, RequestLogType.VERIFY_BATCH, labelUid, request, response);
    	return response;
	}

    @Transactional
	public List<ApiLocation> checkProductLabelBatchOriginPublic(String labelUid, ApiProductLabelBatchCheckOrigin request, HttpServletRequest servletRequest) {
    	ApiProductLabelBatch b = getProductLabelBatchPublic(labelUid, request.number);
    	List<ApiLocation> response;
    	
    	if (b != null) response = b.getLocations();
    	else response = List.of();
    	
    	requestLogEngine.log(servletRequest, RequestLogType.VERIFY_BATCH_ORIGIN, labelUid, request, b != null);
    	return response;
	}

    private KnowledgeBlog knowledgeBlogListQueryObject(Long productId, ApiListKnowledgeBlogRequest request) {
    	KnowledgeBlog kbProxy = Torpedo.from(KnowledgeBlog.class);
        
        OnGoingLogicalCondition condition = Torpedo.condition();
        condition = condition.and(kbProxy.getProduct().getId()).eq(productId); 
        if (request.type != null) {
        	condition = condition.and(kbProxy.getType()).eq(request.type);
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "type": QueryTools.orderBy(request.sort, kbProxy.getType()); break;
	        default: QueryTools.orderBy(request.sort, kbProxy.getId());
        }
        return kbProxy;
    }	
    
	@Transactional
	public ApiPaginatedList<ApiKnowledgeBlogBase> listKnowledgeBlogs(CustomUserDetails authUser, Long productId, ApiListKnowledgeBlogRequest request) throws ApiException {
		checkProductPermissionAssoc(authUser, productId);
    	return PaginationTools.createPaginatedResponse(em, request, () -> knowledgeBlogListQueryObject(productId, request), 
    			ProductApiTools::toApiKnowledgeBlogBase); 
	}

    @Transactional
	public void updateKnowledgeBlog(CustomUserDetails authUser, ApiKnowledgeBlog request) throws ApiException {
    	KnowledgeBlog kb = fetchKnowledgeBlogAssoc(authUser, request.id);
    	productApiTools.updateKnowledgeBlog(authUser.getUserId(), kb, request);
	}

    @Transactional
	public ApiKnowledgeBlog getKnowledgeBlog(CustomUserDetails authUser, Long id) throws ApiException {
    	KnowledgeBlog kb = fetchKnowledgeBlogAssoc(authUser, id);
    	return productApiTools.toApiKnowledgeBlog(authUser.getUserId(), kb);
	}

    @Transactional
	public ApiKnowledgeBlog getKnowledgeBlogPublic(Long id) throws ApiException {
    	KnowledgeBlog kb = Queries.get(em, KnowledgeBlog.class, id);
    	
		if (kb == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id");
		}		    	
    	return productApiTools.toApiKnowledgeBlog(null, kb);
	}
    
    @Transactional
	public void addKnowledgeBlog(CustomUserDetails authUser, Long productId, ApiKnowledgeBlog request) throws ApiException {
    	Product p = fetchProductAssoc(authUser, productId);
    	
    	KnowledgeBlog kb = new KnowledgeBlog();
    	kb.setType(request.type);
    	kb.setProduct(p);
    	productApiTools.updateKnowledgeBlog(authUser.getUserId(), kb, request);
    	em.persist(kb);
	}
    
    private CompanyCustomer customerListQueryObject(Long userId, Long productId, ApiListCustomersRequest request) {
    	CompanyCustomer pcProxy = Torpedo.from(CompanyCustomer.class);
        
        OnGoingLogicalCondition condition = Torpedo.condition(); // .Torpedo conditions = new ArrayList<>();
        condition = condition.and(pcProxy.getProduct().getId()).eq(productId); 
        if (StringUtils.isNotBlank(request.query)) {
        	OnGoingLogicalCondition queryCondition = 
    				Torpedo.condition(pcProxy.getName()).like().any(request.query).
        				   or(pcProxy.getOfficialCompanyName()).like().any(request.query);
        	condition = condition.and(queryCondition);
        }
        if (request.phone != null) {
            condition = condition.and(Torpedo.condition(pcProxy.getPhone()).like().startsWith(request.phone));
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "name": QueryTools.orderBy(request.sort, pcProxy.getName()); break;
	        case "officialCompanyName": QueryTools.orderBy(request.sort, pcProxy.getOfficialCompanyName()); break;
	        case "phone": QueryTools.orderBy(request.sort, pcProxy.getPhone()); break;
	        default: QueryTools.orderBy(request.sort, pcProxy.getName());
        }
        return pcProxy;
    }

    @Transactional
	public ApiPaginatedList<ApiCompanyCustomer> listCompanyCustomers(CustomUserDetails authUser, Long productId, ApiListCustomersRequest request) throws ApiException {
    	checkProductPermission(authUser, productId);
    	return PaginationTools.createPaginatedResponse(em, request, () -> customerListQueryObject(authUser.getUserId(), productId, request), 
    			ProductApiTools::toApiCompanyCustomer); 
	}        
    
    private ProductLabelFeedback feedbackListQueryObject(String labelUid, ApiListProductLabelFeedbackRequest request) {
    	ProductLabelFeedback plfProxy = Torpedo.from(ProductLabelFeedback.class);
        
        OnGoingLogicalCondition condition = Torpedo.condition(); // .Torpedo conditions = new ArrayList<>();
        condition = condition.and(plfProxy.getLabel().getUuid()).eq(labelUid); 
        if (request.type != null) {
        	condition = condition.and(plfProxy.getType()).eq(request.type);
        }
        Torpedo.where(condition);
        switch (request.sortBy) {
	        case "type": QueryTools.orderBy(request.sort, plfProxy.getType()); break;
	        default: QueryTools.orderBy(request.sort, plfProxy.getId());
        }
        return plfProxy;
    }	
    
    @Transactional
	public ApiPaginatedList<ApiProductLabelFeedback> listProductLabelFeedbacks(String labelUid, ApiListProductLabelFeedbackRequest request) throws ApiException {
    	return PaginationTools.createPaginatedResponse(em, request, () -> feedbackListQueryObject(labelUid, request), 
    			ProductApiTools::toApiProductLabelFeedback); 
	}
    
    @Transactional
	public void addProductLabelFeedback(String labelUid, ApiProductLabelFeedback request) throws ApiException {
    	ProductLabel pl = fetchProductLabelPublic(labelUid);
    	ProductLabelFeedback fb = new ProductLabelFeedback();
    	fb.setLabel(pl);
    	ProductApiTools.updateProductLabelFeedback(fb, request);
    	em.persist(fb);
	}
    
    private void checkProductPermission(CustomUserDetails authUser, Long productId) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return;
    
    	Number count = em.createQuery("SELECT count(*) FROM Product p INNER JOIN CompanyUser cu ON cu.company.id = p.company.id "
    			+ "WHERE cu.user.id = :userId AND p.id = :productId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("productId", productId).
    		getSingleResult();
    	if (count.longValue() == 0L) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid product id or forbidden");
		}		
    }
    
	@SuppressWarnings("unchecked")
    public List<Long> userCompanies(CustomUserDetails authUser, Long productId) throws ApiException {
		// if (authUser.getUserRole() == UserRole.ADMIN) return Arrays.asList(); 
    
		List<Long> companyIds = (List<Long>) em.createQuery("SELECT cu.company.id FROM Product p INNER JOIN CompanyUser cu ON cu.company.id = p.company.id "
    			+ "WHERE cu.user.id = :userId AND p.id = :productId").
    		setParameter("userId", authUser.getUserId()).
    		setParameter("productId", productId).
    		getResultList();
		return companyIds;
    }    
    
    private void checkProductPermissionAssoc(CustomUserDetails authUser, Long productId) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return; 
    
    	Number count = em.createQuery("SELECT count(*) FROM Product p INNER JOIN CompanyUser cu ON cu.company.id = p.company.id "
    			+ "WHERE cu.user.id = :userId AND p.id = :productId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("productId", productId).
    		getSingleResult();
    	if (count.longValue() > 0) return;
    	
    	Number countAssoc = em.createQuery("SELECT count(*) FROM ProductCompany pc INNER JOIN "
    			+ "CompanyUser cu ON cu.company.id = pc.company.id WHERE cu.user.id = :userId AND pc.product.id = :productId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("productId", productId).
    		getSingleResult();
    	if (countAssoc.longValue() == 0L) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid product id or forbidden");
		}		
    }
    
    private void checkProductLabelBatchPermission(CustomUserDetails authUser, Long batchId) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return; 
    
    	Number count = em.createQuery("SELECT count(*) FROM ProductLabelBatch plb INNER JOIN CompanyUser cu ON cu.company.id = plb.label.product.company.id "
    			+ "WHERE cu.user.id = :userId AND plb.id = :batchId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("batchId", batchId).
    		getSingleResult();
    	if (count.longValue() == 0L) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid batch id or forbidden");
		}		
    }
    
    private void checkProductLabelBatchPermissionAssoc(CustomUserDetails authUser, Long batchId) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return; 
    
    	Number count = em.createQuery("SELECT count(*) FROM ProductLabelBatch plb INNER JOIN CompanyUser cu ON cu.company.id = plb.label.product.company.id "
    			+ "WHERE cu.user.id = :userId AND plb.id = :batchId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("batchId", batchId).
    		getSingleResult();
    	if (count.longValue() > 0) return;
    	
    	Number countAssoc = em.createQuery("SELECT count(*) FROM ProductCompany pc "
    			+ "INNER JOIN CompanyUser cu ON cu.company.id = pc.company.id "
    			+ "INNER JOIN ProductLabelBatch plb ON plb.label.product.id = pc.product.id "
    			+ "WHERE cu.user.id = :userId AND plb.id = :batchId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("batchId", batchId).
    		getSingleResult();
    	if (countAssoc.longValue() == 0L) {    	
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid batch id or forbidden");
		}		
    }    

	private KnowledgeBlog fetchKnowledgeBlog(CustomUserDetails authUser, Long id) throws ApiException {
    	KnowledgeBlog kb = Queries.get(em, KnowledgeBlog.class, id);
    	
		if (kb == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id");
		}		
		checkProductPermission(authUser, kb.getProduct().getId());
		return kb;
	}
	
	private KnowledgeBlog fetchKnowledgeBlogAssoc(CustomUserDetails authUser, Long id) throws ApiException {
    	KnowledgeBlog kb = Queries.get(em, KnowledgeBlog.class, id);
    	
		if (kb == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id");
		}		
		checkProductPermissionAssoc(authUser, kb.getProduct().getId());
		return kb;
	}	
    
	private Product fetchProduct(CustomUserDetails authUser, Long id) throws ApiException {
		checkProductPermission(authUser, id);
		Product p = Queries.get(em, Product.class, id);
		if (p == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid product id");
		}		
		return p;
	}
	
	private Product fetchProductAssoc(CustomUserDetails authUser, Long id) throws ApiException {
		checkProductPermissionAssoc(authUser, id);
		Product p = Queries.get(em, Product.class, id);
		if (p == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid product id");
		}		
		return p;
	}

	private CompanyCustomer fetchCompanyCustomer(CustomUserDetails authUser, Long id) throws ApiException {
		CompanyCustomer pc = Queries.get(em, CompanyCustomer.class, id);
		if (pc == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid id");
		}		
		checkProductPermission(authUser, pc.getProduct().getId());
		return pc;
	}
	
	private ProductLabel fetchProductLabelPublic(String uid) throws ApiException {
		ProductLabel pl = Queries.getUniqueBy(em, ProductLabel.class, ProductLabel::getUuid, uid);
		if (pl == null || pl.getStatus() == ProductLabelStatus.UNPUBLISHED) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid label uid");
		}		
		return pl;
	}
	
	private ProductLabel fetchProductLabelPublic(Long id) throws ApiException {
		ProductLabel pl = Queries.get(em, ProductLabel.class, id);
		if (pl == null || pl.getStatus() == ProductLabelStatus.UNPUBLISHED) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid label id");
		}		
		return pl;
	}
	
	private ProductLabelBatch fetchProductLabelBatch(CustomUserDetails authUser, Long id) throws ApiException {
		checkProductLabelBatchPermissionAssoc(authUser, id);		
		ProductLabelBatch plb = Queries.get(em, ProductLabelBatch.class, id);
		if (plb == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid batch id");
		}		
		return plb;
	}
}
