package com.abelium.inatrace.components.stockorder;

import java.time.LocalDate;

public class ProcessingPerformanceQueryRequest {

	public Long companyId;
	public Long facilityId;
	public Long processActionId;
	public LocalDate dateStart;
	public LocalDate dateEnd;

	public ProcessingPerformanceQueryRequest(Long companyId, Long facilityId, Long processActionId, LocalDate dateStart,
	                                         LocalDate dateEnd) {
		this.companyId = companyId;
		this.facilityId = facilityId;
		this.processActionId = processActionId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
}
