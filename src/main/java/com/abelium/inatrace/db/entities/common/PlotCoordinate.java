package com.abelium.inatrace.db.entities.common;

import com.abelium.inatrace.db.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * Singe coordinate of a plot.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
@Entity
public class PlotCoordinate extends BaseEntity {

	@Column
	private Double latitude;

	@Column
	private Double longitude;

	@ManyToOne
	private Plot plot;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

}
