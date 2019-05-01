package com.company.itinerary.management.common;

public class ErrorResponse {

	private String error;
	private int code;

	public ErrorResponse(String error, int code) {
		this.error = error;
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public int getCode() {
		return code;
	}
}