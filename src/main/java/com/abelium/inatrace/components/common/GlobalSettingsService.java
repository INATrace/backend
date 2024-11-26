package com.abelium.inatrace.components.common;

import com.abelium.inatrace.db.entities.common.GlobalSettings;
import com.abelium.inatrace.tools.Queries;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Lazy
@Service
public class GlobalSettingsService extends BaseService {
	
    @Transactional 
    public String getSettings(String name, boolean publicAccess) {
    	GlobalSettings gs = Queries.getUniqueBy(em, GlobalSettings.class, GlobalSettings::getName, name);
    	if (gs == null) return null;
    	else if (publicAccess && !Boolean.TRUE.equals(gs.getIsPublic())) return null;
    	else return gs.getValue();
    }
    
    @Transactional
    public void updateSettings(String name, String value, Boolean isPublic) {
    	GlobalSettings gs = Queries.getUniqueBy(em, GlobalSettings.class, GlobalSettings::getName, name);
    	if (gs != null) {
    		gs.setValue(value);
    		gs.setIsPublic(isPublic);
    	} else {
    		gs = new GlobalSettings();
    		gs.setName(name);
    		gs.setValue(value);
    		gs.setIsPublic(isPublic);
    		em.persist(gs);
    	}
    }

}
