package com.company.itinerary.management.model;

import java.io.Serializable;

public class TravelInfoUpdateDTO extends TravelInfoBaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long travelId;

	public TravelInfoUpdateDTO() {
		super();
	}

	public Long getTravelId() {
		return travelId;
	}

	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}

	public TravelInfoUpdateDTO(Long travelId) {
		super();
		this.travelId = travelId;
	}

}
