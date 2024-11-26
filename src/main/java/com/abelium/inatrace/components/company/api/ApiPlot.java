package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.product.api.ApiProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Validated
public class ApiPlot extends ApiBaseEntity {

	@Schema(description = "Plot name")
	private String plotName;

	@Schema(description = "Product type of this plot")
	private ApiProductType crop;

	@Schema(description = "Number of plants")
	public Integer numberOfPlants;

	@Schema(description = "The unit in which the size of the plot is expressed")
	private String unit;

	@Schema(description = "The total size of the plot")
	private Double size;

	@Schema(description = "The Geoid of the plot")
	private String geoId;

	@Schema(description = "Date of transitioning into organic production")
	private Date organicStartOfTransition;

	@Schema(description = "The list of coordinates of the plot")
	private List<ApiPlotCoordinate> coordinates;

	@Schema(description = "Date of last update")
	private Date lastUpdated;

	@Schema(description = "Date of last update")
	private Long farmerId;

	public String getPlotName() {
		return plotName;
	}

	public void setPlotName(String plotName) {
		this.plotName = plotName;
	}

	public ApiProductType getCrop() {
		return crop;
	}

	public void setCrop(ApiProductType crop) {
		this.crop = crop;
	}

	public Integer getNumberOfPlants() {
		return numberOfPlants;
	}

	public void setNumberOfPlants(Integer numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public String getGeoId() {
		return geoId;
	}

	public void setGeoId(String geoId) {
		this.geoId = geoId;
	}

	public Date getOrganicStartOfTransition() {
		return organicStartOfTransition;
	}

	public void setOrganicStartOfTransition(Date organicStartOfTransition) {
		this.organicStartOfTransition = organicStartOfTransition;
	}

	public List<ApiPlotCoordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<ApiPlotCoordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Long farmerId) {
		this.farmerId = farmerId;
	}

}
