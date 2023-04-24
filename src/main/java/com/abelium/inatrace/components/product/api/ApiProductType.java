package com.abelium.inatrace.components.product.api;

import com.abelium.inatrace.api.ApiTimestampEntity;

import java.util.List;

public class ApiProductType extends ApiTimestampEntity {

    private String name;
    private String code;
    private String description;

    private List<ApiProductTypeTranslation> translations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ApiProductTypeTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<ApiProductTypeTranslation> translations) {
        this.translations = translations;
    }
}
