package com.abelium.inatrace.components.analytics.api;

public class ApiHogRequest {
	
	public static class ApiHogRequestProperties {
		public String distinct_id;
		public String key1;
		public String key2;
		
		public ApiHogRequestProperties(String distinct_id, String key1, String key2) {
			this.distinct_id = distinct_id;
			this.key1 = key1;
			this.key2 = key2;
		}
	}
	
	public String api_key;
	public String event;
	public ApiHogRequestProperties properties;
	
	public ApiHogRequest(String api_key, String event, ApiHogRequestProperties properties) {
		this.api_key = api_key;
		this.event = event;
		this.properties = properties;
	}
	
}
