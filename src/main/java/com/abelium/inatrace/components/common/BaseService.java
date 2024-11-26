package com.abelium.inatrace.components.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {
	
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Entity manager for connection to the database.
     */
    @PersistenceContext
    protected EntityManager em;

}
