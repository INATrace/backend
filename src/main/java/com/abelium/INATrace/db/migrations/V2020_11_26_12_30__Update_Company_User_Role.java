package com.abelium.INATrace.db.migrations;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.core.env.Environment;
import com.abelium.INATrace.components.flyway.JpaMigration;
import com.abelium.INATrace.db.entities.CompanyUser;
import com.abelium.INATrace.tools.Queries;
import com.abelium.INATrace.types.CompanyUserRole;

public class V2020_11_26_12_30__Update_Company_User_Role implements JpaMigration {
    
    public void migrate(EntityManager em, Environment environment) throws Exception {
    	List<CompanyUser> cuList = Queries.getAll(em, CompanyUser.class);
    	
        for (CompanyUser cu : cuList) {
        	cu.setRole(CompanyUserRole.USER);
        }
    }
}
