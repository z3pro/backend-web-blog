package com.spring.payload.response;

import com.spring.dto.CustomUserDetailDTO;
import com.spring.security.CustomUserDetails;

public class JwtResponse {
	private String access_token;
	private String refresh_token;
	private String type = "Bearer";
	private CustomUserDetailDTO user;

	public CustomUserDetailDTO getUser() {
		return user;
	}

	public void setUser(CustomUserDetailDTO user) {
		this.user = user;
	}

	public JwtResponse(String access_token, String refresh_token, CustomUserDetailDTO user) {
		this.access_token = access_token;
		this.refresh_token = refresh_token;
		this.user = user;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
