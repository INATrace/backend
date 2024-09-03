package com.abelium.inatrace.components.company.mappers;

import com.abelium.inatrace.components.company.api.ApiPlot;
import com.abelium.inatrace.components.company.api.ApiPlotCoordinate;
import com.abelium.inatrace.components.product.ProductTypeMapper;
import com.abelium.inatrace.db.entities.common.Plot;
import com.abelium.inatrace.db.entities.common.PlotCoordinate;
import com.abelium.inatrace.types.Language;

import java.util.stream.Collectors;

public class PlotMapper {

	public static ApiPlot toApiPlot(Plot plot, Language language) {

		if (plot == null) {
			return null;
		}

		ApiPlot apiPlot = new ApiPlot();

		apiPlot.setId(plot.getId());
		apiPlot.setPlotName(plot.getPlotName());
		apiPlot.setCrop(ProductTypeMapper.toApiProductType(plot.getCrop(), language));
		apiPlot.setNumberOfPlants(plot.getNumberOfPlants());
		apiPlot.setUnit(plot.getUnit());
		apiPlot.setSize(plot.getSize());
		apiPlot.setGeoId(plot.getGeoId());
		apiPlot.setOrganicStartOfTransition(plot.getOrganicStartOfTransition());
		apiPlot.setCoordinates(
				plot.getCoordinates().stream().map(PlotMapper::toApiPlotCoordinate).collect(Collectors.toList()));
		apiPlot.setLastUpdated(plot.getLastUpdated());

		return apiPlot;
	}

	public static ApiPlotCoordinate toApiPlotCoordinate(PlotCoordinate plotCoordinate) {

		if (plotCoordinate == null) {
			return null;
		}

		ApiPlotCoordinate apiPlotCoordinate = new ApiPlotCoordinate();
		apiPlotCoordinate.setId(plotCoordinate.getId());
		apiPlotCoordinate.setLatitude(plotCoordinate.getLatitude());
		apiPlotCoordinate.setLongitude(plotCoordinate.getLongitude());

		return apiPlotCoordinate;
	}

}
