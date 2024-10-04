package com.abelium.inatrace.components.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.time.Duration;
import java.util.UUID;

public class StorageKeyCache {

	private static final Cache<String, CacheValue> cache = CacheBuilder.newBuilder().
			expireAfterWrite(Duration.ofMinutes(120)).
			build();

	public static class CacheValue {
		String dbStorageKey;	// Storage key in the database
		Long userId;			// user allowed to access a document, null if public
		
		public CacheValue(String dbStorageKey, Long userId) {
			this.dbStorageKey = dbStorageKey;
			this.userId = userId;
		}
	}

	public static String put(String storageKey, Long userId) {
		if (storageKey == null) return null;
		
		String tempKey = UUID.randomUUID().toString();
		cache.put(tempKey, new CacheValue(storageKey, userId));
		return tempKey;
	}

	public static String get(String tempKey, Long userId) {
		CacheValue value = cache.getIfPresent(tempKey);
		return (value != null && (value.userId == null || value.userId.equals(userId))) ? value.dbStorageKey : null;
	}
	
}
