package com.usermanagement.DTO;

public class NotificationRequestDto {
	String method = "getNotification";
	long userid = 2;
	long notificationid = 23;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Number getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public Number getNotificationid() {
		return notificationid;
	}
	public void setNotificationid(long notificationid) {
		this.notificationid = notificationid;
	}
	
}
