package com.abelium.inatrace.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.abelium.inatrace.api.errors.ApiException;

public class ListTools
{
    public static <T> Iterable<T> toIterable(Enumeration<T> enumeration) {
        return enumeration::asIterator;
    }
    
    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> result = new ArrayList<T>();
        for (T elt : iterable) result.add(elt);
        return result;
    }
    
    public static <T> List<T> toList(Enumeration<T> enumeration) {
        return toList(enumeration::asIterator);
    }
    
    public static <T, R> List<R> map(Collection<T> source, Function<T, R> function) {
        if (source == null) return null;
        List<R> result = new ArrayList<R>(source.size());
        for (T x : source) {
            result.add(function.apply(x));
        }
        return result;
    }
    
    public static <T, R> List<R> mapThrowable(Collection<T> source, ThrowableFunction<T, R> function) throws ApiException {
        if (source == null) return null;
        List<R> result = new ArrayList<R>(source.size());
        for (T x : source) {
            result.add(function.apply(x));
        }
        return result;
    }    

    public static <T, R, C extends Collection<R>> C map(Collection<T> source, Function<T, R> function, Supplier<C> resultCreator) {
        if (source == null) return null;
        C result = resultCreator.get();
        for (T x : source) {
            result.add(function.apply(x));
        }
        return result;
    }

    public static <T, R> List<R> map(T[] source, Function<T, R> function) {
        if (source == null) return null;
        List<R> result = new ArrayList<R>(source.length);
        for (T x : source) {
            result.add(function.apply(x));
        }
        return result;
    }

    public static <T, R, C extends Collection<R>> C map(T[] source, Function<T, R> function, Supplier<C> resultCreator) {
        if (source == null) return null;
        C result = resultCreator.get();
        for (T x : source) {
            result.add(function.apply(x));
        }
        return result;
    }
    
    public static <T> List<T> subList(List<T> data, int start, int end) {
        if (data == null) return null;
        int length = data.size();
        start = Math.max(0, Math.min(start, length));
        end = Math.max(start, Math.min(end, length));
        return data.subList(start, end);
    }
    
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return c != null && !c.isEmpty();
    }
}
