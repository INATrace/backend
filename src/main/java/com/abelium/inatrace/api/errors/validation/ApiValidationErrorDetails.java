package com.abelium.inatrace.api.errors.validation;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiValidationErrorDetails implements Serializable {
    private static final long serialVersionUID = 1;
    
    /**
     * Type name of the enclosing object, e.g. "ApiUser".
     */
    private String className = null;

    /**
     * Validation error messages by JSON fields, e.g.
     * <code>{ "age": "Must be between 18 and 120", "name": "Must be a non-empty string" }</code>
     */
    private Map<String, String> fieldErrors = new LinkedHashMap<String, String>();
    
    protected ApiValidationErrorDetails() {}

    public ApiValidationErrorDetails(String className, Map<String, String> fieldErrors) {
        this.className = className;
        this.fieldErrors = fieldErrors;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
