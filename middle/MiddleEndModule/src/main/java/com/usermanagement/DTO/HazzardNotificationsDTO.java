package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class HazzardNotificationsDTO {

	public List<EarthquakesNotificationsDTO> earthquakesList = new ArrayList<EarthquakesNotificationsDTO>();
	public HazzardDTO  hazzard;
	public List<EarthquakesNotificationsDTO> getEarthquakesList() {
		return earthquakesList;
	}
	public void setEarthquakesList(List<EarthquakesNotificationsDTO> earthquakesList) {
		this.earthquakesList = earthquakesList;
	}
	public HazzardDTO getHazzard() {
		return hazzard;
	}
	public void setHazzard(HazzardDTO hazzard) {
		this.hazzard = hazzard;
	}
	

}
