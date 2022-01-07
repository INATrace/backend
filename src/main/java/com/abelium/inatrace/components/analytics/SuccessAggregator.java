package com.abelium.inatrace.components.analytics;

import com.abelium.inatrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.inatrace.db.entities.analytics.RequestLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuccessAggregator extends Aggregator {

	private Map<String, Integer> resultTrue = new HashMap<>();
	private Map<String, Integer> resultFalse = new HashMap<>();		
	
	@Override
	public void update(RequestLog rl) {
		if ("true".equals(rl.getValue2())) {
			resultTrue.compute(rl.getLogKey(), (k, v) -> v == null ? 1 : v + 1);
		} else {
			resultFalse.compute(rl.getLogKey(), (k, v) -> v == null ? 1 : v + 1);
		}
	}
	
	@Override
	public List<AggregateUpdate> getUpdaters(AnalyticsQueries analyticsQueries, String keyPrefix) {
		return List.of(analyticsQueries.updaterFor(keyPrefix + "True", resultTrue),
				analyticsQueries.updaterFor(keyPrefix + "False", resultFalse));	
	}

}
