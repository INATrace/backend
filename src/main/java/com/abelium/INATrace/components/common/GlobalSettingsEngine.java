package com.abelium.INATrace.components.common;

import javax.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.abelium.INATrace.db.entities.GlobalSettings;
import com.abelium.INATrace.tools.Queries;

@Lazy
@Service
public class GlobalSettingsEngine extends BaseEngine {
	
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
