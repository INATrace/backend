package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class V2023_05_24_17_09__Rename__CompanyUserRole__ADMIN__into__COMPANY_ADMIN implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {

        Query query = em.createQuery("UPDATE CompanyUser set role = 'COMPANY_ADMIN' where role = 'ADMIN'");
        query.executeUpdate();
    }
}
