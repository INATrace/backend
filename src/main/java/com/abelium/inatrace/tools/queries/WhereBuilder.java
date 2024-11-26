package com.abelium.inatrace.tools.queries;

import org.torpedoquery.jakarta.jpa.Torpedo;
import java.util.Collection;

/**
 * A temporary object for building simple Torpedo-like conditions e.g.
 * <pre>where(User::getEmail).like("phy%")</pre>
 * Most comparison methods from Torpedo are supported (but not {@code and} and {@code or} - use {@code whereCondition} for complex conditions).
 * @param <T> the enclosing entity type
 * @param <S> the property type
 */
public class WhereBuilder<T, S> {
    private SimpleQueryBuilder<T> qb;
    private PropertySelector<T, S> property;

    public WhereBuilder(SimpleQueryBuilder<T> qb, PropertySelector<T, S> property) {
        this.qb = qb;
        this.property = property;
    }
    
    public SimpleQueryBuilder<T> isNull() {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).isNull();
        return qb.whereCondition(condition);
    }

    public SimpleQueryBuilder<T> isNotNull() {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).isNotNull();
        return qb.whereCondition(condition);
    }
    
    public SimpleQueryBuilder<T> eq(S value) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).eq(value);
        return qb.whereCondition(condition);
    }

    public SimpleQueryBuilder<T> neq(S value) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).neq(value);
        return qb.whereCondition(condition);
    }
    
    public SimpleQueryBuilder<T> in(Collection<S> values) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).in(values);
        return qb.whereCondition(condition);
    }

    public SimpleQueryBuilder<T> in(@SuppressWarnings("unchecked") S... values) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).in(values);
        return qb.whereCondition(condition);
    }

    public SimpleQueryBuilder<T> notIn(Collection<S> values) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).notIn(values);
        return qb.whereCondition(condition);
    }

    public SimpleQueryBuilder<T> notIn(@SuppressWarnings("unchecked") S... values) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).notIn(values);
        return qb.whereCondition(condition);
    }

    @SuppressWarnings("unchecked")
    public SimpleQueryBuilder<T> gt(S value) {
        AliasCondition<T> condition = e -> Torpedo.condition((Comparable<S>) property.evaluate(e)).gt(value);
        return qb.whereCondition(condition);
    }

    @SuppressWarnings("unchecked")
    public SimpleQueryBuilder<T> gte(S value) {
        AliasCondition<T> condition = e -> Torpedo.condition((Comparable<S>) property.evaluate(e)).gte(value);
        return qb.whereCondition(condition);
    }

    @SuppressWarnings("unchecked")
    public SimpleQueryBuilder<T> lt(S value) {
        AliasCondition<T> condition = e -> Torpedo.condition((Comparable<S>) property.evaluate(e)).lt(value);
        return qb.whereCondition(condition);
    }

    @SuppressWarnings("unchecked")
    public SimpleQueryBuilder<T> lte(S value) {
        AliasCondition<T> condition = e -> Torpedo.condition((Comparable<S>) property.evaluate(e)).lte(value);
        return qb.whereCondition(condition);
    }

    public SimpleQueryBuilder<T> between(S from, S to) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).between(from, to);
        return qb.whereCondition(condition);
    }

    public SimpleQueryBuilder<T> notBetween(S from, S to) {
        AliasCondition<T> condition = e -> Torpedo.condition(property.evaluate(e)).notBetween(from, to);
        return qb.whereCondition(condition);
    }
    
    public SimpleQueryBuilder<T> like(String value) {
        AliasCondition<T> condition = e -> Torpedo.condition((String) property.evaluate(e)).like(value);
        return qb.whereCondition(condition);
    }
    
    public SimpleQueryBuilder<T> notLike(String value) {
        AliasCondition<T> condition = e -> Torpedo.condition((String) property.evaluate(e)).notLike(value);
        return qb.whereCondition(condition);
    }
}
