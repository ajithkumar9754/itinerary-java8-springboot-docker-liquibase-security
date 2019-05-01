package com.company.itinerary.management.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.itinerary.management.ItinerarymanagementApplicationTests;
import com.company.itinerary.management.constants.ItineraryConstants;
import com.company.itinerary.management.exception.ItineraryExceptionHandler;
import com.company.itinerary.management.model.TravelInfoBaseDTO;
import com.company.itinerary.management.model.TravelInfoResponse;
import com.company.itinerary.management.service.ITravelService;

public class TravelManagementControllerTests extends ItinerarymanagementApplicationTests {

	private static final String TRAVEL_INFO_ENDPOINT = "/api/v1/management/travelinfo";

	private MockMvc mockMvc;

	@InjectMocks
	private TravelManagementController travelManagementController;

	@Mock
	private ITravelService travelService;

	@Mock
	private Environment env;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(travelManagementController)
				.setControllerAdvice(new ItineraryExceptionHandler()).build();
	}

	@Test
	public void testTravelInformations_between_two_cities_should_return() throws Exception {
		List<TravelInfoResponse> travelInformationList = new LinkedList<TravelInfoResponse>();

		TravelInfoResponse travelInfoResponse1 = new TravelInfoResponse(1L, "source1", "destinationCity1",
				"2019-01-19 02:30", "2019-01-19 02:30", 2, 3.5);
		TravelInfoResponse travelInfoResponse2 = new TravelInfoResponse(2L, "source2", "destinationCity2",
				"2019-01-19 02:30", "2019-01-19 02:30", 4, 5.5);
		travelInformationList.add(travelInfoResponse1);
		travelInformationList.add(travelInfoResponse2);

		Mockito.when(travelService.getTravelInformations(Mockito.any(), Mockito.any()))
				.thenReturn(travelInformationList);

		this.mockMvc
				.perform(get(TRAVEL_INFO_ENDPOINT).param("sourcecity", "cochin").param("destinationcity", "delhi")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.length()", is(2)));
	}

	@Test
	public void testTravelInformation_should_returnWithEmptyResult() throws Exception {
		List<TravelInfoResponse> travelInformationList = new LinkedList<TravelInfoResponse>();
		Mockito.when(travelService.getTravelInformations(Mockito.any(), Mockito.any()))
				.thenReturn(travelInformationList);

		this.mockMvc.perform(get(TRAVEL_INFO_ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(0)));
	}

	@Test
	public void testcreateTravelInformation() throws Exception {

		TravelInfoBaseDTO travelInfoBaseDTORequest = new TravelInfoBaseDTO(1L, "source1", "destinationCity1",
				"2019-01-19 02:30", "2019-01-19 02:30", 2, 3.5);

		Mockito.when(travelService.createTravelInformation(travelInfoBaseDTORequest))
				.thenReturn(env.getProperty(ItineraryConstants.TRAVEL_INFO_CREATED_SUCCESSFULLY));
		this.mockMvc.perform(post(TRAVEL_INFO_ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void testUpdateTravelInformation() throws Exception {

		TravelInfoBaseDTO travelInfoBaseDTORequest = new TravelInfoBaseDTO(1L, "sourceupdated",
				"destinationCityupdated", "2019-01-19 02:30", "2019-01-19 02:40", 2, 3.5);

		Mockito.when(travelService.createTravelInformation(travelInfoBaseDTORequest))
				.thenReturn(env.getProperty(ItineraryConstants.TRAVEL_INFO_UPDATED_SUCCESSFULLY));
		this.mockMvc.perform(post(TRAVEL_INFO_ENDPOINT).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

}
