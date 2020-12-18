package com.abelium.INATrace.configuration;

import javax.persistence.EntityManagerFactory;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.abelium.INATrace.components.flyway.DelayedFlywayMigrationInitializer;
import com.abelium.INATrace.components.flyway.JpaMigrationStrategy;

@Configuration
public class MigrationsConfiguration {
    
    // Override default Flyway initializer to do nothing
    @Bean
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, f -> { /* empty */ });
    }
    
    // Create a second Flyway initializer to run after EntityManagerFactory was initialized
    @Bean
    DelayedFlywayMigrationInitializer delayedFlywayInitializer(Flyway flyway, EntityManagerFactory entityManagerFactory, Environment environment) {
        return new DelayedFlywayMigrationInitializer(flyway, new JpaMigrationStrategy(entityManagerFactory, environment));
    }
}
