package com.abelium.inatrace.components.flyway;

import jakarta.persistence.EntityManager;
import org.springframework.core.env.Environment;

public interface JpaMigration {
    void migrate(EntityManager em, Environment environment) throws Exception;
}
