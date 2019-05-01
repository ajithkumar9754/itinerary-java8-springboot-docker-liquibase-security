package com.company.itinerary.information.feignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.itinerary.information.model.TravelInfoResponse;

@FeignClient(name = "ITINERARY-MANAGEMENT-SERVICE")
public interface ManagementServiceFeignClient {

	@GetMapping("/api/v1/management/travelinfo")
	public List<TravelInfoResponse> getTravelInformations(@RequestHeader("Authorization") String token,
			@RequestParam("sourcecity") String sourcecity, @RequestParam("destinationcity") String destinationcity);

	@GetMapping("/api/v1/management/travelinfo/route")
	public TravelInfoResponse getFastestRouteInformation(@RequestHeader("Authorization") String token,
			@RequestParam("sourcecity") String sourcecity, @RequestParam("destinationcity") String destinationcity);

}
