package com.company.itinerary.management.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.itinerary.management.exception.InvalidDataException;


public class DateUtils {

	static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	public static Timestamp getTimeStampFromString(String dateString) throws InvalidDataException {

		LOGGER.info("Invoking service getTimeStampFromString");

		Timestamp time = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(dateString, format);
		time = Timestamp.valueOf(dateTime);

		return time;

	}

	public static double getDurationBetweenTimeInHours(Timestamp departure, Timestamp arrival)
			throws InvalidDataException {

		double duration = 0;

		LocalDateTime tempDateTime = LocalDateTime.from(departure.toLocalDateTime());
		double hours = tempDateTime.until(arrival.toLocalDateTime(), ChronoUnit.MINUTES);
		DecimalFormat durationRoundOf = new DecimalFormat("0.00");
		duration = Double.parseDouble(durationRoundOf.format(hours / 60));

		return duration;
	}

}
