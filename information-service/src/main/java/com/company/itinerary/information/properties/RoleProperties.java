package com.company.itinerary.information.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "roleproperties", ignoreUnknownFields = false)
public class RoleProperties {

	private String traveluser;

	private String traveluserPassword;

	public String getTraveluser() {
		return traveluser;
	}

	public void setTraveluser(String traveluser) {
		this.traveluser = traveluser;
	}

	public String getTraveluserPassword() {
		return traveluserPassword;
	}

	public void setTraveluserPassword(String traveluserPassword) {
		this.traveluserPassword = traveluserPassword;
	}
	

	
	
	

}
