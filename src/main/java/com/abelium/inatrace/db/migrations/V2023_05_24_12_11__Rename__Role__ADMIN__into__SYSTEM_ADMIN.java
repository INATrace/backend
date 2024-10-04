package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.processingaction.ProcessingAction;
import com.abelium.inatrace.tools.Queries;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

public class V2023_05_24_12_11__Rename__Role__ADMIN__into__SYSTEM_ADMIN implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {

        Query query = em.createQuery("UPDATE User set role = 'SYSTEM_ADMIN' where role = 'ADMIN'");
        query.executeUpdate();
    }
}
