package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.types.PublicTimelineIconType;

import java.time.LocalDate;

/**
 * API model representing single item (processing) in the QR tag history timeline.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiHistoryTimelineItem {

	private String type;

	private String name;

	private LocalDate date;

	private String location;
    
    private Double longitude;
    
    private Double latitude;

	private Integer step;

	private Integer steps;

	private PublicTimelineIconType iconType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public PublicTimelineIconType getIconType() {
		return iconType;
	}

	public void setIconType(PublicTimelineIconType iconType) {
		this.iconType = iconType;
	}
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}
}
