package com.abelium.inatrace.tools.queries;

@FunctionalInterface
public interface PropertySelector<T, S> {
    public S evaluate(T alias);
}
