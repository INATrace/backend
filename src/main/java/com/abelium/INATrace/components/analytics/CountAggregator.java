package com.abelium.INATrace.components.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abelium.INATrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.INATrace.db.entities.RequestLog;

public class CountAggregator extends Aggregator {

	private Map<String, Integer> count = new HashMap<>();
	
	@Override
	public void update(RequestLog rl) {
		count.compute(rl.getLogKey(), (k, v) -> v == null ? 1 : v + 1);
	}
	
	@Override
	public List<AggregateUpdate> getUpdaters(AnalyticsQueries analyticsQueries, String keyPrefix) {
		return List.of(analyticsQueries.updaterFor(keyPrefix + "Count", count));	
	}

}
