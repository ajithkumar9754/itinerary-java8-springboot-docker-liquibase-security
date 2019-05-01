package com.company.itinerary.management.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "travel_information")
public class TravelInformation implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name ="travelId", nullable = false, unique = true)
	private Long travelId;

	@Column(name = "source", nullable = false)
	private String sourceCity;

	@Column(name = "destination", nullable = false)
	private String destinationCity;

	@Column(name = "arrival", nullable = false)
	private Timestamp arrivalTime;

	@Column(name = "departure", nullable = false)
	private Timestamp departureTime;

	@Column(name = "no_of_connections", nullable = false)
	private int numberOfConnections;

	@Column(name = "duration", nullable = false)
	private double duration;

	public TravelInformation() {

	}

	public TravelInformation(Long travelId, String sourceCity, String destinationCity, Timestamp arrivalTime,
			Timestamp departureTime, int numberOfConnections, double duration) {
		super();
		this.travelId = travelId;
		this.sourceCity = sourceCity;
		this.destinationCity = destinationCity;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.numberOfConnections = numberOfConnections;
		this.duration = duration;
	}

	public Long getTravelId() {
		return travelId;
	}

	public void setTravelId(Long travelId) {
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

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((travelId == null) ? 0 : travelId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TravelInformation other = (TravelInformation) obj;
		if (travelId == null) {
			if (other.travelId != null)
				return false;
		} else if (!travelId.equals(other.travelId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TravelInformation [sourceCity=" + sourceCity + ", destinationCity=" + destinationCity + ", arrivalTime="
				+ arrivalTime + ", departureTime=" + departureTime + ", numberOfConnections=" + numberOfConnections
				+ ", duration=" + duration + "]";
	}
	
	
	

}
