
package com.abelium.inatrace.components.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseEngine {
	
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Entity manager for connection to the database.
     */
    @PersistenceContext
    protected EntityManager em;

}
