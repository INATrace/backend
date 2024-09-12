package com.abelium.inatrace.db.base;

import jakarta.persistence.*;

/**
 * Base class for all entities, contains auto incremented id.
 */
@MappedSuperclass
public class BaseEntity {

    // @Access(AccessType.PROPERTY) is required on the field 'id',
    // so that you can use getId() on a lazy proxy object without doing a full database fetch
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Long id;    
    
    public Long getId() {
        return id;
    }
    
    protected void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return BaseEntity.infoString(this);
    }
    
    public static String infoString(BaseEntity e) {
        return e != null ? String.format("%s(id:%s)", e.getClass().getSimpleName(), e.id) : "null";
    }
}
