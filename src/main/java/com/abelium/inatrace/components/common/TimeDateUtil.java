package com.abelium.inatrace.components.common;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class TimeDateUtil {

    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    public static Instant toInstant(String date, String format) throws ApiException {
        try {
            return new SimpleDateFormat(format).parse(date).toInstant();
        } catch (ParseException e) {
            throw new ApiException(ApiStatus.INVALID_REQUEST,
                    "Provided date '" + date + "' is invalid. It should be in '" + format + "' format.");
        }
    }

    public static Instant toInstantOrNull(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date).toInstant();
        } catch (ParseException e) {
            return null;
        }
    }

    public static String fromInstant(Instant date, String format) {
        return new SimpleDateFormat(format).format(Date.from(date));
    }

}
