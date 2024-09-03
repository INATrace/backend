package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.product.api.ApiProductType;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Validated
public class ApiPlot extends ApiBaseEntity {

	@ApiModelProperty(value = "Plot name", position = 1)
	private String plotName;

	@ApiModelProperty(value = "Product type of this plot", position = 2)
	private ApiProductType crop;

	@ApiModelProperty(value = "Number of plants", position = 3)
	public Integer numberOfPlants;

	@ApiModelProperty(value = "The unit in which the size of the plot is expressed", position = 4)
	private String unit;

	@ApiModelProperty(value = "The total size of the plot", position = 5)
	private Double size;

	@ApiModelProperty(value = "The Geoid of the plot", position = 6)
	private String geoId;

	@ApiModelProperty(value = "Date of transitioning into organic production", position = 7)
	private Date organicStartOfTransition;

	@ApiModelProperty(value = "The list of coordinates of the plot", position = 8)
	private List<ApiPlotCoordinate> coordinates;

	@ApiModelProperty(value = "Date of last update", position = 9)
	private Date lastUpdated;

	@ApiModelProperty(value = "Date of last update", position = 10)
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
