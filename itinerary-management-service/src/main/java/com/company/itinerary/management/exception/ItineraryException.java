package com.company.itinerary.management.exception;

import com.company.itinerary.management.constants.ErrorConstants;

public class ItineraryException extends Exception {

	private static final long serialVersionUID = 2608731921661294403L;
	private final ErrorConstants code;

	public ItineraryException() {
		super();
		this.code = ErrorConstants.ITINERARY_ERROR;
	}

	public ItineraryException(ErrorConstants errorCode) {
		super(errorCode.toString());
		this.code = errorCode;
	}

	public ItineraryException(ErrorConstants errorCode, String errorMessage) {
		super(errorMessage);
		this.code = errorCode;
	}

	public ItineraryException(String message, Throwable cause) {
		super(message, cause);
		this.code = ErrorConstants.ITINERARY_ERROR;
	}

	public ItineraryException(String message) {
		super(message);
		this.code = ErrorConstants.ITINERARY_ERROR;
	}

	public ItineraryException(Throwable cause) {
		super(cause);
		this.code = ErrorConstants.ITINERARY_ERROR;
	}

	public ErrorConstants getCode() {
		return code;
	}

}