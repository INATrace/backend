package com.abelium.inatrace.tools.queries;

import org.torpedoquery.jakarta.jpa.OnGoingLogicalCondition;

@FunctionalInterface
public interface AliasCondition<T> {
    public OnGoingLogicalCondition evaluate(T alias);
}
