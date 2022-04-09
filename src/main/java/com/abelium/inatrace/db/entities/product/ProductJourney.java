package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.converters.MarkerListConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class ProductJourney extends BaseEntity {
    
    @Convert(converter = MarkerListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<JourneyMarker> markers;
    
    public List<JourneyMarker> getMarkers() {
        return markers;
    }
    
    public void setMarkers(List<JourneyMarker> markers) {
        this.markers = markers;
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
    }
}
