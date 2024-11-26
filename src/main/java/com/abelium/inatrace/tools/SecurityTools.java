package com.abelium.inatrace.tools;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityTools {
    
    public static String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
    
    public static String getUtf8Header(HttpServletRequest request, String... names) {
        return Stream.of(names)
                .flatMap(name -> ListTools.toList(request.getHeaders(name)).stream())
                .map(SecurityTools::utf8Reencode)
                .collect(Collectors.joining(" "));
    }
    
    // Shibboleth encodes headers as utf-8 while they should be latin1 according to the standard
    public static String utf8Reencode(String value) {
        try {
            return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return value;   // wrong encoding?
        }
    }    
}
