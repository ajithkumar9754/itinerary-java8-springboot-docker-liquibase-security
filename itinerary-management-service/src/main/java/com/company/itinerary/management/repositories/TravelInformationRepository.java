package com.company.itinerary.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.itinerary.management.entities.TravelInformation;

public interface TravelInformationRepository extends JpaRepository<TravelInformation, Long> {

	@Query(value = "SELECT * FROM travel_information TRAVELINFO WHERE  TRAVELINFO.source=LOWER(:source) AND  TRAVELINFO.destination=LOWER(:destination)  ORDER BY DURATION,NO_OF_CONNECTIONS ASC", nativeQuery = true)
	public List<TravelInformation> findAllTravelInformations(@Param("source") String source,
			@Param("destination") String destination);
	
	@Query(value = "SELECT * FROM travel_information TRAVELINFO WHERE  TRAVELINFO.source=LOWER(:source) AND  TRAVELINFO.destination=LOWER(:destination)  ORDER BY DURATION,NO_OF_CONNECTIONS ASC LIMIT 1", nativeQuery = true)
	public TravelInformation findFastestRouteInformation(@Param("source") String source,
			@Param("destination") String destination);

}
