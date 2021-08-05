package com.abelium.inatrace.components.flyway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component("FlywayInitializer")
public class FlywayInitializer
{
    // makes sure that Flyway runs before every component that depends on FlywayInitializer,
    // but does not require Flyway to actually run
    @Autowired(required = false)
    DelayedFlywayMigrationInitializer delayedFlywayInitializer;
}
