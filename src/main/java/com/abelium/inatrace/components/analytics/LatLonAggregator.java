package com.abelium.inatrace.components.analytics;

import com.abelium.inatrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.inatrace.db.entities.analytics.RequestLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatLonAggregator extends Aggregator {

	private Map<String, Map<String, Integer>> resultLatLon = new HashMap<>();
	
	@Override
	public void update(RequestLog rl) {
		String latLon = "unknown";
		if (rl.getGeoLocation() != null && rl.getGeoLocation().getLatitude() != null && rl.getGeoLocation().getLongitude() != null) {
			latLon = String.format("%.4f:%.4f", rl.getGeoLocation().getLatitude(), rl.getGeoLocation().getLongitude());
		}
		if (!resultLatLon.containsKey(rl.getLogKey())) {
			resultLatLon.put(rl.getLogKey(), new HashMap<>());
		}
		resultLatLon.get(rl.getLogKey()).compute(latLon, (k, v) -> v == null ? 1 : v + 1);
	}
	
	@Override
	public List<AggregateUpdate> getUpdaters(AnalyticsQueries analyticsQueries, String keyPrefix) {
		return List.of(analyticsQueries.updaterForList(keyPrefix + "LatLon", resultLatLon));
	}
}
