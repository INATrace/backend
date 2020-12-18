package com.abelium.INATrace.components.product;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.BaseEngine;
import com.abelium.INATrace.db.entities.ProductLabel;
import com.abelium.INATrace.security.service.CustomUserDetails;
import com.abelium.INATrace.tools.Queries;
import com.abelium.INATrace.types.UserRole;

@Lazy
@Component
public class ProductQueries extends BaseEngine {
	
	@Transactional
	public ProductLabel fetchProductLabel(CustomUserDetails authUser, Long id) throws ApiException {
		checkProductLabelPermission(authUser, id);
		ProductLabel pl = Queries.get(em, ProductLabel.class, id);
		if (pl == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid label id");
		}		
		return pl;
	}
	
	@Transactional
	public ProductLabel fetchProductLabelAssoc(CustomUserDetails authUser, Long id) throws ApiException {
		checkProductLabelPermissionAssoc(authUser, id);
		ProductLabel pl = Queries.get(em, ProductLabel.class, id);
		if (pl == null) {
			throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid label id");
		}		
		return pl;
	}	
	
    public void checkProductLabelPermission(CustomUserDetails authUser, Long labelId) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return; 
    
    	Number count = em.createQuery("SELECT count(*) FROM ProductLabel pl INNER JOIN CompanyUser cu ON cu.company.id = pl.product.company.id "
    			+ "WHERE cu.user.id = :userId AND pl.id = :labelId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("labelId", labelId).
    		getSingleResult();
    	if (count.longValue() == 0L) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid label id or forbidden");
		}		
    }
    
    public void checkProductLabelPermissionAssoc(CustomUserDetails authUser, Long labelId) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return; 
    
    	Number count = em.createQuery("SELECT count(*) FROM ProductLabel pl INNER JOIN CompanyUser cu ON cu.company.id = pl.product.company.id "
    			+ "WHERE cu.user.id = :userId AND pl.id = :labelId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("labelId", labelId).
    		getSingleResult();
    	if (count.longValue() > 0) return;
    	
    	Number countAssoc = em.createQuery("SELECT count(*) FROM ProductCompany pc "
    			+ "INNER JOIN CompanyUser cu ON cu.company.id = pc.company.id "
    			+ "INNER JOIN ProductLabel pl ON pl.product.id = pc.product.id "
    			+ "WHERE cu.user.id = :userId AND pl.id = :labelId", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("labelId", labelId).
    		getSingleResult();
    	if (countAssoc.longValue() == 0L) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid label id or forbidden");
		}		
    }
    

    @Transactional
    public void checkProductLabelPermission(CustomUserDetails authUser, String labelUid) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return; 
    
    	Number count = em.createQuery("SELECT count(*) FROM ProductLabel pl INNER JOIN CompanyUser cu ON cu.company.id = pl.product.company.id "
    			+ "WHERE cu.user.id = :userId AND pl.uuid = :labelUid", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("labelUid", labelUid).
    		getSingleResult();
    	if (count.longValue() == 0L) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid label uid or forbidden");
		}		
    }

    @Transactional
    public void checkProductLabelPermissionAssoc(CustomUserDetails authUser, String labelUid) throws ApiException {
		if (authUser.getUserRole() == UserRole.ADMIN) return; 
	    
    	Number count = em.createQuery("SELECT count(*) FROM ProductLabel pl INNER JOIN CompanyUser cu ON cu.company.id = pl.product.company.id "
    			+ "WHERE cu.user.id = :userId AND pl.uuid = :labelUid", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("labelUid", labelUid).
    		getSingleResult();
    	if (count.longValue() > 0) return;
    	
    	Number countAssoc = em.createQuery("SELECT count(*) FROM ProductCompany pc "
    			+ "INNER JOIN CompanyUser cu ON cu.company.id = pc.company.id "
    			+ "INNER JOIN ProductLabel pl ON pl.product.id = pc.product.id "
    			+ "WHERE cu.user.id = :userId AND pl.uuid = :labelUid", Number.class).
    		setParameter("userId", authUser.getUserId()).
    		setParameter("labelUid", labelUid).
    		getSingleResult();
    	if (countAssoc.longValue() == 0L) {
			throw new ApiException(ApiStatus.UNAUTHORIZED, "Invalid label id or forbidden");
		}		
    }
    
	
}
