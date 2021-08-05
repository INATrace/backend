package com.abelium.inatrace.api.formatters;

import java.text.ParseException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class InstantFormatter implements Formatter<Instant> {
	
    @Override
    public String print(Instant object, Locale locale) {
        return object != null ? DateTimeFormatter.ISO_INSTANT.format(object) : "";
    }

    @Override
    public Instant parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        if (StringUtils.containsOnly(text, "0123456789")) {
            long timestamp = Long.parseLong(text);
            return Instant.ofEpochMilli(timestamp);
        }
        // parsing via OffsetDateTime allows offsets and alows omiting seconds (also includes stricter ISO_INSTANT format)
        return OffsetDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant(); 
    }
}
