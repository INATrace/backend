package com.abelium.inatrace.db.migrations;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.springframework.core.env.Environment;
import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.company.CompanyUser;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.CompanyUserRole;

public class V2020_11_26_12_30__Update_Company_User_Role implements JpaMigration {
    
    public void migrate(EntityManager em, Environment environment) throws Exception {
    	List<CompanyUser> cuList = Queries.getAll(em, CompanyUser.class);
    	
        for (CompanyUser cu : cuList) {
        	cu.setRole(CompanyUserRole.COMPANY_USER);
        }
    }
}
