package com.abelium.inatrace.db.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

/**
 * Base class for all entities with timestamp, creationTimestamp and updateTimestamp 
 * managed by Hibernate using @CreationTimestamp and @UpdateTimestamp.
 */
@MappedSuperclass
public class TimestampEntity extends BaseEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private Instant creationTimestamp;
    
    @UpdateTimestamp
    @Column
    private Instant updateTimestamp;
    
    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }
    
    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

}
