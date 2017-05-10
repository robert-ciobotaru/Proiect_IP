package com.usermanagement.DTO;

public class UserCreateReturn implements ResponseInterfaceDto {
		private PostUsersFrontendResponseDTO user;

		public PostUsersFrontendResponseDTO getUser() {
			return user;
		}
		public void setUser(PostUsersFrontendResponseDTO user) {
			this.user = user;
		}


}
