package com.abelium.inatrace.components.analytics;

import com.abelium.inatrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.inatrace.db.entities.analytics.RequestLog;

import java.util.List;

public class Aggregator {

	public void update(RequestLog rl) {
	}

	public List<AggregateUpdate> getUpdaters(AnalyticsQueries analyticsQueries, String keyPrefix) {
		return List.of();
	}
}
