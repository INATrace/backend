package com.abelium.inatrace.tools.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class SimpleDateConverter {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    public static class Deserialize extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonParseException {
            String date = jsonParser.getValueAsString();
            try {

                SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                return dateFormat.parse(date).toInstant();
            } catch (ParseException e) {
                throw new IOException("Failed parsing input date. Date '" + date + "' should be in '" + SIMPLE_DATE_FORMAT + "' format.");
            }
        }
    }

    public static class Serialize extends StdConverter<Instant, String> {
        @Override
        public String convert(Instant instant) {
            return new SimpleDateFormat(SIMPLE_DATE_FORMAT).format(Date.from(instant));
        }
    }
}
