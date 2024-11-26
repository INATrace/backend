package com.abelium.inatrace.db.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * Base class for all entities with creationTimestamp  
 * managed by Hibernate using @CreationTimestamp
 */
@MappedSuperclass
public class CreationTimestampEntity extends BaseEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private Instant creationTimestamp;
    

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }
}
