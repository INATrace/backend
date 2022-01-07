package com.abelium.inatrace.api.errors;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class HtmlPageException extends Exception 
{
    private HttpStatus status;
    private String text;
    
    public HtmlPageException(HttpStatus status) {
        this(status, null);
    }
    
    public HtmlPageException(HttpStatus status, String text) {
        this.status = status;
        this.text = text;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }
}
