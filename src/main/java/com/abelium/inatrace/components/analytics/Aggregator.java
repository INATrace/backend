package com.abelium.inatrace.components.analytics;

import java.util.List;

import com.abelium.inatrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.inatrace.db.entities.RequestLog;

public class Aggregator {

	public void update(RequestLog rl) {
	}

	public List<AggregateUpdate> getUpdaters(AnalyticsQueries analyticsQueries, String keyPrefix) {
		return List.of();
	}
}
