package com.usermanagement.DTO;

import java.util.ArrayList;
import java.util.List;

public class HazzardDto {
	public List<FloodsDto> floodsList = new ArrayList<FloodsDto>();
	public List<CyclonesDto> cyclonesList = new ArrayList<CyclonesDto>();
	public List<FloodsDto> getFloodsList() {
		return floodsList;
	}
	public void setFloodsList(List<FloodsDto> floodsList) {
		this.floodsList = floodsList;
	}
	public List<CyclonesDto> getCyclonesList() {
		return cyclonesList;
	}
	public void setCyclonesList(List<CyclonesDto> cyclonesList) {
		this.cyclonesList = cyclonesList;
	}

}
