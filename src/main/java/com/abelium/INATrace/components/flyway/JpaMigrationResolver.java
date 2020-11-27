package com.abelium.INATrace.components.flyway;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.api.resolver.Context;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.resolver.MigrationInfoHelper;
import org.flywaydb.core.internal.resolver.ResolvedMigrationComparator;
import org.flywaydb.core.internal.resolver.ResolvedMigrationImpl;
import org.flywaydb.core.internal.scanner.Scanner;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.Pair;
import org.springframework.core.env.Environment;

/**
 * Migration resolver for JPA migrations. 
 * (Modified JDBC migrations)
 */
public class JpaMigrationResolver implements MigrationResolver {

    private EntityManagerFactory entityManagerFactory;
    
    /**
     * The base package on the classpath where the migrations are located.
     */
    private Location[] locations;

    /**
     * The ClassLoader to use.
     */
    private ClassLoader classLoader;
    
    /**
     * The application environment
     */
    private Environment environment;
    
    /**
     * Creates a new instance.
     *
     * @param locations The base packages on the classpath where to migrations are located.
     * @param entityManager entity manager.
     * @param classLoader The ClassLoader for loading migrations on the classpath.
     * @param environment application environment
     */
    public JpaMigrationResolver(Configuration configuration, EntityManagerFactory entityManagerFactory, Environment environment) {
        this.locations = configuration.getLocations();
        this.classLoader = configuration.getClassLoader();
        this.entityManagerFactory = entityManagerFactory;
        this.environment = environment;
    }

    @Override
    public Collection<ResolvedMigration> resolveMigrations(Context context) {
        List<ResolvedMigration> migrations = new ArrayList<ResolvedMigration>();

        for (Location location : locations) {
            if (!location.isClassPath()) {
                continue;
            }

            try {
                Collection<Class<? extends JpaMigration>> classes = 
                        new Scanner<JpaMigration>(JpaMigration.class, Arrays.asList(location), classLoader, StandardCharsets.UTF_8).getClasses();
                for (Class<?> clazz : classes) {
                    JpaMigration migration = ClassUtils.instantiate(clazz.getName(), classLoader);
                    ResolvedMigrationImpl migrationInfo = extractMigrationInfo(migration, clazz);
                    migrations.add(migrationInfo);
                }
            } catch (Exception e) {
                throw new FlywayException("Unable to resolve Custom JPA migrations in location: " + location, e);
            }
        }

        Collections.sort(migrations, new ResolvedMigrationComparator());
        return migrations;
    }

    /**
     * Extracts the migration info from this migration.
     *
     * @param migration The migration to analyze.
     * @param clazz 
     * @return The migration info.
     */
    ResolvedMigrationImpl extractMigrationInfo(JpaMigration migration, Class<?> clazz) {
        String className = getShortName(migration.getClass());
        Pair<MigrationVersion, String> info = MigrationInfoHelper.extractVersionAndDescription(className, "V", "__", new String[] { "" }, false);
        MigrationVersion version = info.getLeft();
        String description = info.getRight();

        Integer checksum = null;
        String script = migration.getClass().getName();
        String physicalLocation = ClassUtils.getLocationOnDisk(clazz);
        JpaMigrationExecutor executor = new JpaMigrationExecutor(migration, entityManagerFactory, environment);

        return new ResolvedMigrationImpl(version, description, script, checksum, MigrationType.CUSTOM, physicalLocation, executor);
    }

    private static String getShortName(Class<?> aClass) {
        String name = aClass.getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }
} 