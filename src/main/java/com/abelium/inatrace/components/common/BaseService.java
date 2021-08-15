
package com.abelium.inatrace.components.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseService {
	
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Entity manager for connection to the database.
     */
    @PersistenceContext
    protected EntityManager em;

}
