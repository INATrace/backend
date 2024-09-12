package com.abelium.inatrace.components.analytics;

import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.db.entities.analytics.AnalyticsAggregate;
import com.abelium.inatrace.db.entities.analytics.AnalyticsAggregateItem;
import com.abelium.inatrace.db.entities.analytics.RequestLog;
import com.abelium.inatrace.tools.Queries;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.torpedoquery.jakarta.jpa.Torpedo;
import java.time.Instant;
import java.util.*;

@Lazy
@Component
public class AnalyticsQueries extends BaseService {
	
	@FunctionalInterface
	public interface AggregateUpdate {
		void run();
	}
	
	public AggregateUpdate updaterForList(String key, Map<String, Map<String, Integer>> map) {
		return () -> updateListAggregate(key, map);
	}
	
	public AggregateUpdate updaterFor(String key, Map<String, Integer> map) {
		return () -> updateAggregate(key, map);
	}

	@Transactional
	public List<AnalyticsAggregate> fetchByKey(String key1, String key2) {
		AnalyticsAggregate aProxy = Torpedo.from(AnalyticsAggregate.class);
		Torpedo.where(aProxy.getKey1()).eq(key1).and(aProxy.getKey2()).eq(key2);
		return Torpedo.select(aProxy).list(em);
	}
	
	@Transactional
	public List<AnalyticsAggregate> fetchByKeys(Collection<String> key1s, String key2) {
		AnalyticsAggregate aProxy = Torpedo.from(AnalyticsAggregate.class);
		Torpedo.where(aProxy.getKey1()).in(key1s).and(aProxy.getKey2()).eq(key2);
		return Torpedo.select(aProxy).list(em);
	}
	
	@Transactional
	public AnalyticsAggregate fetchUniqueByKey(String key1, String key2) {
		AnalyticsAggregate aProxy = Torpedo.from(AnalyticsAggregate.class);
		Torpedo.where(aProxy.getKey1()).eq(key1).and(aProxy.getKey2()).eq(key2);
		Optional<AnalyticsAggregate> result = Torpedo.select(aProxy).get(em);
		return result.orElse(null);
	}

	@Transactional
	public AnalyticsAggregate fetchUniqueByKey(String key1) {
		return Queries.getUniqueBy(em, AnalyticsAggregate.class, AnalyticsAggregate::getKey1, key1);
		
	}

	@Transactional
	public List<RequestLog> fetchRequestLogs(Instant from, int max) {
		RequestLog rlProxy = Torpedo.from(RequestLog.class);
		Torpedo.where(rlProxy.getCreationTimestamp()).gt(from);
		return Torpedo.select(rlProxy).setMaxResults(max).list(em);
	}

	private List<AnalyticsAggregateItem> fetchItemsByKey(AnalyticsAggregate aa, Collection<String> keys) {
		AnalyticsAggregateItem aProxy = Torpedo.from(AnalyticsAggregateItem.class);
		Torpedo.where(aProxy.getItemKey()).in(keys).and(aProxy.getAggregate()).eq(aa);
		return Torpedo.select(aProxy).list(em);
	}

	@Transactional
	public void updateAggregates(Instant maxTimestamp, Collection<AggregateUpdate> updaters) { 
		for (AggregateUpdate au : updaters) {
			au.run();
		}
		
		AnalyticsAggregate aggTimestamp = fetchUniqueByKey("lastTimestamp");
		if (aggTimestamp != null) {
			aggTimestamp.setTimestampValue(maxTimestamp);
		} else {
			aggTimestamp = new AnalyticsAggregate();
			aggTimestamp.setKey1("lastTimestamp");
			aggTimestamp.setTimestampValue(maxTimestamp);
			em.persist(aggTimestamp);
		}
	}

	private void updateListAggregate(String key2, Map<String, Map<String, Integer>> key1ValueMap) {
		List<AnalyticsAggregate> aggList = fetchByKeys(key1ValueMap.keySet(), key2);
		Set<String> dbKeys = new HashSet<>();
		
		for (AnalyticsAggregate a : aggList) {
			updateAggregateItems(a, key1ValueMap.get(a.getKey1()));
			dbKeys.add(a.getKey1());
		}
		for (var e : key1ValueMap.entrySet()) {
			if (!dbKeys.contains(e.getKey())) {
				AnalyticsAggregate agg = new AnalyticsAggregate();
				agg.setKey1(e.getKey());
				agg.setKey2(key2);
				em.persist(agg);
				createAggregateItems(agg, e.getValue());
			}
		}
	}

	private void createAggregateItems(AnalyticsAggregate a, Map<String, Integer> map) {
		for (var e : map.entrySet()) {
			AnalyticsAggregateItem ai = new AnalyticsAggregateItem();
			ai.setAggregate(a);
			ai.setItemKey(e.getKey());
			ai.setIntValue(e.getValue());
			em.persist(ai);
		}
	}

	private void updateAggregateItems(AnalyticsAggregate a, Map<String, Integer> map) {
		List<AnalyticsAggregateItem> items = fetchItemsByKey(a, map.keySet());
		Set<String> dbKeys = new HashSet<>();
		
		for (AnalyticsAggregateItem ai : items) {
			ai.setIntValue(ai.getIntValue() + map.get(ai.getItemKey()));
			dbKeys.add(ai.getItemKey());
		}
		for (var e : map.entrySet()) {
			if (!dbKeys.contains(e.getKey())) {
				AnalyticsAggregateItem ai = new AnalyticsAggregateItem();
				ai.setAggregate(a);
				ai.setItemKey(e.getKey());
				ai.setIntValue(e.getValue());
				em.persist(ai);
			}
		}
	}

	private void updateAggregate(String key2, Map<String, Integer> key1ValueMap) {
		List<AnalyticsAggregate> aggList = fetchByKeys(key1ValueMap.keySet(), key2);
		Set<String> dbKeys = new HashSet<>();
		
		for (AnalyticsAggregate a : aggList) {
			a.setIntValue(a.getIntValue() + key1ValueMap.get(a.getKey1()));
			dbKeys.add(a.getKey1());
		}
		for (var e : key1ValueMap.entrySet()) {
			if (!dbKeys.contains(e.getKey())) {
				AnalyticsAggregate agg = new AnalyticsAggregate();
				agg.setKey1(e.getKey());
				agg.setKey2(key2);
				agg.setIntValue(e.getValue());
				em.persist(agg);
			}
		}
	}

	@Transactional
	public List<AnalyticsAggregateItem> fetchItems(AnalyticsAggregate aa) {
		return Queries.getAllBy(em, AnalyticsAggregateItem.class, AnalyticsAggregateItem::getAggregate, aa);
	}

}
