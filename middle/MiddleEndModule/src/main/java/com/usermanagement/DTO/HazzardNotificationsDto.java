package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class HazzardNotificationsDto {

	public List<EarthquakesDto> earthquakesList = new ArrayList<EarthquakesDto>();
	public HazzardDto  hazzard;
	public List<EarthquakesDto> getEarthquakesList() {
		return earthquakesList;
	}
	public void setEarthquakesList(List<EarthquakesDto> earthquakesList) {
		this.earthquakesList = earthquakesList;
	}
	public HazzardDto getHazzard() {
		return hazzard;
	}
	public void setHazzard(HazzardDto hazzard) {
		this.hazzard = hazzard;
	}
	

}
