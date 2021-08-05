package com.abelium.inatrace.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.abelium.inatrace.api.formatters.InstantFormatter;
import com.abelium.inatrace.api.formatters.LocalDateFormatter;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new InstantFormatter());
        registry.addFormatter(new LocalDateFormatter());
    }
    
}
