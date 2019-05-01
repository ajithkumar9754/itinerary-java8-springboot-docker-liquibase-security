package com.company.itinerary.management.service;

import java.util.List;

import com.company.itinerary.management.exception.ItineraryException;
import com.company.itinerary.management.model.TravelInfoBaseDTO;
import com.company.itinerary.management.model.TravelInfoResponse;
import com.company.itinerary.management.model.TravelInfoUpdateDTO;

public interface ITravelService {

	public String createTravelInformation(TravelInfoBaseDTO travelInfoVO) throws ItineraryException;

	public List<TravelInfoResponse> getTravelInformations(String sourceCity, String destinationCity)
			throws ItineraryException;

	public String updateTravelInformation(TravelInfoUpdateDTO travelInfoUpdateDTO) throws ItineraryException;

	public TravelInfoResponse getTravelInformationById(Long travelId) throws ItineraryException;
	
	public TravelInfoResponse findFastestRouteInformation(String sourceCity, String destinationCity)
			throws ItineraryException;
	

}
