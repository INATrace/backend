package com.abelium.inatrace.components.stockorder.api;

import com.abelium.inatrace.db.entities.codebook.MeasureUnitType;

import java.time.LocalDate;
import java.util.List;

public class ApiProcessingPerformanceRequest {

	private Long companyId;
	private Long facilityId;
	private Long processActionId;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	private List<ApiProcessingPerformanceRequestEvidenceField> evidenceFields;
	private ApiAggregationTimeUnit aggregationType;

	private MeasureUnitType measureUnitType;

	public ApiProcessingPerformanceRequest(Long companyId, Long facilityId, Long processActionId, LocalDate dateStart,
	                                       LocalDate dateEnd,
	                                       List<ApiProcessingPerformanceRequestEvidenceField> evidenceFields,
	                                       ApiAggregationTimeUnit aggregationType, MeasureUnitType measureUnitType) {
		this.companyId = companyId;
		this.facilityId = facilityId;
		this.processActionId = processActionId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.evidenceFields = evidenceFields;
		this.aggregationType = aggregationType;
		this.measureUnitType = measureUnitType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Long getProcessActionId() {
		return processActionId;
	}

	public void setProcessActionId(Long processActionId) {
		this.processActionId = processActionId;
	}

	public LocalDate getDateStart() {
		return dateStart;
	}

	public void setDateStart(LocalDate dateStart) {
		this.dateStart = dateStart;
	}

	public LocalDate getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}

	public List<ApiProcessingPerformanceRequestEvidenceField> getEvidenceFields() {
		return evidenceFields;
	}

	public void setEvidenceFields(List<ApiProcessingPerformanceRequestEvidenceField> evidenceFields) {
		this.evidenceFields = evidenceFields;
	}

	public ApiAggregationTimeUnit getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(ApiAggregationTimeUnit aggregationType) {
		this.aggregationType = aggregationType;
	}

	public MeasureUnitType getMeasureUnitType() {
		return measureUnitType;
	}

	public void setMeasureUnitType(MeasureUnitType measureUnitType) {
		this.measureUnitType = measureUnitType;
	}
}
