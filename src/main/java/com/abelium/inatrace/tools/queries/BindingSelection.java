package com.abelium.inatrace.tools.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BindingSelection<E, O> implements Selection<O>
{
    @FunctionalInterface
    public interface BindingSetter<O, V> {
        void set(O object, V value);
    }
    
    private static class Binding<E, O, V> {
        final PropertySelector<E, V> property;
        final BindingSetter<O, V> setter;
        
        public Binding(PropertySelector<E, V> property, BindingSetter<O, V> setter) {
            this.property = property;
            this.setter = setter;
        }
        
        @SuppressWarnings("unchecked")
        public final void set(O object, Object value) {
            setter.set(object, (V) value); 
        }
    }

    
    private SimpleQueryBuilder<E> queryBuilder;
    private Supplier<O> objectCreator;
    private List<Binding<E, O, ?>> bindings = new ArrayList<>();
    private Selection<O> selection = null;
    private boolean skipNulls = false;
    
    public BindingSelection(SimpleQueryBuilder<E> queryBuilder, Supplier<O> objectCreator) {
        this.queryBuilder = queryBuilder;
        this.objectCreator = objectCreator;
    }

    /**
     * Bind entity property to a field or property of the projection target object.
     * @param property property get method or lambda that returns a torpedo function call
     * @param setter target property setter or a lambda that sets a field
     * @return this for chaining
     */
    public <V> BindingSelection<E, O> bind(PropertySelector<E, V> property, BindingSetter<O, V> setter) {
        bindings.add(new Binding<>(property, setter));
        return this;
    }
    
    /**
     * If a row value is {@code null}, setting it to a field of primitive type will trigger {@link NullPointerException}.
     * Setting {@code skipNulls} to {@code true} (default is {@code false}) will prevent assigning nulls, in which case fields corresponding to null
     * values will typically have default value (e.g. 0 for {@code int}).
     * @param value the new value for {@code skipNulls} property
     * @return this for chaining
     */
    public BindingSelection<E, O> skipNulls(boolean value) {
        this.skipNulls = value;
        return this;
    }
    
    private Selection<O> initSelection() {
        if (selection == null) {
            @SuppressWarnings("unchecked")
            PropertySelector<E, ?>[] properties = new PropertySelector[bindings.size()];
            for (int i = 0; i < bindings.size(); i++) {
                properties[i] = bindings.get(i).property;
            }
            Selection<Object[]> rowSelection = queryBuilder.select(properties);
            selection = rowSelection.transform(this::transform);
        }
        return selection;
    }
    
    private O transform(Object[] row) {
        O obj = objectCreator.get();
        for (int i = 0; i < row.length; i++) {
            Binding<E, O, ?> binding = bindings.get(i);
            Object value = row[i];
            if (value != null || !skipNulls) {
                binding.set(obj, value);
            }
        }
        return obj;
    }

    @Override
    public Selection<O> limit(Integer limit) {
        return initSelection().limit(limit);
    }

    @Override
    public Selection<O> offset(Integer offset) {
        return initSelection().offset(offset);
    }

    @Override
    public List<O> list() {
        return initSelection().list();
    }

    @Override
    public Optional<O> unique() {
        return initSelection().unique();
    }

    @Override
    public Optional<O> first() {
        return initSelection().first();
    }

    @Override
    public String queryString() {
        return initSelection().queryString();
    }
}
