package com.abelium.INATrace.components.analytics;

import java.util.List;

import com.abelium.INATrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.INATrace.db.entities.RequestLog;

public class Aggregator {

	public void update(RequestLog rl) {
	}

	public List<AggregateUpdate> getUpdaters(AnalyticsQueries analyticsQueries, String keyPrefix) {
		return List.of();
	}
}
