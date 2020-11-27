package com.abelium.INATrace.configuration;

import java.lang.reflect.Method;

import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class PrefixedApiRequestHandler extends RequestMappingHandlerMapping {
	
    private final static String API_BASE_PATH = "api";

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        
        if (method.getDeclaringClass().getPackageName().startsWith("com.abelium")) {
            PatternsRequestCondition apiPattern = new PatternsRequestCondition(API_BASE_PATH)
                    .combine(mapping.getPatternsCondition());

            mapping = new RequestMappingInfo(mapping.getName(), apiPattern,
                    mapping.getMethodsCondition(), mapping.getParamsCondition(),
                    mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                    mapping.getProducesCondition(), mapping.getCustomCondition());
        }

        super.registerHandlerMethod(handler, method, mapping);
    }	
}
