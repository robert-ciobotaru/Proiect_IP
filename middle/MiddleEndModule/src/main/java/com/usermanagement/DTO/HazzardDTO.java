package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class HazzardDTO {
	public List<FloodsNotificationsDTO> floodsList = new ArrayList<FloodsNotificationsDTO>();
	public List<CyclonesNotificationsDTO> cyclonesList = new ArrayList<CyclonesNotificationsDTO>();
	public List<FloodsNotificationsDTO> getFloodsList() {
		return floodsList;
	}
	public void setFloodsList(List<FloodsNotificationsDTO> floodsList) {
		this.floodsList = floodsList;
	}
	public List<CyclonesNotificationsDTO> getCyclonesList() {
		return cyclonesList;
	}
	public void setCyclonesList(List<CyclonesNotificationsDTO> cyclonesList) {
		this.cyclonesList = cyclonesList;
	}

}
