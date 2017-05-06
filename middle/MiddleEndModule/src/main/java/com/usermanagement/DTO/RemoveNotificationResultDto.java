package com.usermanagement.DTO;

public class RemoveNotificationResultDto{
	private Integer id;
	private String error;
	public Integer getId() {
		return id;
	}
	public void setId(int user_id) {
		this.id = user_id;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
