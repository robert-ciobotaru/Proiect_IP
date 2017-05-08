package com.usermanagement.DTO;

public class UserCreateReturn implements ResponseInterfaceDto {
		private UserDto user;

		public UserDto getUser() {
			return user;
		}
		public void setUser(UserDto user) {
			this.user = user;
		}


}
