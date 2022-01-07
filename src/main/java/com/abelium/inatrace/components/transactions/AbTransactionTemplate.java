package com.abelium.inatrace.components.transactions;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.abelium.inatrace.api.errors.ApiException;

/**
 * Like Spring's {@link TransactionTemplate}, but passes through {@link ApiException} (rolling back transaction)
 * and allows actions that don't return result.
 */
@Lazy
@Component
public class AbTransactionTemplate
{
    @FunctionalInterface
    public interface ApiTransactionCallback<T> {
        T doInTransaction(TransactionStatus status) throws ApiException;
    }

    @FunctionalInterface
    public interface ApiTransactionRunner {
        void doInTransaction(TransactionStatus status) throws ApiException;
    }

    @FunctionalInterface
    public interface TransactionRunner {
        void doInTransaction(TransactionStatus status);
    }
    
    @FunctionalInterface
    public interface SimpleApiTransactionCallback<T> {
        T doInTransaction() throws ApiException;
    }

    @FunctionalInterface
    public interface SimpleApiTransactionRunner {
        void doInTransaction() throws ApiException;
    }
    
    @FunctionalInterface
    public interface SimpleTransactionCallback<T> {
        T doInTransaction();
    }
    
    @FunctionalInterface
    public interface SimpleTransactionRunner {
        void doInTransaction();
    }
    
    private TransactionTemplate transactionTemplate;
    private int retries = 1;

    public AbTransactionTemplate(PlatformTransactionManager ptm, Propagation propagation) {
        this(ptm, createTransactionDefinition(propagation, Isolation.DEFAULT));
    }
    
    public AbTransactionTemplate(PlatformTransactionManager ptm, Propagation propagation, Isolation isolation) {
        this(ptm, createTransactionDefinition(propagation, isolation));
    }

    public AbTransactionTemplate(PlatformTransactionManager ptm, TransactionDefinition definition) {
        this.transactionTemplate = new TransactionTemplate(ptm, definition);
    }

    @Autowired
    public AbTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    private static DefaultTransactionDefinition createTransactionDefinition(Propagation propagation, Isolation isolation) {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition();
        td.setPropagationBehavior(propagation.value());
        td.setIsolationLevel(isolation.value());
        return td;
    }
    
    public AbTransactionTemplate withPropagation(Propagation propagation) {
        TransactionTemplate tt = new TransactionTemplate(transactionTemplate.getTransactionManager(), transactionTemplate);
        tt.setPropagationBehavior(propagation.value());
        return new AbTransactionTemplate(tt);
    }

    public AbTransactionTemplate withIsolation(Isolation isolation) {
        TransactionTemplate tt = new TransactionTemplate(transactionTemplate.getTransactionManager(), transactionTemplate);
        tt.setIsolationLevel(isolation.value());
        return new AbTransactionTemplate(tt);
    }
    
    public AbTransactionTemplate withRetries(int retries) {
        AbTransactionTemplate result = new AbTransactionTemplate(transactionTemplate);
        result.retries = Math.max(retries, 1);
        return result;
    }
    
    public AbTransactionTemplate withNewTransaction() {
        return withPropagation(Propagation.REQUIRES_NEW);
    }
    
    private static Map<Integer, Propagation> propagationMap = 
            Stream.of(Propagation.values()).collect(Collectors.toMap(v -> v.value(), v -> v));
    
    private static Map<Integer, Isolation> isolationMap = 
            Stream.of(Isolation.values()).collect(Collectors.toMap(v -> v.value(), v -> v));
    
    public Propagation getPropagation() {
        return propagationMap.get(transactionTemplate.getPropagationBehavior());
    }

    public Isolation getIsolation() {
        return isolationMap.get(transactionTemplate.getIsolationLevel());
    }
    
    public int getRetries() {
        return retries;
    }
    
    @SuppressWarnings("serial")
    private static class WrappedApiException extends RuntimeException {
        public final ApiException exception;
        
        public WrappedApiException(ApiException exception) {
            this.exception = exception;
        }
    }

    public <T> T execute(ApiTransactionCallback<T> action) throws ApiException {
        for (int count = 1; ; count++) {
            try {
                return transactionTemplate.execute(ts -> {
                    try {
                        return action.doInTransaction(ts);
                    } catch (ApiException e) {
                        throw new WrappedApiException(e);
                    }
                });
            } catch (WrappedApiException e) {
                throw e.exception;
            } catch (ConcurrencyFailureException e) {
                if (count >= retries) throw e;
            }
        }
    }

    public void executeVoid(ApiTransactionRunner action) throws ApiException {
        for (int count = 1; ; count++) {
            try {
                transactionTemplate.execute(ts -> {
                    try {
                        action.doInTransaction(ts);
                        return null;
                    } catch (ApiException e) {
                        throw new WrappedApiException(e);
                    }
                });
                return;
            } catch (WrappedApiException e) {
                throw e.exception;
            } catch (ConcurrencyFailureException e) {
                if (count >= retries) throw e;
            }
        }
    }

    public <T> T executeWithoutExceptions(TransactionCallback<T> action) {
        for (int count = 1; ; count++) {
            try {
                return transactionTemplate.execute(action); 
            } catch (ConcurrencyFailureException e) {
                if (count >= retries) throw e;
            }
        }
    }

    public void executeVoidWithoutExceptions(TransactionRunner action) {
        for (int count = 1; ; count++) {
            try {
                transactionTemplate.execute(ts -> {
                    action.doInTransaction(ts);
                    return null;
                });
                return;
            } catch (ConcurrencyFailureException e) {
                if (count >= retries) throw e;
            }
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    // "Simple" versions - do not take TransactionStatus argument
    
    public <T> T execute(SimpleApiTransactionCallback<T> action) throws ApiException {
        return execute((ts) -> action.doInTransaction());
    }
    
    public void executeVoid(SimpleApiTransactionRunner action) throws ApiException {
        executeVoid((ts) -> action.doInTransaction());
    }
    
    public <T> T executeWithoutExceptions(SimpleTransactionCallback<T> action) {
        return executeWithoutExceptions((ts) -> action.doInTransaction());
    }
    
    public void executeVoidWithoutExceptions(SimpleTransactionRunner action) {
        executeVoidWithoutExceptions((ts) -> action.doInTransaction());
    }
}
