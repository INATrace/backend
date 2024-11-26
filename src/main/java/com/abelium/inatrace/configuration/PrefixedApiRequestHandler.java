package com.abelium.inatrace.configuration;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;
import jakarta.annotation.Nonnull;
import java.lang.reflect.Method;

public class PrefixedApiRequestHandler extends RequestMappingHandlerMapping {
	
    private final static String API_BASE_PATH = "api";

    @Override
    protected void registerHandlerMethod(@Nonnull Object handler, Method method, @Nonnull RequestMappingInfo mapping) {
        
        if (method.getDeclaringClass().getPackageName().startsWith("com.abelium") && mapping.getPathPatternsCondition() != null) {

            PathPatternsRequestCondition pathPatternsRequestCondition = new PathPatternsRequestCondition(
                    new PathPatternParser(), API_BASE_PATH).combine(mapping.getPathPatternsCondition());

            RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
            options.setPatternParser(new PathPatternParser());

            RequestMappingInfo.Builder builder = RequestMappingInfo
                    .paths(pathPatternsRequestCondition.getPatternValues().toArray(new String[]{}))
                    .methods(mapping.getMethodsCondition().getMethods().toArray(new RequestMethod[]{}))
                    .params(mapping.getParamsCondition().getExpressions().stream().map(NameValueExpression::getValue).toArray(String[]::new))
                    .headers(mapping.getHeadersCondition().getExpressions().stream().map(NameValueExpression::getValue).toArray(String[]::new))
                    .consumes(mapping.getConsumesCondition().getExpressions().stream().map(mediaTypeExpression -> mediaTypeExpression.getMediaType().toString()).toArray(String[]::new))
                    .produces(mapping.getProducesCondition().getExpressions().stream().map(mediaTypeExpression -> mediaTypeExpression.getMediaType().toString()).toArray(String[]::new))
                    .options(options);

            if (mapping.getCustomCondition() != null) {
                builder.customCondition(mapping.getCustomCondition());
            }
            if (mapping.getName() != null) {
                builder.mappingName(mapping.getName());
            }

            mapping = builder.build();
        }

        super.registerHandlerMethod(handler, method, mapping);
    }	
}
