package com.company.itinerary.management.controllers;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.itinerary.management.exception.InvalidDataException;
import com.company.itinerary.management.exception.ItineraryException;
import com.company.itinerary.management.model.TravelInfoBaseDTO;
import com.company.itinerary.management.model.TravelInfoResponse;
import com.company.itinerary.management.model.TravelInfoUpdateDTO;
import com.company.itinerary.management.service.ITravelService;
import com.company.itinerary.management.util.DateUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@PropertySource(value = "messages.properties", ignoreResourceNotFound = true)
@Validated
@RequestMapping(path = "/api/v1/management/travelinfo")
public class TravelManagementController {

	@Value("${itinerary.default.city.source}")
	private String sourceCityDefault;

	Logger LOGGER = LoggerFactory.getLogger(TravelManagementController.class);

	private ITravelService travelService;
	private Environment env;

	private static final String SOURCE_CITY = "sourcecity";
	private static final String DESTINATION_CITY = "destinationcity";

	public TravelManagementController(ITravelService travelService, Environment env) {
		this.travelService = travelService;
		this.env = env;

	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create Travel Information")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Travel Information created successfully ") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN_USER')")
	public ResponseEntity<String> createTravelInformation(@Valid @RequestBody TravelInfoBaseDTO travelInfoDTO)
			throws ItineraryException {

		validateTravelInformation(travelInfoDTO);
		String response = this.travelService.createTravelInformation(travelInfoDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/route", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get Shortest route between 2 cities")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fastest Route Informations retrieved successfully") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN_USER','ROLE_TRAVEL_USER')")
	public ResponseEntity<TravelInfoResponse> getshortestPathTravelInformation(
			@RequestParam(name = SOURCE_CITY, required = false) String source,
			@RequestParam(DESTINATION_CITY) String destination) throws ItineraryException {

		TravelInfoResponse travelInfoResponse = this.travelService.findFastestRouteInformation(source, destination);

		if (null == travelInfoResponse) {
			LOGGER.info("There is no Travel information found ");
		}

		return ResponseEntity.ok(travelInfoResponse);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get All the Travel Informations between 2 cities")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Travel Informations retrieved successfully") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN_USER','ROLE_TRAVEL_USER')")
	public ResponseEntity<List<TravelInfoResponse>> getTravelInformations(
			@RequestParam(name = SOURCE_CITY, required = false) String source,
			@RequestParam(DESTINATION_CITY) String destination) throws ItineraryException {

		if (StringUtils.isEmpty(source)) {
			LOGGER.info(
					"Since there is source city specified in the search crieteria , setting default source from properties");
			source = sourceCityDefault;
		}

		List<TravelInfoResponse> travelInformationsList = this.travelService.getTravelInformations(source, destination);

		if (travelInformationsList.isEmpty()) {
			LOGGER.info("There is no Travel informations found for specified search");
		}

		return ResponseEntity.ok(travelInformationsList);
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Travel Information")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Travel Information updated successfully ") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN_USER')")
	public ResponseEntity<String> updateTravelInformation(@Valid @RequestBody TravelInfoUpdateDTO travelInfoUpdateDTO)
			throws ItineraryException {

		validateTravelInformation(travelInfoUpdateDTO);
		String response = this.travelService.updateTravelInformation(travelInfoUpdateDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/{traveliId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get Travel Information with TravelId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Travel Informations retrieved successfully") })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN_USER','ROLE_TRAVEL_USER')")
	public ResponseEntity<TravelInfoResponse> getTravelInformationById(@PathVariable("traveliId") Long traveliId)
			throws ItineraryException {

		TravelInfoResponse travelInfoResponse = this.travelService.getTravelInformationById(traveliId);

		if (null == travelInfoResponse) {
			LOGGER.info("There is no Travel information found for travel Id ->" + traveliId);
		}

		return ResponseEntity.ok(travelInfoResponse);
	}

	private void validateTravelInformation(TravelInfoBaseDTO travelInfoDTO) throws InvalidDataException {

		Timestamp arrivalTime = null;
		Timestamp departureTime = null;

		try {
			arrivalTime = DateUtils.getTimeStampFromString(travelInfoDTO.getArrivalTime());
			departureTime = DateUtils.getTimeStampFromString(travelInfoDTO.getDepartureTime());
		} catch (Exception e) {
			LOGGER.error(
					"Exception in parsing date, please refer API documenttaion and verify the Date format pattern ");
			throw new InvalidDataException(e.getMessage());
		}

		if (!arrivalTime.after(departureTime)) {
			LOGGER.error("Arrival Time should be greater than Departure Time");
			throw new InvalidDataException("Arrival Time should be greater than Departure Time");
		}

	}

}
