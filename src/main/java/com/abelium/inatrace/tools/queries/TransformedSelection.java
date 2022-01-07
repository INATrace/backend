package com.abelium.inatrace.tools.queries;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.abelium.inatrace.tools.ListTools;

public class TransformedSelection<S, R> implements Selection<R>
{
    private Selection<S> selection;
    private Function<S, R> transformation;

    public TransformedSelection(Selection<S> selection, Function<S, R> transformation) {
        this.selection = selection;
        this.transformation = transformation;
    }

    @Override
    public Selection<R> limit(Integer limit) {
        selection.limit(limit);
        return this;
    }

    @Override
    public Selection<R> offset(Integer offset) {
        selection.offset(offset);
        return this;
    }

    @Override
    public List<R> list() {
        List<S> list = selection.list();
        return ListTools.map(list, transformation);
    }

    @Override
    public Optional<R> unique() {
        return selection.unique().map(transformation); 
    }

    @Override
    public Optional<R> first() {
        return selection.first().map(transformation);
    }

    @Override
    public String queryString() {
        return selection.queryString();
    }
}
