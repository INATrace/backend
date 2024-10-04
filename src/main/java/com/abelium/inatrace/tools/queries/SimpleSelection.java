package com.abelium.inatrace.tools.queries;

import jakarta.persistence.EntityManager;
import org.torpedoquery.jakarta.jpa.Query;

import java.util.List;
import java.util.Optional;

public class SimpleSelection<S> implements Selection<S> {
    private EntityManager em;
    private Query<S> query;
    private Integer limit = null;
    private Integer offset = null;
    
    public SimpleSelection(EntityManager em, Query<S> query) {
        this.em = em;
        this.query = query;
    }

    /* (non-Javadoc)
     * @see com.abelium.INATrace.tools.queries.Selection#limit(java.lang.Integer)
     */
    @Override
    public Selection<S> limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    /* (non-Javadoc)
     * @see com.abelium.INATrace.tools.queries.Selection#offset(java.lang.Integer)
     */
    @Override
    public Selection<S> offset(Integer offset) {
        this.offset = offset;
        return this;
    }
    
    /* (non-Javadoc)
     * @see com.abelium.INATrace.tools.queries.Selection#list()
     */
    @Override
    public List<S> list() {
        if (limit != null) query.setMaxResults(limit);
        if (offset != null) query.setFirstResult(offset);
        return query.list(em);
    }
    
    /* (non-Javadoc)
     * @see com.abelium.INATrace.tools.queries.Selection#unique()
     */
    @Override
    public Optional<S> unique() {
        return query.get(em);
    }

    /* (non-Javadoc)
     * @see com.abelium.INATrace.tools.queries.Selection#first()
     */
    @Override
    public Optional<S> first() {
        List<S> results = query.setMaxResults(1).list(em);
        return results.size() >= 1 ? Optional.of(results.get(0)) : Optional.empty();
    }
    
    /* (non-Javadoc)
     * @see com.abelium.INATrace.tools.queries.Selection#getQueryString()
     */
    @Override
    public String queryString() {
        return query.getQuery();
    }
}
