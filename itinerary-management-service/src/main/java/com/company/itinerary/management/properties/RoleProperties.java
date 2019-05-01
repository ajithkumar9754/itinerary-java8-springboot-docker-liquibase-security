package com.company.itinerary.management.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "roleproperties", ignoreUnknownFields = false)
public class RoleProperties {

	private String adminUser;
	private String traveluser;

	private String adminUserPassword;
	private String traveluserPassword;
	
	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public String getTraveluser() {
		return traveluser;
	}

	public void setTraveluser(String traveluser) {
		this.traveluser = traveluser;
	}

	public String getAdminUserPassword() {
		return adminUserPassword;
	}

	public void setAdminUserPassword(String adminUserPassword) {
		this.adminUserPassword = adminUserPassword;
	}

	public String getTraveluserPassword() {
		return traveluserPassword;
	}

	public void setTraveluserPassword(String traveluserPassword) {
		this.traveluserPassword = traveluserPassword;
	}
	
	

}
