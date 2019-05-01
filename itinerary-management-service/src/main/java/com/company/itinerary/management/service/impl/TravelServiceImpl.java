package com.company.itinerary.management.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.company.itinerary.management.constants.ErrorConstants;
import com.company.itinerary.management.constants.ItineraryConstants;
import com.company.itinerary.management.controllers.TravelManagementController;
import com.company.itinerary.management.entities.TravelInformation;
import com.company.itinerary.management.exception.ItineraryException;
import com.company.itinerary.management.model.TravelInfoBaseDTO;
import com.company.itinerary.management.model.TravelInfoResponse;
import com.company.itinerary.management.model.TravelInfoUpdateDTO;
import com.company.itinerary.management.repositories.TravelInformationRepository;
import com.company.itinerary.management.service.ITravelService;
import com.company.itinerary.management.util.DateUtils;

@Service
public class TravelServiceImpl implements ITravelService {

	Logger LOGGER = LoggerFactory.getLogger(TravelManagementController.class);

	private Environment env;
	private TravelInformationRepository travelInformationRepository;

	public TravelServiceImpl(TravelInformationRepository travelInformationRepository, Environment env) {
		this.env = env;
		this.travelInformationRepository = travelInformationRepository;

	}

	@Override
	public String createTravelInformation(TravelInfoBaseDTO travelInfoVO) throws ItineraryException {

		LOGGER.info("Invoking service {createTravelInformation }");

		TravelInformation travelInformation = new TravelInformation();

		travelInformation.setSourceCity(travelInfoVO.getSourceCity().toLowerCase());
		travelInformation.setDestinationCity(travelInfoVO.getDestinationCity().toLowerCase());
		Timestamp arrivalTime = DateUtils.getTimeStampFromString(travelInfoVO.getArrivalTime());
		Timestamp departureTime = DateUtils.getTimeStampFromString(travelInfoVO.getDepartureTime());
		travelInformation.setDepartureTime(departureTime);
		travelInformation.setArrivalTime(arrivalTime);
		travelInformation.setDuration(DateUtils.getDurationBetweenTimeInHours(departureTime, arrivalTime));
		travelInformation.setNumberOfConnections(travelInfoVO.getNumberOfConnections());
		// travelInformation.setDuration(5);

		LOGGER.info("{travelInformation }" + travelInformation.toString());

		travelInformationRepository.save(travelInformation);

		LOGGER.info("{travelInformation saved successfully}");

		return env.getProperty(ItineraryConstants.TRAVEL_INFO_CREATED_SUCCESSFULLY);

	}

	@Override
	public List<TravelInfoResponse> getTravelInformations(String sourceCity, String destinationCity)
			throws ItineraryException {

		LOGGER.info("Invoking service getTravelInformations  { Source City }" + sourceCity + " {DestinationCity  }"
				+ destinationCity);

		List<TravelInformation> travelInformationList = travelInformationRepository
				.findAllTravelInformations(sourceCity, destinationCity);

		List<TravelInfoResponse> TravelInfoList = travelInformationList.stream().map(TravelInfoResponse::new)
				.collect(Collectors.toList());

		return TravelInfoList;

	}

