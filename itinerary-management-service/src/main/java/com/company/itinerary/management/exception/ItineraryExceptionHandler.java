package com.company.itinerary.management.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.itinerary.management.common.ErrorResponse;
import com.company.itinerary.management.constants.ErrorConstants;

@ControllerAdvice(basePackages = "com.company.itinerary.management.controllers")
public class ItineraryExceptionHandler {

	private static HttpHeaders headers;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception) {
		return generateErrorResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ItineraryException.class)
	public ResponseEntity<ErrorResponse> handleItineraryException(ItineraryException itineraryException) {
		ErrorConstants error = itineraryException.getCode();
		return new ResponseEntity<>(new ErrorResponse(error.toString(), error.getStatus()), headers,
				HttpStatus.valueOf(error.getStatus()));
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException exception) {
		return generateErrorResponseEntity(exception, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<ErrorResponse> generateErrorResponseEntity(Exception e, HttpStatus status) {
		return new ResponseEntity<>(new ErrorResponse(status.getReasonPhrase(), status.value()), headers, status);
	}

}
