package com.abelium.inatrace.tools.queries;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * The class representing a fully specified query.
 */
public interface Selection<S>
{

    /**
     * Set the maximum number of results.
     * @param limit maximum number of results
     * @return this for chaining
     */
    Selection<S> limit(Integer limit);

    /**
     * Set the first result.
     * @param offset the first result (0 based)
     * @return this for chaining
     */
    Selection<S> offset(Integer offset);

    /**
     * List all results matching the query.
     */
    List<S> list();

    /**
     * Return unique result or throw.
     * @return the unique result if available otherwise empty
     */
    Optional<S> unique();
    
    /**
     * Return the first result (even if more than one matches query).
     * @return the first result if available otherwise empty
     */
    Optional<S> first();
    
    /**
     * Return JPQL query string.
     */
    String queryString();

    /**
     * Return unique result or throw.
     * @return the unique result or {@code null} if no results are available
     */
    default S uniqueOrNull() {
        return unique().orElse(null);
    }

    /**
     * Return the first result (even if more than one matches query).
     * @return the first result or {@code null} if no results are available
     */
    default S firstOrNull() {
        return first().orElse(null);
    }

    /**
     * Transform selection rows to another type.
     * @param transformatio the function to transform each row in result.
     * @return transformed selection
     */
    default <R> Selection<R> transform(Function<S, R> transformation) {
        return new TransformedSelection<>(this, transformation);
    }
}
