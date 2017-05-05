package com.usermanagement.DTO;

public class UserCreateReturn implements ResponseInterfaceDto {
		public UserDto user;

		public UserDto getUser() {
			return user;
		}
		public void setUser(UserDto user) {
			this.user = user;
		}


}
