package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class V2023_05_24_17_26__Rename__CompanyUserRole__USER__into__COMPANY_USER implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {

        Query query = em.createQuery("UPDATE CompanyUser set role = 'COMPANY_USER' where role = 'USER'");
        query.executeUpdate();
    }
}