	@Override
	public String updateTravelInformation(TravelInfoUpdateDTO travelInfoUpdateDTO) throws ItineraryException {

		LOGGER.info("Invoking service {updateTravelInformation } ");

		Optional<TravelInformation> travelInformation = travelInformationRepository
				.findById(travelInfoUpdateDTO.getTravelId());

		if (!travelInformation.isPresent()) {
			LOGGER.error("TravelInformation not found");
			throw new ItineraryException(ErrorConstants.TRAVEL_INFORMATION_NOT_FOUND);
		} else {

			LOGGER.error("TravelInformation is found to update for TravelID" + travelInfoUpdateDTO.getTravelId());

			TravelInformation travelInformationTobeUpdate = new TravelInformation();
			travelInformationTobeUpdate.setTravelId(travelInfoUpdateDTO.getTravelId());
			travelInformationTobeUpdate.setSourceCity(travelInfoUpdateDTO.getSourceCity().toLowerCase());
			travelInformationTobeUpdate.setDestinationCity(travelInfoUpdateDTO.getDestinationCity().toLowerCase());
			Timestamp arrivalTime = DateUtils.getTimeStampFromString(travelInfoUpdateDTO.getArrivalTime());
			Timestamp departureTime = DateUtils.getTimeStampFromString(travelInfoUpdateDTO.getDepartureTime());
			travelInformationTobeUpdate.setDepartureTime(departureTime);
			travelInformationTobeUpdate.setArrivalTime(arrivalTime);
			travelInformationTobeUpdate
					.setDuration(DateUtils.getDurationBetweenTimeInHours(departureTime, arrivalTime));
			travelInformationTobeUpdate.setNumberOfConnections(travelInfoUpdateDTO.getNumberOfConnections());

			travelInformationRepository.save(travelInformationTobeUpdate);

			LOGGER.info("{travelInformation updated successfully}");
		}
		return env.getProperty(ItineraryConstants.TRAVEL_INFO_UPDATED_SUCCESSFULLY);
	}

	@Override
	public TravelInfoResponse getTravelInformationById(Long travelId) throws ItineraryException {

		LOGGER.info("Invoking service getTravelInformation  with { travelId}");

		Optional<TravelInformation> travelInformationOptional = travelInformationRepository.findById(travelId);
		TravelInfoResponse travelInfoResponse = null;
		if (!travelInformationOptional.isPresent()) {
			LOGGER.error("TravelInformation not found");
			throw new ItineraryException(ErrorConstants.TRAVEL_INFORMATION_NOT_FOUND);
		} else {
			TravelInformation travelInformation = travelInformationOptional.get();
			travelInfoResponse = new TravelInfoResponse();

			travelInfoResponse.setTravelId(travelInformation.getTravelId());
			travelInfoResponse.setSourceCity(travelInformation.getSourceCity());
			travelInfoResponse.setDestinationCity(travelInformation.getDestinationCity());
			travelInfoResponse.setArrivalTime(travelInformation.getArrivalTime().toString());
			travelInfoResponse.setDepartureTime(travelInformation.getDepartureTime().toString());
			travelInfoResponse.setDuration(travelInformation.getDuration());
			travelInfoResponse.setNumberOfConnections(travelInformation.getNumberOfConnections());

		}

		return travelInfoResponse;

	}

	@Override
	public TravelInfoResponse findFastestRouteInformation(String sourceCity, String destinationCity)
			throws ItineraryException {

		LOGGER.info("Invoking service findFastestRouteInformation  with { source and destinationCity}");

		TravelInformation travelInformationRecieved = travelInformationRepository
				.findFastestRouteInformation(sourceCity, destinationCity);
		TravelInfoResponse travelInfoResponse = null;
		if (null == travelInformationRecieved) {
			LOGGER.error("TravelInformation not found");
			throw new ItineraryException(ErrorConstants.TRAVEL_INFORMATION_NOT_FOUND);
		} else {
			travelInfoResponse = new TravelInfoResponse();

			travelInfoResponse.setTravelId(travelInformationRecieved.getTravelId());
			travelInfoResponse.setSourceCity(travelInformationRecieved.getSourceCity());
			travelInfoResponse.setDestinationCity(travelInformationRecieved.getDestinationCity());
			travelInfoResponse.setArrivalTime(travelInformationRecieved.getArrivalTime().toString());
			travelInfoResponse.setDepartureTime(travelInformationRecieved.getDepartureTime().toString());
			travelInfoResponse.setDuration(travelInformationRecieved.getDuration());
			travelInfoResponse.setNumberOfConnections(travelInformationRecieved.getNumberOfConnections());

		}

		return travelInfoResponse;
	}

}
