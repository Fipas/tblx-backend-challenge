package io.techhublisbon.api.model;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "traces", type = "trace")
public class Trace {

	@Id
	private String id;

	@Field(name = "@timestamp", type = FieldType.Date)
	private Instant timestamp;
	private Long lineId;
	private Long direction;
	private String journeyPatternId;

	@Field(type = FieldType.Date)
	private LocalDateTime timeframe;
	private Long vehicleJourneyID;
	private String operator;
	private Boolean congestion;
	private Double longitude;
	private Double latitude;
	private Long delay;
	private Long blockId;
	private Long vehicleId;
	private Long stopId;
	private Boolean atStop;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public Long getDirection() {
		return direction;
	}

	public void setDirection(Long direction) {
		this.direction = direction;
	}

	public String getJourneyPatternId() {
		return journeyPatternId;
	}

	public void setJourneyPatternId(String journeyPatternId) {
		this.journeyPatternId = journeyPatternId;
	}

	public LocalDateTime getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(LocalDateTime timeframe) {
		this.timeframe = timeframe;
	}

	public Long getVehicleJourneyID() {
		return vehicleJourneyID;
	}

	public void setVehicleJourneyID(Long vehicleJourneyID) {
		this.vehicleJourneyID = vehicleJourneyID;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Boolean getCongestion() {
		return congestion;
	}

	public void setCongestion(Boolean congestion) {
		this.congestion = congestion;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public Boolean getAtStop() {
		return atStop;
	}

	public void setAtStop(Boolean atStop) {
		this.atStop = atStop;
	}

}
