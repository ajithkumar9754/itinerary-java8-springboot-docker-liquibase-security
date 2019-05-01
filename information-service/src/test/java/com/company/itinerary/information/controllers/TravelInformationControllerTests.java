package com.company.itinerary.information.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.itinerary.information.InformationApplicationTests;
import com.company.itinerary.information.feignClients.ManagementServiceFeignClient;
import com.company.itinerary.information.model.TravelInfoResponse;

public class TravelInformationControllerTests extends InformationApplicationTests {

	private static final String TRAVEL_INFO_ENDPOINT = "/api/v1/information/travel";

	private MockMvc mockMvc;

	@InjectMocks
	private TravelInformationController travelInformationController;
	
	@Mock
    private ManagementServiceFeignClient managementServiceFeignClient;
	

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(travelInformationController).build();
	}

	@Test
	public void testTravelInformation() throws Exception {
		List<TravelInfoResponse> travelInformationList = new LinkedList<TravelInfoResponse>();
		
		TravelInfoResponse travelInfoResponse1 = new TravelInfoResponse(1L, "source1", "destinationCity1", "2019-01-19 02:30", "2019-01-19 02:30", 2, 3.5);
		TravelInfoResponse travelInfoResponse2 = new TravelInfoResponse(2L, "source2", "destinationCity2", "2019-01-19 02:30", "2019-01-19 02:30", 4, 5.5);
		travelInformationList.add(travelInfoResponse1);
		travelInformationList.add(travelInfoResponse2);
		
		given(managementServiceFeignClient.getTravelInformations("token", "sourcecity", "destinationcity")).willReturn(travelInformationList);
		
		HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.add("Accept","application/json");
	    httpHeaders.add("Authorization", "basic token");
	    
		this.mockMvc.perform(get("/api/v1/information/travel")
				.param("sourcecity", "cochin")
	            .param("destinationcity", "delhi")
	            .headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.length()", is(2)));
	}
	
}
