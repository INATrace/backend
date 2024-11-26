package com.abelium.inatrace.components.product.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class ApiProductLabelAnalytics {
	
	@Schema(description = "number of vistis")
	public Integer visits;

	@Schema(description = "number of visits per country")
	public Map<String, Integer> visitsCountries = new HashMap<>();

	@Schema(description = "number of visits per lat:lon")
	public Map<String, Integer> visitsLocations = new HashMap<>();

	@Schema(description = "number of successful authenticity checks")
	public Integer authTrue;

	@Schema(description = "number of unsuccessful authenticity checks")
	public Integer authFalse;

	@Schema(description = "number of authenticity checks per country")
	public Map<String, Integer> authCountries = new HashMap<>();

	@Schema(description = "number of authenticity checks per lat:lon")
	public Map<String, Integer> authLocations = new HashMap<>();

	@Schema(description = "number of successful origin checks")
	public Integer originTrue;

	@Schema(description = "number of unsuccessful origin checks")
	public Integer originFalse;

	@Schema(description = "number of origin checks per country")
	public Map<String, Integer> originCountries = new HashMap<>();

	@Schema(description = "number of origin checks per lat:lon")
	public Map<String, Integer> originLocations = new HashMap<>();
	
	
	public Integer getVisits() {
		return visits;
	}

	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	public Integer getAuthTrue() {
		return authTrue;
	}

	public void setAuthTrue(Integer authTrue) {
		this.authTrue = authTrue;
	}

	public Integer getAuthFalse() {
		return authFalse;
	}

	public void setAuthFalse(Integer authFalse) {
		this.authFalse = authFalse;
	}

	public Map<String, Integer> getAuthCountries() {
		return authCountries;
	}

	public void setAuthCountries(Map<String, Integer> authCountries) {
		this.authCountries = authCountries;
	}

	public Map<String, Integer> getAuthLocations() {
		return authLocations;
	}

	public void setAuthLocations(Map<String, Integer> authLocations) {
		this.authLocations = authLocations;
	}

	public Integer getOriginTrue() {
		return originTrue;
	}

	public void setOriginTrue(Integer originTrue) {
		this.originTrue = originTrue;
	}

	public Integer getOriginFalse() {
		return originFalse;
	}

	public void setOriginFalse(Integer originFalse) {
		this.originFalse = originFalse;
	}

	public Map<String, Integer> getOriginCountries() {
		return originCountries;
	}

	public void setOriginCountries(Map<String, Integer> originCountries) {
		this.originCountries = originCountries;
	}

	public Map<String, Integer> getOriginLocations() {
		return originLocations;
	}

	public void setOriginLocations(Map<String, Integer> originLocations) {
		this.originLocations = originLocations;
	}

	public Map<String, Integer> getVisitsCountries() {
		return visitsCountries;
	}

	public void setVisitsCountries(Map<String, Integer> visitsCountries) {
		this.visitsCountries = visitsCountries;
	}

	public Map<String, Integer> getVisitsLocations() {
		return visitsLocations;
	}

	public void setVisitsLocations(Map<String, Integer> visitsLocations) {
		this.visitsLocations = visitsLocations;
	}
}
