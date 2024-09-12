package com.abelium.inatrace.db.converters;

import com.abelium.inatrace.db.entities.product.ProductJourney;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class MarkerListConverter implements AttributeConverter<List<ProductJourney.JourneyMarker>, String> {
    
    public static final String LIST_DELIMITOR = ";";
    
    @Override
    public String convertToDatabaseColumn(List<ProductJourney.JourneyMarker> attribute) {
        if (attribute == null || attribute.size() == 0) {
            return "";
        }
        return attribute.stream()
            .map(ProductJourney.JourneyMarker::toString)
            .collect(Collectors.joining(LIST_DELIMITOR));
    }
    
    @Override
    public List<ProductJourney.JourneyMarker> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty() || dbData.isBlank()) {
            return new ArrayList<>();
        }
        return Arrays.stream(dbData.split(LIST_DELIMITOR))
            .map(ProductJourney.JourneyMarker::fromString)
            .collect(Collectors.toList());
    }
    
}
