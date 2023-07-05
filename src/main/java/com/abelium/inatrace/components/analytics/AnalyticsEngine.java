package com.abelium.inatrace.components.analytics;

import com.abelium.inatrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.inatrace.components.product.api.ApiProductLabelAnalytics;
import com.abelium.inatrace.db.entities.analytics.RequestLog;
import com.abelium.inatrace.db.entities.analytics.AnalyticsAggregate;
import com.abelium.inatrace.db.entities.analytics.AnalyticsAggregateItem;
import com.abelium.inatrace.types.RequestLogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class AnalyticsEngine {
	
    protected final Logger logger = LoggerFactory.getLogger(AnalyticsEngine.class);
	
	
	@Autowired
	private AnalyticsQueries analyticsQueries; 
	
	@Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 1000)
	public void aggregateAll() {
		logger.debug("Running aggregations");
		Instant from = Instant.ofEpochMilli(0);
		AnalyticsAggregate ag = analyticsQueries.fetchUniqueByKey("lastTimestamp");
		
		if (ag != null) {
			from = ag.getTimestampValue();
		}
		List<RequestLog> logs = analyticsQueries.fetchRequestLogs(from, 100);
		
		if (logs.isEmpty()) {
			return;
		}

		List<AggregateUpdate> updaters = new ArrayList<>();
		Optional<Instant> maxTimestamp = logs.stream().map(RequestLog::getCreationTimestamp).max(Instant::compareTo);
		
		updaters.addAll(updateAggregateList(RequestLogType.VISIT_QR, logs, "qrVisit",
				List.of(new LatLonAggregator(), new CountryAggregator(), new CountAggregator())));
		updaters.addAll(updateAggregateList(RequestLogType.VERIFY_BATCH, logs, "check",
				List.of(new LatLonAggregator(), new CountryAggregator(), new SuccessAggregator())));
		updaters.addAll(updateAggregateList(RequestLogType.VERIFY_BATCH_ORIGIN, logs, "origin",
				List.of(new LatLonAggregator(), new CountryAggregator(), new SuccessAggregator())));
		
		analyticsQueries.updateAggregates(maxTimestamp.get(), updaters);
		logger.debug("Finished running aggregations");
	}
	
	public ApiProductLabelAnalytics createAnalyticsForLabel(String labelUid) {
		ApiProductLabelAnalytics result = new ApiProductLabelAnalytics();

		result.visits = fetchCount(labelUid, "qrVisitCount");
		result.visitsLocations = fetchCountMap(labelUid, "qrVisitLatLon");
		result.visitsCountries = fetchCountMap(labelUid, "qrVisitCountry");
		result.authTrue = fetchCount(labelUid, "checkTrue");
		result.authFalse = fetchCount(labelUid, "checkFalse");
		result.authLocations = fetchCountMap(labelUid, "checkLatLon");
		result.authCountries = fetchCountMap(labelUid, "checkCountry");
		result.originTrue = fetchCount(labelUid, "originTrue");
		result.originFalse = fetchCount(labelUid, "originFalse");
		result.originLocations = fetchCountMap(labelUid, "originLatLon");
		result.originCountries = fetchCountMap(labelUid, "originCountry");
		return result;
	}
	
	private int fetchCount(String labelUid, String key) {
		AnalyticsAggregate agg = analyticsQueries.fetchUniqueByKey(labelUid, key);
		return agg == null ? 0 : agg.getIntValue();
	}
	
	private Map<String, Integer> fetchCountMap(String labelUid, String key) {
		AnalyticsAggregate agg = analyticsQueries.fetchUniqueByKey(labelUid, key);
		if (agg == null) {
			return new HashMap<>();
		} else {
			List<AnalyticsAggregateItem> items = analyticsQueries.fetchItems(agg);
			return items.stream().collect(Collectors.toMap(AnalyticsAggregateItem::getItemKey, AnalyticsAggregateItem::getIntValue));
		} 
	}
	
	// Use for counting only
	protected List<AggregateUpdate> updateAggregate(RequestLogType type, List<RequestLog> logs, String key) {
		Map<String, Integer> result = new HashMap<>();
		
		Iterator<RequestLog> iter = logs.listIterator();

		while (iter.hasNext()) {
			RequestLog rl = iter.next();
			if (rl.getType() == type) {
				result.compute(rl.getLogKey(), (k, v) -> (v == null) ? 1 : v + 1);
				iter.remove();
			}
		}
		return List.of(analyticsQueries.updaterFor(key, result));
	}
	
	protected List<AggregateUpdate> updateAggregateList(RequestLogType type, List<RequestLog> logs, String keyPrefix,
			Collection<Aggregator> aggregators) {
		Iterator<RequestLog> iter = logs.listIterator();

		while (iter.hasNext()) {
			RequestLog rl = iter.next();
			if (rl.getType() == type) {
				for (Aggregator agg : aggregators) {
					agg.update(rl);
				}
				iter.remove();
			}
		}
		
		List<AggregateUpdate> updaters = new ArrayList<>();
		
		for (Aggregator agg : aggregators) {
			updaters.addAll(agg.getUpdaters(analyticsQueries, keyPrefix));
		}
		return updaters;
	}
	
}
