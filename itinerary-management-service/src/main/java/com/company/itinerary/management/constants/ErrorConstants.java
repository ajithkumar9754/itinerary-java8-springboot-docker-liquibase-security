package com.company.itinerary.management.constants;

import org.springframework.http.HttpStatus;

public enum ErrorConstants {
	ITINERARY_ERROR("Error", HttpStatus.INTERNAL_SERVER_ERROR.value()),
	ITINERARY_ACCESS_DENIED("Access is denied", HttpStatus.UNAUTHORIZED.value()),
	ITINERARY_UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED.value()),
	TRAVEL_INFORMATION_NOT_FOUND("TravelInformation not found.", HttpStatus.BAD_REQUEST.value()),
	TRAVEL_INFORMATION_CREATION_FAILED("TravelInformation creation failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
	
	private final String error;
    private final int status;

    ErrorConstants(String error, int status) {
        this.error = error;
        this.status = status;
    }

    @Override
    public String toString() {
        return this.error;
    }

    public int getStatus() {
        return this.status;
    }
}
