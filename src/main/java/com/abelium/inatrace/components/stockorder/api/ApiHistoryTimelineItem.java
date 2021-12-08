package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.types.PublicTimelineIconType;

import java.util.Date;

/**
 * API model representing single item (processing) in the QR tag history timeline.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiHistoryTimelineItem {

	private String type;

	private String name;

	private Date date;

	private String location;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

}
