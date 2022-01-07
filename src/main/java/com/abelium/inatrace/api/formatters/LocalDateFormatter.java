package com.abelium.inatrace.api.formatters;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class LocalDateFormatter implements Formatter<LocalDate> {
	
    @Override
    public String print(LocalDate object, Locale locale) {
        return object != null ? DateTimeFormatter.ISO_LOCAL_DATE.format(object) : "";
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
