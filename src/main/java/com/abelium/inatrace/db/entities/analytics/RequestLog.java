package com.abelium.inatrace.db.entities.analytics;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.base.CreationTimestampEntity;
import com.abelium.inatrace.types.RequestLogType;
import org.flywaydb.core.internal.util.StringUtils;

import jakarta.persistence.*;

@Entity
@Table(indexes = { @Index(columnList = "creationTimestamp") })
public class RequestLog extends CreationTimestampEntity {

    /**
     * Request ip address
     */
    @Column(length = Lengths.IP_6)
    private String ip;
    
    /**
     * Type of request, can be used for filtering
     */
    @Enumerated(EnumType.STRING)
    @Column(length = Lengths.ENUM)
    private RequestLogType type;
    
    /**
     * "Key" of the request (64 characters) 
     * TODO: If used for querying, add index! 
     */
    @Column(length = Lengths.REQUEST_LOG_KEY)
    private String logKey;
    
    /**
     * Geolocation, if available 
     */
    @Embedded
    private RequestGeoLocation geoLocation;

    /**
     * Optional value
     */
    @Column(length = Lengths.DEFAULT)
    private String value1;
    
    /**
     * Optional response 
     */
    @Column(length = Lengths.DEFAULT)
    private String value2;

    protected RequestLog() {
    }

    public RequestLog(String ip, RequestLogType type, String logKey, RequestGeoLocation geoLocation) {
		super();
		this.ip = ip;
		this.type = type;
		this.logKey = StringUtils.left(logKey, Lengths.REQUEST_LOG_KEY);
		this.geoLocation = geoLocation;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public RequestLogType getType() {
		return type;
	}

	public void setType(RequestLogType type) {
		this.type = type;
	}

	public RequestGeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(RequestGeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getLogKey() {
		return logKey;
	}

	public void setLogKey(String logKey) {
		this.logKey = StringUtils.left(logKey, Lengths.REQUEST_LOG_KEY);
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = StringUtils.left(value1, Lengths.DEFAULT);
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = StringUtils.left(value2, Lengths.DEFAULT);
	}
}
