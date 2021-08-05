package com.abelium.inatrace.db.base;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;

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
