package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.components.product.api.ApiProductJourneyMarker;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * API model for QR tag history timeline (used in public B2C page). Holds only the public data for Stock order history.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiHistoryTimeline {

	@Schema(description = "The items in the history timeline")
	private List<ApiHistoryTimelineItem> items;
    
    @Schema(description = "The items in the journey of history timeline")
    private List<ApiProductJourneyMarker> journeyMarkers;

	public List<ApiHistoryTimelineItem> getItems() {
		if (items == null) {
			items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<ApiHistoryTimelineItem> items) {
		this.items = items;
	}

    public List<ApiProductJourneyMarker> getJourneyMarkers() {
        if (journeyMarkers == null) {
            journeyMarkers = new ArrayList<>();
        }
        return journeyMarkers;
    }
    
    public void setJourneyMarkers(List<ApiProductJourneyMarker> journeyMarkers) {
        this.journeyMarkers = journeyMarkers;
    }
}
