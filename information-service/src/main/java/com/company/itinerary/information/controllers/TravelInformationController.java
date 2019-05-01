package com.company.itinerary.information.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.itinerary.information.feignClients.ManagementServiceFeignClient;
import com.company.itinerary.information.model.TravelInfoResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1/information/travel")
public class TravelInformationController {

	Logger LOGGER = LoggerFactory.getLogger(TravelInformationController.class);

	private static final String SOURCE_CITY = "sourcecity";
	private static final String DESTINATION_CITY = "destinationcity";

	@Autowired
	ManagementServiceFeignClient managementServiceFeignClient;

	@GetMapping(value = "/route", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get Shortest route between 2 cities")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fastest Route Information retrieved successfully") })
	@PreAuthorize("hasAnyRole('ROLE_TRAVEL_USER')")
	public ResponseEntity<TravelInfoResponse> getshortestPathTravelInformation(
			@RequestParam(name = SOURCE_CITY, required = false) String source,
			@RequestParam(DESTINATION_CITY) String destination,
			@RequestHeader(value = "Authorization") String authorization) {

		TravelInfoResponse travelResponse = managementServiceFeignClient.getFastestRouteInformation(authorization,
				source, destination);

		if (null == travelResponse) {
			LOGGER.info("There is no Travel information found ");
		}

		return ResponseEntity.ok(travelResponse);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get All the Travel Informations between 2 cities")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Travel Informations retrieved successfully") })
	@PreAuthorize("hasAnyRole('ROLE_TRAVEL_USER')")
	public ResponseEntity<List<TravelInfoResponse>> getTravelInformations(
			@RequestParam(name = SOURCE_CITY, required = false) String source,
			@RequestParam(DESTINATION_CITY) String destination,
			@RequestHeader(value = "Authorization") String authorization) {

		LOGGER.info("Invoking service getTravelInformations from ManagementServiceFeignClient via ZUUL gateway...");

		List<TravelInfoResponse> travelResponses = managementServiceFeignClient.getTravelInformations(authorization,
				source, destination);

		if (CollectionUtils.isEmpty(travelResponses)) {
			LOGGER.info("There is no Travel information found ");
		}

		return ResponseEntity.ok(travelResponses);
	}

}
