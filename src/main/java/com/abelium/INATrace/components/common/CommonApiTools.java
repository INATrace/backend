package com.abelium.INATrace.components.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.abelium.INATrace.api.ApiBaseEntity;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.components.common.api.ApiCountry;
import com.abelium.INATrace.components.common.api.ApiDocument;
import com.abelium.INATrace.components.company.api.ApiAddress;
import com.abelium.INATrace.components.company.api.ApiGeoAddress;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.db.entities.Address;
import com.abelium.INATrace.db.entities.Country;
import com.abelium.INATrace.db.entities.Document;
import com.abelium.INATrace.db.entities.GeoAddress;

@Lazy
@Service
public class CommonApiTools {
	
	@Autowired
	private CommonEngine commonEngine;
	
	public static void updateApiBaseEntity(ApiBaseEntity apiEntity, BaseEntity entity) {
		apiEntity.id = entity.getId();
	}	

    public static ApiCountry toApiCountry(Country country) {
        if (country == null) {
            return null;
        }
        ApiCountry ac = new ApiCountry(); 
        ac.id = country.getId();
        ac.name = country.getName();
        ac.code = country.getCode();
        return ac;
    }

    public static ApiDocument toApiDocument(Document document, Long userId) {
        if (document == null) {
            return null;
        }
        ApiDocument ad = new ApiDocument();
        ad.id = document.getId();
        ad.name = document.getName();
        //ad.storageKey = document.getStorageKey();
        ad.storageKey = StorageKeyCache.put(document.getStorageKey(), userId);
        ad.contentType = document.getContentType();
        ad.size = document.getSize();
        return ad;
    }
    
    public static void updateDocument(Document d, ApiDocument ad) {
        d.setName(ad.name);
        d.setStorageKey(ad.storageKey);
        d.setContentType(ad.contentType);
        d.setSize(ad.size);
    }
    
    public static ApiAddress toApiAddress(Address address) {
        if (address == null) {
            return null;
        }
        ApiAddress aa = new ApiAddress();
        updateApiAddress(aa, address);
        return aa;
    }
    
    public static ApiGeoAddress toApiGeoAddress(GeoAddress address) {
        if (address == null) {
            return null;
        }
        ApiGeoAddress aa = new ApiGeoAddress();
        updateApiAddress(aa, address);
        aa.latitude = address.getLatitude();
        aa.longitude = address.getLongitude();
        return aa;
    }
    
    public static void updateApiAddress(ApiAddress aa, Address address) {
        aa.address = address.getAddress();
        aa.city = address.getCity();
        aa.state = address.getState();
        aa.zip = address.getZip();
        aa.country = toApiCountry(address.getCountry());    	
    }
    
    public void updateAddress(Address a, ApiAddress aa) throws ApiException {
        a.setAddress(aa.address);
        a.setCity(aa.city);
        a.setState(aa.state);
        a.setZip(aa.zip);
        a.setCountry(commonEngine.fetchCountry(aa.getCountry()));
    }

    public void updateGeoAddress(GeoAddress a, ApiGeoAddress aa) throws ApiException {
    	updateAddress(a, aa);
        a.setLatitude(aa.latitude);
        a.setLongitude(aa.longitude);
    }

    public Address toAddress(ApiAddress aa) throws ApiException {
		if (aa == null) return null;
		
		Address a = new Address();
		updateAddress(a, aa);
		return a;
	}

    public GeoAddress toGeoAddress(ApiGeoAddress aa) throws ApiException {
		if (aa == null) return null;
		
		GeoAddress a = new GeoAddress();
		updateGeoAddress(a, aa);
		return a;
	}
    
}
