package com.abelium.inatrace.configuration;

import java.lang.reflect.AnnotatedElement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.AbstractTransactionManagementConfiguration;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.config.TransactionManagementConfigUtils;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.DelegatingTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.abelium.inatrace.api.errors.ApiException;

@Configuration
public class CustomProxyTransactionManagementConfiguration extends AbstractTransactionManagementConfiguration {

    @Bean(name = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME)
    public BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor() {
        BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
        advisor.setTransactionAttributeSource(transactionAttributeSource());
        advisor.setAdvice(transactionInterceptor());
        return advisor;
    }

    @Bean
    public TransactionAttributeSource transactionAttributeSource() {
        return new CustomTransactionAttribute();
    }

    @Bean
    public TransactionInterceptor transactionInterceptor() {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionAttributeSource(transactionAttributeSource());
        if (this.txManager != null) {
            interceptor.setTransactionManager(this.txManager);
        }
        return interceptor;
    }

    @SuppressWarnings("serial")
    public static class CustomTransactionAttribute extends AnnotationTransactionAttributeSource {
        @Override
        protected TransactionAttribute determineTransactionAttribute(AnnotatedElement ae) {
            TransactionAttribute target = super.determineTransactionAttribute(ae);
            if (target == null) {
                return null;
            }
            return new DelegatingTransactionAttribute(target) {
                @Override
                public boolean rollbackOn(Throwable ex) {
                    return ex instanceof ApiException || super.rollbackOn(ex);
                }
            };
        }
    }
}
