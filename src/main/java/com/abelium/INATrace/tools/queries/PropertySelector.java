package com.abelium.INATrace.tools.queries;

@FunctionalInterface
public interface PropertySelector<T, S> {
    public S evaluate(T alias);
}