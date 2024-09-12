package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.converters.MarkerListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ProductJourney extends BaseEntity {
    
    @Convert(converter = MarkerListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<JourneyMarker> markers = new ArrayList<>();
    
    public List<JourneyMarker> getMarkers() {
        return markers;
    }
    
    public void setMarkers(List<JourneyMarker> markers) {
        this.markers = markers;
    }
    
    public ProductJourney copy() {
        ProductJourney j = new ProductJourney();
        j.setMarkers(getMarkers().stream().map(JourneyMarker::copy).collect(Collectors.toList()));
        return j;
    }
    
    public static class JourneyMarker {
        
        public static final String DELIMITOR = ",";
        
        public static JourneyMarker fromString(String value) {
            String[] parts = value.split(DELIMITOR);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid string format! Should be 'longitude,latitude'.");
            }
            try {
                JourneyMarker marker = new JourneyMarker();
                marker.setLongitude(Double.parseDouble(parts[0]));
                marker.setLatitude(Double.parseDouble(parts[1]));
                return marker;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid string format! Should be 'double,double'.");
            }
        }
        
        private double latitude;
        
        private double longitude;
        
        public double getLatitude() {
            return latitude;
        }
        
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
        
        public double getLongitude() {
            return longitude;
        }
        
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
        
        @Override
        public String toString() {
            return longitude + DELIMITOR + latitude;
        }
        
        public JourneyMarker copy() {
            JourneyMarker marker = new JourneyMarker();
            marker.setLatitude(getLatitude());
            marker.setLongitude(getLongitude());
            return marker;
        }
    }
}
