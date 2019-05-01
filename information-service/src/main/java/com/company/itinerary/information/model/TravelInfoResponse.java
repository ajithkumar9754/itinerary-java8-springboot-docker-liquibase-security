package com.company.itinerary.information.model;

import java.io.Serializable;

public class TravelInfoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long travelId;
	private String sourceCity;
	private String destinationCity;
	private String arrivalTime;
	private String departureTime;
	private int numberOfConnections;
	private double duration;

	public TravelInfoResponse() {
	}

	public TravelInfoResponse(Long travelId, String sourceCity, String destinationCity, String arrivalTime,
			String departureTime, int numberOfConnections, double duration) {
		super();
		this.travelId = travelId;
		this.sourceCity = sourceCity;
		this.destinationCity = destinationCity;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.numberOfConnections = numberOfConnections;
		this.duration = duration;
	}


	
	
	public long getTravelId() {
		return travelId;
	}

	public void setTravelId(long travelId) {
		this.travelId = travelId;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public int getNumberOfConnections() {
		return numberOfConnections;
	}

	public void setNumberOfConnections(int numberOfConnections) {
		this.numberOfConnections = numberOfConnections;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	
	
}
